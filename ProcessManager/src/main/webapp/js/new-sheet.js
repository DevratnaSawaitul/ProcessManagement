// Select the modal and related elements
const newSheetModal = document.getElementById("new-sheet-modal");
const saveButton = document.getElementById("saveBtn");

// Select all input fields
const inputFields = document.querySelectorAll('.new-sheet-body input');

/**
 * Opens the "Add New Sheet" modal and clears the fields.
 */
function openNewSheetModal() {
    // Clear all fields when opening the form
    inputFields.forEach(input => input.value = "");
    saveButton.disabled = true; // Initially disable the save button
    newSheetModal.classList.add("show");
}

/**
 * Closes the "Add New Sheet" modal.
 */
function closeNewSheetModal() {
    newSheetModal.classList.remove("show");
}

/**
 * Saves the entered sheet data.
 */
function saveNewSheetData() {
    const data = {
        fileName: document.getElementById("fileName").value,
        version: document.getElementById("version").value,
        date: document.getElementById("date").value,
        department: document.getElementById("department").value,
        designNo: document.getElementById("designNo").value,
        floor: document.getElementById("floor").value,
        lastUpdatedBy: document.getElementById("lastUpdatedBy").value,
    };

    // Validate inputs
    for (const key in data) {
        if (!data[key]) {
            alert(`${key} cannot be empty!`);
            return;
        }
    }

    // Simulate saving data
    console.log("New sheet data saved:", data);
    alert("Sheet saved successfully!");
    closeNewSheetModal();
}

/**
 * Function to check if all fields are filled and enables the save button accordingly
 */
function validateInputs() {
    const allFieldsFilled = Array.from(inputFields).every(input => {
        // Special handling for date input field
        if (input.type === "date") {
            return input.value.trim() !== ""; // Ensure date is not empty
        }
        return input.value.trim() !== ""; // Standard check for all other input fields
    });
    
    saveButton.disabled = !allFieldsFilled; // Enable save button if all fields are filled
}
