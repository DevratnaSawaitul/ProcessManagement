/**
 * Fetches all sheet data from the API and displays it in the table.
 */

/*function loadSingleSheets(sheet) {
	const tableBody = document.getElementById("single-sheet");
	tableBody.innerHTML = sheet;
}*/

/**
 * Fetches all sheet data from the API and displays it in the table.
 */
function loadSingleSheets(sheet) {
    const apiUrl = window.location.origin + "/ProcessManager/webapi/sheets/showSheets"; // API endpoint
    const jsonData = JSON.stringify({ load_type: "singleSheet",file_name:sheet });

    const tableBody = document.getElementById("sheets-table-body-single-sheet");
    const mainContent = document.getElementById("main-content-single-sheet");

    // Clear any previous rows and show a loading message
    tableBody.innerHTML = "<tr><td colspan='9'>Loading...</td></tr>";

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

            if (data.status === true && data.sheets.length > 0) {
                populateSingleTable(data.sheets);
            } else {
                tableBody.innerHTML =
                    "<tr><td colspan='9'>No sheets available to display.</td></tr>";
            }
        })
        .catch((error) => {
            console.error("Error fetching sheets data:", error);
            tableBody.innerHTML = `<tr><td colspan='9'>Error: ${error.message}</td></tr>`;
        });
}

/**
 * Populates the table with the fetched sheet data.
 * @param {Array} sheets - Array of sheet objects returned from the API.
 */
function populateSingleTable(sheets) {
    const tableBody = document.getElementById("sheets-table-body-single-sheet");
    sheets.forEach((sheet) => {
        // Parse the date fields and format them if needed
        const formattedDate = formatDateSingle(sheet.date);
        const formattedLastUpdated = formatDateSingle(sheet.date_of_last_update);

        const row = document.createElement("tr");
        row.innerHTML = `
        	<td>${sheet.sheet_id}</td>
            <td>${sheet.file_name}</td>
            <td>${sheet.design_no}</td>
            <td>${sheet.department}</td>
            <td>${sheet.floor}</td>
            <td>${formattedDate}</td>  <!-- Use formatted date here -->
            <td>${sheet.last_updated_by}</td>
            <td>${formattedLastUpdated}</td> <!-- Use formatted last update date here -->
            <td>${sheet.version}</td>
        `;
        tableBody.appendChild(row);
    });
}

// Helper function to format the date properly
function formatDateSingle(dateString) {
    // Handle edge cases for date formatting
    if (!dateString || dateString === '01-01-1') {
        return "N/A"; // Return 'N/A' for invalid or placeholder dates
    }
    const date = new Date(dateString);
    if (isNaN(date)) {
        return dateString; // If it's an invalid date, return the original string
    }

    // Format the date to 'dd-mm-yyyy' format
    const day = String(date.getDate()).padStart(2, "0");
    const month = String(date.getMonth() + 1).padStart(2, "0"); // Months are 0-based
    const year = date.getFullYear();
    return `${day}-${month}-${year}`;
}
