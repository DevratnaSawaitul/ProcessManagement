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
function saveNewSheetData() {
    // Disable the save button to prevent multiple submissions
    saveButton.disabled = true;

    // Show the loader
    document.getElementById("loader").style.display = "block"; // Show loader

    // Get the values from the input fields
    const data = {
        file_name: document.getElementById("fileName").value,
        version: document.getElementById("version").value,
        date: formatDate(document.getElementById("date").value), // Format date
        department: document.getElementById("department").value,
        design_no: document.getElementById("designNo").value,
        floor: document.getElementById("floor").value,
        last_updated_by: document.getElementById("lastUpdatedBy").value,
    };

    // Validate inputs
    for (const key in data) {
        if (!data[key]) {
            alert(`${key} cannot be empty!`);
            document.getElementById("loader").style.display = "none"; // Hide loader if validation fails
            saveButton.disabled = false; // Re-enable the save button
            return;
        }
    }

    // Stringify the data
    const jsonData = JSON.stringify(data);

    // API URL
    const apiUrl = window.location.origin + "/ProcessManager/webapi/sheets/addSheets"; // API URL

    // Fetch the data from the API
    fetch(apiUrl, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: jsonData
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
        // Hide the loader
        document.getElementById("loader").style.display = "none"; // Hide loader

        // Check the status in the response data
        if (data.success) {
            // Successfully added the sheet
            alert("Sheet saved successfully!");
            closeNewSheetModal();
            fetchRecentSheets();
            loadExistingSheets();
        } else {
            // Handle failure cases
            let errorMsg = "";
            switch(data.msg) {
                case "file_name_already_exist":
                    errorMsg = "File name already exists!";
                    break;
                case "sheet_add_fail":
                    errorMsg = "Failed to add the sheet!";
                    break;
                default:
                    errorMsg = "An unknown error occurred!";
                    break;
            }
            alert(errorMsg);
        }
    })
    .catch(function(error) {
        // Hide the loader in case of error
        document.getElementById("loader").style.display = "none"; // Hide loader

        // Handle errors during fetch or JSON parsing
        console.error("Error during API call:", error);
        alert("An unexpected error occurred.");

        // Re-enable the save button in case of error
        saveButton.disabled = false;
    });
}

/**
 * Function to format the date as "dd-mm-yyyy hh:mm:ss"
 */
function formatDate(inputDate) {
    if (!inputDate) return ""; // Handle empty date

    const date = new Date(inputDate);
    const day = String(date.getDate()).padStart(2, '0');
    const month = String(date.getMonth() + 1).padStart(2, '0'); // Months are 0-indexed
    const year = date.getFullYear();
    return `${day}-${month}-${year}`;
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
