<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Process Management System</title>
<style>
* {
	margin: 0;
	padding: 0;
	box-sizing: border-box;
}

body {
	font-family: Arial, sans-serif;
	display: flex;
	height: 100vh;
	overflow: hidden;
}

/* Sidebar Styles */
.sidebar {
	width: 250px;
	background-color: #2c3e50;
	color: white;
	display: flex;
	flex-direction: column;
	position: relative;
	transition: width 0.3s ease;
}

.sidebar.collapsed {
	width: 60px;
}

.sidebar-header {
	padding: 15px;
	text-align: center;
	font-size: 1.2rem;
	background-color: #34495e;
}

.menu-toggle {
	position: absolute;
	top: 10px;
	right: 10px;
	background: none;
	border: none;
	color: white;
	font-size: 1.2rem;
	cursor: pointer;
}

.sidebar-buttons {
	flex-grow: 1;
	display: flex;
	flex-direction: column;
	gap: 10px;
	padding: 10px;
}

.sidebar-buttons button {
	padding: 10px;
	font-size: 1rem;
	background-color: #34495e;
	color: white;
	border: none;
	cursor: pointer;
	border-radius: 5px;
	text-align: left;
	position: relative;
}

.sidebar-buttons button:hover {
	background-color: #1abc9c;
}

.recent-sheets {
	display: none;
	flex-direction: column;
	gap: 10px;
	padding: 10px;
	background-color: #34495e;
	border-radius: 5px;
}

.sidebar-buttons .recent-sheets {
	display: flex;
}

.recent-sheet {
	background-color: #1abc9c;
	padding: 10px;
	color: white;
	border-radius: 5px;
	cursor: pointer;
}

.recent-sheet:hover {
	background-color: #16a085;
}

/* Main Content Styles */
.main-content {
	flex-grow: 1;
	padding: 20px;
	overflow-y: auto;
}

.sheet-details {
	margin-top: 20px;
	padding: 20px;
	background-color: #ecf0f1;
	border-radius: 5px;
	box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.sheet-details h3 {
	margin-bottom: 10px;
}

.steps {
	margin-top: 10px;
	display: flex;
	flex-direction: column;
	gap: 10px;
}

.step {
	display: flex;
	align-items: center;
	gap: 10px;
}

.step select, .step button {
	padding: 5px;
	font-size: 1rem;
}

.step button {
	background-color: #3498db;
	color: white;
	border: none;
	cursor: pointer;
	border-radius: 5px;
}

.step button:hover {
	background-color: #2980b9;
}

/* Modal Styles */
.modal {
	display: none;
	position: fixed;
	z-index: 10;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	background-color: rgba(0, 0, 0, 0.4);
	justify-content: center;
	align-items: center;
}

.modal.show {
	display: flex;
}

.modal-content {
	background-color: white;
	padding: 20px;
	border-radius: 5px;
	width: 40%;
	max-height: 80%; /* Ensure it doesn't exceed the screen height */
	overflow-y: auto; /* Enable scrolling if content exceeds height */
	box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.modal-body {
	max-height: calc(80vh - 100px); /* Adjust height considering the modal title and footer */
	overflow-y: auto;
	padding: 10px
}

.modal-content h2 {
	margin-bottom: 10px;
}

.modal-content textarea, .modal-content input {
	width: 100%;
	padding: 10px;
	margin-bottom: 10px;
	font-size: 1rem;
	border: 1px solid #bdc3c7;
	border-radius: 5px;
}

.modal-footer {
	display: flex;
	justify-content: flex-end;
	gap: 10px;
}

.modal-footer button {
	padding: 10px 20px;
	font-size: 1rem;
	cursor: pointer;
	border: none;
	border-radius: 5px;
}

.modal-footer .save-btn {
	background-color: #27ae60;
	color: white;
}

.modal-footer .save-btn:hover {
	background-color: #1e8449;
}

.modal-footer .cancel-btn {
	background-color: #e74c3c;
	color: white;
}

.modal-footer .cancel-btn:hover {
	background-color: #c0392b;
}


</style>
</head>
<body>
	<!-- Sidebar -->
	<div class="sidebar" id="sidebar">
		<div class="sidebar-header">
			<span>Menu</span>
		</div>
		<div class="sidebar-buttons">
			<button onclick="showModal('add')">+ Add New Sheet</button>
			<button onclick="showModal('open')">Open Existing Sheet</button>
			<button onclick="toggleRecentSheets()">Recent Sheets</button>
			<div class="recent-sheets" id="recent-sheets">
				<div class="recent-sheet" onclick="showSheetDetails('Sheet 1')">Sheet
					1</div>
				<div class="recent-sheet" onclick="showSheetDetails('Sheet 2')">Sheet
					2</div>
				<div class="recent-sheet" onclick="showSheetDetails('Sheet 3')">Sheet
					3</div>
				<div class="recent-sheet" onclick="showSheetDetails('Sheet 4')">Sheet
					4</div>
				<div class="recent-sheet" onclick="showSheetDetails('Sheet 5')">Sheet
					5</div>
			</div>
		</div>
	</div>

	<!-- Main Content -->
	<div class="main-content">
		<h2>Process Management System</h2>
		<div id="sheet-details" class="sheet-details">
			<h3>Click on Recent sheet to see details</h3>
		</div>
	</div>

	<!-- Modal -->
	<div class="modal" id="modal">
		<div class="modal-content">
			<h2 id="modal-title">Add New Sheet</h2>
			<div class="modal-body">
				<div class="input-field">
					<label for="fileName">File Name:</label> <input type="text"
						id="fileName" placeholder="Enter file name">
				</div>
				<div class="input-field">
					<label for="version">Version:</label> <input type="text"
						id="version" placeholder="Enter version">
				</div>
				<div class="input-field">
					<label for="date">Date:</label> <input type="date" id="date">
				</div>
				<div class="input-field">
					<label for="department">Department:</label> <input type="text"
						id="department" placeholder="Enter department">
				</div>
				<div class="input-field">
					<label for="designNo">Design No:</label> <input type="text"
						id="designNo" placeholder="Enter design number">
				</div>
				<div class="input-field">
					<label for="floor">Floor:</label> <input type="text" id="floor"
						placeholder="Enter floor">
				</div>
				<div class="input-field">
					<label for="lastUpdatedBy">Last Updated By:</label> <input
						type="text" id="lastUpdatedBy" placeholder="Enter last updated by">
				</div>
			</div>
			<div class="modal-footer">
				<button class="save-btn" onclick="saveChanges()">Save</button>
				<button class="cancel-btn" onclick="closeModal()">Cancel</button>
			</div>
		</div>
	</div>


	<!-- JavaScript -->
	<script>
		function toggleRecentSheets() {
			const recentSheets = document.getElementById('recent-sheets');
			recentSheets.style.display = (recentSheets.style.display === 'flex') ? 'none'
					: 'flex';
		}

		function showModal(type) {
			const modal = document.getElementById('modal');
			const modalTitle = document.getElementById('modal-title');
			modalTitle.innerText = type === 'add' ? 'Add New Sheet'
					: 'Open Existing Sheet';
			modal.classList.add('show');
		}

		function closeModal() {
			document.getElementById('modal').classList.remove('show');
		}

		function showSheetDetails(sheetName) {
			const details = document.getElementById('sheet-details');
			details.innerHTML = `<h3>${sheetName} Details</h3><p>Some details about ${sheetName}</p>`;
		}

		function saveChanges() {
			alert('Changes Saved!');
			closeModal();
		}
	</script>
</body>
</html>