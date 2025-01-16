<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Process Management System</title>
    <link rel="stylesheet" href="sidebar.css">
</head>
<body>
    <div class="sidebar">
        <div class="sidebar-header">
            <span>Menu</span>
        </div>
        <div class="sidebar-buttons">
            <button onclick="openNewSheetModal()">+ Add New Sheet</button>
            <button onclick="showModal('open')">Open Existing Sheet</button>
            <button onclick="toggleRecentSheets()">Recent Sheets</button>
            <div class="recent-sheets" id="recent-sheets">
                <!-- Recent sheets will be populated here dynamically -->
            </div>
        </div>
    </div>

    <script src="sidebar.js"></script>
</body>
</html>
