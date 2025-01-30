/**
 * Fetches all process data from the API and displays it in the table.
 */
function loadProcesses() {
    const apiUrl = window.location.origin + "/ProcessManager/webapi/org/show_process"; // API endpoint
    const jsonData = JSON.stringify({ load_type: "all" });

    const tableBody = document.getElementById("processes-table-body");

    // Clear any previous rows and show a loading message
    tableBody.innerHTML = "<tr><td colspan='3'>Loading...</td></tr>";

    fetch(apiUrl, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: jsonData,
    })
    .then((response) => {
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
        return response.json();
    })
    .then((data) => {
        // Remove loading message once data is received
        tableBody.innerHTML = ""; // Clear the loading message

        if (data.status === true && data.processes.length > 0) {
            populateProcessTable(data.processes);
        } else {
            tableBody.innerHTML =
                "<tr><td colspan='3'>No processes available to display.</td></tr>";
        }
    })
    .catch((error) => {
        console.error("Error fetching process data:", error);
        tableBody.innerHTML = `<tr><td colspan='3'>Error: ${error.message}</td></tr>`;
    });
}

/**
 * Populates the table with the fetched process data.
 * @param {Array} processes - Array of process objects returned from the API.
 */
function populateProcessTable(processes) {
    const tableBody = document.getElementById("processes-table-body");
    processes.forEach((process) => {
        const row = document.createElement("tr");
        row.innerHTML = `
            <td>${process.process_name}</td>
            <td>${process.active ? "Yes" : "No"}</td>
        `;
        tableBody.appendChild(row);
    });
}

// Load processes when the page loads
document.addEventListener("DOMContentLoaded", loadProcesses);
