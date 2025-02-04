document.addEventListener("DOMContentLoaded", () => {
	loadExistingSheets();
});

/**
 * Fetches all sheets from the API and displays them.
 */
function loadExistingSheets() {
	const apiUrl = window.location.origin + "/ProcessManager/webapi/sheets/showSheets";
	const jsonData = JSON.stringify({ load_type: "all" });

	const tableBody = document.getElementById("sheets-table-body-existing-sheet");
	tableBody.innerHTML = "<tr><td colspan='9'>Loading...</td></tr>";

	fetch(apiUrl, {
		method: "POST",
		headers: { "Content-Type": "application/json" },
		body: jsonData,
	})
		.then(response => response.json())
		.then(data => {
			tableBody.innerHTML = ""; // Clear loading message

			if (data.success === true && data.sheets.length > 0) {
				populateSheetTable(data.sheets);
			} else {
				tableBody.innerHTML = "<tr><td colspan='9'>No sheets available.</td></tr>";
			}
		})
		.catch(error => {
			console.error("Error fetching sheets:", error);
			tableBody.innerHTML = `<tr><td colspan='9'>Error: ${error.message}</td></tr>`;
		});
}

/**
 * Populates the table with sheet data.
 */
function populateSheetTable(sheets) {
	const tableBody = document.getElementById("sheets-table-body-existing-sheet");
	tableBody.innerHTML = "";

	sheets.forEach(sheet => {
		const row = document.createElement("tr");
		row.dataset.sheet = JSON.stringify(sheet); // Store sheet object in dataset

		row.innerHTML = `
            <td>${sheet.file_name}</td>
            <td>${sheet.design_no}</td>
            <td>${sheet.department}</td>
            <td>${sheet.floor}</td>
            <td>${formatDate(sheet.date)}</td>
            <td>${sheet.last_updated_by}</td>
            <td>${sheet.date_of_last_update}</td>
            <td>${sheet.version}</td>
            <td>
                <span class="action-btn edit-btn" title="Edit Sheet" onclick="editSheet(this)">
                    <font face="Arial">&#x270E;</font> <!-- ✎ Edit Icon -->
                </span>
                <span class="action-btn delete-btn" title="Delete Sheet" onclick="deleteSheet(this)">
                    <font face="Arial">&#x1F5D1;</font> <!-- 🗑 Delete Icon -->
                </span>
            </td>
        `;

		// Show alert and open single sheet view on row click
		row.addEventListener("click", (event) => {
			if (!event.target.closest(".action-btn")) {
				alert(`Sheet ID: ${sheet.sheet_id}`);

				// Show the single sheet content view
				showView('single-sheet-content');

				// Load the sheet data
				loadSingleSheets(sheet.file_name);
			}
		});

		tableBody.appendChild(row);
	});
}

/**
 * Handles deleting a sheet.
 */
function deleteSheet(element) {
	const sheet = JSON.parse(element.closest("tr").dataset.sheet);

	if (!confirm(`Are you sure you want to delete "${sheet.file_name}"?`)) {
		return; // User canceled deletion
	}

	const apiUrl = window.location.origin + "/ProcessManager/webapi/sheets/deleteSheet";
	const requestData = JSON.stringify({ sheet_id: sheet.sheet_id, file_name: sheet.file_name });

	fetch(apiUrl, {
		method: "POST",
		headers: { "Content-Type": "application/json" },
		body: requestData,
	})
		.then(response => response.json())
		.then(data => {
			if (data.success) {
				alert("Sheet deleted successfully!");
				loadExistingSheets();
			} else {
				alert(`Failed to delete sheet: ${data.msg || "Unknown error"}`);
			}
		})
		.catch(error => {
			console.error("Error deleting sheet:", error);
			alert(`Error: ${error.message}`);
		});
}

/**
 * Handles editing a sheet.
 */
function editSheet(element) {
	const sheet = JSON.parse(element.closest("tr").dataset.sheet);
	openEditSheetModal(sheet); // Open modal with sheet data
	//alert("edit sheet");
}

/**
 * Handles adding a new sheet.
 */
function addSheet() {
	openAddSheetModal(); // Open modal for new sheet
	//alert("add");
}

/**
 * Helper function to format the date properly.
 */
function formatDate(dateString) {
	if (!dateString || dateString === "01-01-1") {
		return "N/A";
	}
	const date = new Date(dateString);
	if (isNaN(date)) {
		return dateString;
	}

	const day = String(date.getDate()).padStart(2, "0");
	const month = String(date.getMonth() + 1).padStart(2, "0");
	const year = date.getFullYear();
	return `${day}-${month}-${year}`;
}
