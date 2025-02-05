<div class="single-sheet-header">
    <span class="back-btn" title="Back" onclick="showView('existing-sheets-content')">&#8592;</span>
    <h2>Sheet Details</h2>
</div>

<!-- Sheet Details Table -->
<div class="table-container-single-sheet">
    <table class="sheets-table-single-sheet">
        <thead>
            <tr>
                <th>File Name</th>
                <th>Design No</th>
                <th>Department</th>
                <th>Floor</th>
                <th>Date Created</th>
                <th>Last Updated By</th>
                <th>Last Updated On</th>
                <th>Version</th>
            </tr>
        </thead>
        <tbody id="sheets-table-body-single-sheet">
            <!-- Data will be inserted dynamically -->
        </tbody>
    </table>
</div>

<!-- Sheet Process Section (Always Visible) -->
<div id="sheet-process-section" class="sheet-process-section">
    <div class="sheet-process-header">
        <h3>Processes</h3>
        <button class="add-btn right-btn" title="Add New Process in the Sheet" onclick="alert('Add New Sheet Process Clicked')">+ Add Process</button>
    </div>

    <div id="sheet-process-container">
        <!-- Processes will be dynamically inserted here -->
    </div>
</div>