/**
 * Toggles the visibility of recent sheets.
 */
/**
 * Toggles the visibility of recent sheets and refreshes the content when opened.
 */
function toggleRecentSheets() {
	const recentSheets = document.getElementById("recent-sheets");

	// If the recent sheets are being opened (i.e., the current display is 'none')
	if (recentSheets.style.display === "none" || recentSheets.style.display === "") {
		fetchRecentSheets(); // Refresh the recent sheets when opening
	}

	// Toggle visibility
	recentSheets.style.display = (recentSheets.style.display === "flex") ? "none" : "flex";
}


/**
 * Function to fetch recent sheets from the API and display them in the sidebar.
 */
function fetchRecentSheets() {
	const apiUrl = window.location.origin + "/ProcessManager/webapi/sheets/recent"; // API URL
	const jsonData = JSON.stringify({});
	const recentSheetsContainer = document.getElementById("recent-sheets");
	recentSheetsContainer.innerHTML = "Loading recent sheets..."; // Display loading message

	// Fetch the recent sheets data from the API using Promises
	fetch(apiUrl, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json',
		}, body: jsonData
	})
		.then(function(response) {
			// Handle non-OK responses (e.g., 404, 500)
			if (!response.ok) {
				throw new Error("HTTP error! Status: " + response.status);
			}
			// Parse the JSON response from the API
			return response.json();
		})
		.then(function(data) {
			// Check the status in the response data
			if (data.status === "success") {
				// Clear the loading message
				recentSheetsContainer.innerHTML = "";

				// Display each sheet in the sidebar
				data.recentSheets.forEach(function(sheet) {
					const sheetElement = document.createElement("div");
					sheetElement.textContent = sheet;
					sheetElement.className = "recent-sheet";

					// When a sheet is clicked, show its details by calling showView('single-sheet-content')
					sheetElement.onclick = function() {
						// Call the showView function to display the single-sheet-content view
						showView('single-sheet-content');
						// Optionally, pass the sheet name to load specific data related to that sheet
						loadSingleSheets(sheet); // Assuming this method loads the single sheet details
					};

					recentSheetsContainer.appendChild(sheetElement);
				});
			} else {
				// If the API response is not successful
				recentSheetsContainer.textContent = "Failed to load recent sheets.";
			}
		})
		.catch(function(error) {
			// Handle errors during fetch or JSON parsing
			console.error("Error fetching recent sheets:", error);
			recentSheetsContainer.textContent = "Error: " + error.message;
		});
}
/**
 * Function to display details of a selected sheet (for now, just log it).
 */
/**
 /**
 * Updates the main content area with the details of the selected sheet.
 * @param {string} sheetName - The name of the sheet to display.
 */
function showSheetDetails(sheetName) {
	const detailsContainer = document.getElementById("sheet-details");
	if (!detailsContainer) {
		console.error("Sheet details container not found!");
		return;
	}

	// Display loading state
	detailsContainer.innerHTML = "Loading details...";

	const apiUrl = window.location.origin + "/ProcessManager/webapi/sheets/showSheets"; // API URL for sheet details
	const jsonData = JSON.stringify({ file_name: sheetName, load_type: "singleSheet" });

	// Fetch the sheet details from the API using Promises
	fetch(apiUrl, {
		method: "POST",
		headers: {
			"Content-Type": "application/json",
		},
		body: jsonData,
	})
		.then(function(response) {
			// Handle non-OK responses (e.g., 404, 500)
			if (!response.ok) {
				throw new Error("HTTP error! Status: " + response.status);
			}
			// Parse the JSON response from the API
			return response.json();
		})
		.then(function(data) {
			// Check if the status is true and data is available
			if (data.status === true && data.sheets && data.sheets.length > 0) {
				// Extract sheet details from the response
				const sheet = data.sheets[0]; // Assuming you're fetching details for a single sheet

				// Update the main content with the sheet details
				detailsContainer.innerHTML = `
                    <h3>${sheet.file_name}</h3>
                    <ul>
                        <li><strong>Design No:</strong> ${sheet.design_no}</li>
                        <li><strong>Department:</strong> ${sheet.department}</li>
                        <li><strong>Floor:</strong> ${sheet.floor}</li>
                        <li><strong>Date Created:</strong> ${sheet.date}</li>
                        <li><strong>Last Updated By:</strong> ${sheet.last_updated_by}</li>
                        <li><strong>Last Updated On:</strong> ${sheet.date_of_last_update}</li>
                        <li><strong>Version:</strong> ${sheet.version}</li>
                    </ul>
                `;
			} else {
				// If the API response is not successful or no sheets are returned
				detailsContainer.innerHTML = `<h3>${sheetName}</h3><p>No details available for this sheet.</p>`;
			}
		})
		.catch(function(error) {
			// Handle errors during fetch or JSON parsing
			console.error("Error fetching sheet details:", error);
			detailsContainer.innerHTML = `<h3>${sheetName}</h3><p>Error: ${error.message}</p>`;
		});
}

// Call the function to load recent sheets when the page loads
document.addEventListener("DOMContentLoaded", fetchRecentSheets);
