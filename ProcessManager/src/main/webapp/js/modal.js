const modal = document.getElementById("modal");
const modalTitle = document.getElementById("modal-title");
const modalBody = document.getElementById("modal-body");

/**
 * Opens the modal with appropriate content based on the type.
 * @param {string} type - The type of modal ("add" or "open").
 */
function showModal(type) {
    if (type === "add") {
        modalTitle.innerText = "Add New Sheet";
        modalBody.innerHTML = `
            <div class="input-field">
                <label for="fileName">File Name:</label>
                <input type="text" id="fileName" placeholder="Enter file name">
            </div>
            <div class="input-field">
                <label for="version">Version:</label>
                <input type="text" id="version" placeholder="Enter version">
            </div>
            <div class="input-field">
                <label for="date">Date:</label>
                <input type="date" id="date">
            </div>
        `;
    } else if (type === "open") {
        modalTitle.innerText = "Open Existing Sheet";
        modalBody.innerHTML = `
            <div class="input-field">
                <label for="sheetName">Sheet Name:</label>
                <input type="text" id="sheetName" placeholder="Enter sheet name">
            </div>
        `;
    }
    modal.classList.add("show");
}

/**
 * Closes the modal.
 */
function closeModal() {
    modal.classList.remove("show");
}

/**
 * Saves the changes made in the modal.
 */
function saveChanges() {
    alert("Changes Saved!");
    closeModal();
}
