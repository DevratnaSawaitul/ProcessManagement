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

    // Mock data for sheets; replace this with server-side data if needed
    const sheetDetails = {
        "Sheet 1": "Details for Sheet 1: Created by User A on 2023-01-01.",
        "Sheet 2": "Details for Sheet 2: Created by User B on 2023-01-05.",
        "Sheet 3": "Details for Sheet 3: Created by User C on 2023-01-10.",
        "Sheet 4": "Details for Sheet 4: Created by User D on 2023-01-15.",
        "Sheet 5": "Details for Sheet 5: Created by User E on 2023-01-20."
    };

    // Update the main content with the sheet details
    if (sheetDetails[sheetName]) {
        detailsContainer.innerHTML = `
            <h3>${sheetName}</h3>
            <p>${sheetDetails[sheetName]}</p>
        `;
    } else {
        detailsContainer.innerHTML = `
            <h3>${sheetName}</h3>
            <p>No details available for this sheet.</p>
        `;
    }
}