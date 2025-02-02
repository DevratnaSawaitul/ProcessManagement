document.addEventListener("DOMContentLoaded", () => {
    loadTools();
});

/**
 * Fetches all tools from the API and displays them in the table.
 */
function loadTools() {
    const apiUrl = window.location.origin + "/ProcessManager/webapi/org/show_tools";
    const jsonData = JSON.stringify({ load_type: "all" });

    const tableBody = document.getElementById("tools-table-body");
    tableBody.innerHTML = "<tr><td colspan='4'>Loading...</td></tr>";

    fetch(apiUrl, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: jsonData
    })
    .then(response => response.json())
    .then(data => {
        tableBody.innerHTML = "";

        if (data.status && data.tools.length > 0) {
            populateToolsTable(data.tools);
        } else {
            tableBody.innerHTML = "<tr><td colspan='4'>No tools available.</td></tr>";
        }
    })
    .catch(error => {
        console.error("Error fetching tools data:", error);
        tableBody.innerHTML = `<tr><td colspan='4'>Error: ${error.message}</td></tr>`;
    });
}

/**
 * Populates the table with the fetched tools data.
 * @param {Array} tools - Array of tool objects returned from the API.
 */
function populateToolsTable(tools) {
    const tableBody = document.getElementById("tools-table-body");
    tools.forEach(tool => {
        const row = document.createElement("tr");
        row.innerHTML = `
            <td>${tool.tool_name}</td>
            <td>${tool.sub_process}</td>
            <td>${tool.active ? "Yes" : "No"}</td>
        `;
        tableBody.appendChild(row);
    });
}
