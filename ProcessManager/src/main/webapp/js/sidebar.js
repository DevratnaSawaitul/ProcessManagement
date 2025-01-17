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
	const jsonData = JSON.stringify({
	});
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

					// When a sheet is clicked, show its details
					sheetElement.onclick = function() {
						showSheetDetails(sheet);
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
function showSheetDetails(sheetName) {
	alert("Showing details for " + sheetName);
}

// Call the function to load recent sheets when the page loads
document.addEventListener("DOMContentLoaded", fetchRecentSheets);
