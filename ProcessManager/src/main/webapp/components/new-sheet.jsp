<!-- Loader Element -->
<div id="loader" class="loader" style="display: none;"></div>

<!-- New Sheet Modal -->
<div id="new-sheet-modal">
    <div class="new-sheet-content">
        <!-- Header -->
        <div class="new-sheet-header" id="new-sheet-title">Add New Sheet</div>

        <!-- Body -->
        <div class="new-sheet-body" id="new-sheet-body">
            <!-- Input Fields -->
            <div class="input-field">
                <label for="fileName">File Name:</label>
                <input type="text" id="fileName" placeholder="Enter file name" oninput="validateInputs()">
            </div>
            <div class="input-field">
                <label for="version">Version:</label>
                <input type="text" id="version" placeholder="Enter version" oninput="validateInputs()">
            </div>
            <div class="input-field">
                <label for="date">Date:</label>
                <input type="date" id="date" oninput="validateInputs()">
            </div>
            <div class="input-field">
                <label for="department">Department:</label>
                <input type="text" id="department" placeholder="Enter department" oninput="validateInputs()">
            </div>
            <div class="input-field">
                <label for="designNo">Design No:</label>
                <input type="text" id="designNo" placeholder="Enter design number" oninput="validateInputs()">
            </div>
            <div class="input-field">
                <label for="floor">Floor:</label>
                <input type="text" id="floor" placeholder="Enter floor" oninput="validateInputs()">
            </div>
            <div class="input-field">
                <label for="lastUpdatedBy">Last Updated By:</label>
                <input type="text" id="lastUpdatedBy" placeholder="Enter updater's name" oninput="validateInputs()">
            </div>
        </div>

        <!-- Footer -->
        <div class="new-sheet-footer">
            <button class="save-btn" id="saveBtn" onclick="saveNewSheetData()" disabled>Save</button>
            <button class="cancel-btn" onclick="closeNewSheetModal()">Cancel</button>
        </div>
    </div>
</div>
