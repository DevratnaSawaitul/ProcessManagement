document.addEventListener("DOMContentLoaded", () => {
    loadSkills();
});

/**
 * Fetches all skills from the API and displays them in the table.
 */
function loadSkills() {
    const apiUrl = window.location.origin + "/ProcessManager/webapi/org/show_skills";
    const jsonData = JSON.stringify({ load_type: "all" });

    const tableBody = document.getElementById("skills-table-body");
    tableBody.innerHTML = "<tr><td colspan='2'>Loading...</td></tr>";

    fetch(apiUrl, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: jsonData
    })
    .then(response => response.json())
    .then(data => {
        tableBody.innerHTML = ""; // Clear table before inserting new rows

        if (data.status && data.skills.length > 0) {
            populateSkillsTable(data.skills);
        } else {
            tableBody.innerHTML = "<tr><td colspan='2'>No skills available.</td></tr>";
        }
    })
    .catch(error => {
        console.error("Error fetching skills data:", error);
        tableBody.innerHTML = `<tr><td colspan='2'>Error: ${error.message}</td></tr>`;
    });
}

/**
 * Populates the table with the fetched skills data.
 * @param {Array} skills - Array of skill objects returned from the API.
 */
function populateSkillsTable(skills) {
    const tableBody = document.getElementById("skills-table-body");

    skills.forEach(skill => {
        const row = document.createElement("tr");
        row.innerHTML = `
            <td>${skill.skill_name}</td>
            <td>${skill.active ? "Yes" : "No"}</td>
        `;
        tableBody.appendChild(row);
    });
}
