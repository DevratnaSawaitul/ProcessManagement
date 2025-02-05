function loadSingleSheets(sheet) {
	const apiUrl = window.location.origin + "/ProcessManager/webapi/sheets/showSheets";
	const jsonData = JSON.stringify({ load_type: "singleSheet", file_name: sheet });

	fetch(apiUrl, {
		method: "POST",
		headers: { "Content-Type": "application/json" },
		body: jsonData
	})
		.then(response => response.json())
		.then(data => {
			if (data.success && data.sheets.length > 0) {
				populateSingleTable(data.sheets);
			} else {
				document.getElementById("sheets-table-body-single-sheet").innerHTML =
					"<tr><td colspan='8'>No sheets available to display.</td></tr>";
			}

			if (data.sheet_process && data.sheet_process.length > 0) {
				populateSheetProcesses(data.sheet_process);
			} else {
				document.getElementById("sheet-process-container").innerHTML =
					"<p>No processes found. Click 'Add Process' to create one.</p>";
			}
		})
		.catch(error => console.error("Error fetching sheet data:", error));
}

function populateSingleTable(sheets) {
	const tableBody = document.getElementById("sheets-table-body-single-sheet");
	tableBody.innerHTML = "";

	sheets.forEach(sheet => {
		tableBody.innerHTML += `
            <tr>
                <td>${sheet.file_name}</td>
                <td>${sheet.design_no}</td>
                <td>${sheet.department}</td>
                <td>${sheet.floor}</td>
                <td>${sheet.date}</td>
                <td>${sheet.last_updated_by}</td>
                <td>${sheet.date_of_last_update}</td>
                <td>${sheet.version}</td>
            </tr>`;
	});
}

function populateSheetProcesses(sheetProcesses) {
	const container = document.getElementById("sheet-process-container");
	container.innerHTML = "";

	sheetProcesses.forEach(process => {
		let stepsHtml = "";
		if (process.steps.length > 0) {
			process.steps.forEach(step => {
				stepsHtml += `
                    <tr>
                        <td>${step.step_number}</td>
						<td>${step.subprocess_name}</td>
                        <td>${step.tool_name}</td>
                        <td>${step.tool_spec}</td>
                        <td>${step.skill}</td>
                        <td>${step.time_minutes} min</td>
                        <td class="shrink">
						<span class="action-btn eye-btn" onclick="alert('Tool Details: ${step.tool_name}, Spec: ${step.tool_spec}, Skill: ${step.skill}, Instructions: ${step.special_instruction}')">
						    <font face="Arial">&#128065;</font> <!-- 👁 Eye Icon -->
						</span>
						<span class="action-btn edit-btn" onclick="alert('Edit Step ${step.step_number}')">
						    <font face="Arial">&#x270E;</font> <!-- ✎ Edit Icon -->
						</span>
						<span class="action-btn delete-btn" onclick="alert('Delete Step ${step.step_number}')">
						    <font face="Arial">&#x1F5D1;</font> <!-- 🗑️ Trash Icon -->
						</span>
                        </td>
                    </tr>`;
			});
		} else {
			stepsHtml = `<tr><td colspan="7">No Steps Found</td></tr>`;
		}

		container.innerHTML += `
            <div class="sheet-process">
                <div class="process-header" onclick="toggleSteps('steps-${process.sheet_process_id}')">
                    <span class="toggle-icon">&#9660;</span>
                    <span>${process.process_name}</span>
                    <button class="add-btn" title="Add Step in the Process" onclick="alert('Add New Step Clicked')">+ New Step</button>
                </div>
                <table id="steps-${process.sheet_process_id}" class="steps-table">
                    <thead>
                        <tr>
                            <th>Step No</th>
							<th>Sub Process</th>
                            <th>Tool Name</th>
                            <th>Tool Spec</th>
                            <th>Skill</th>
                            <th>Time</th>
                            <th class="shrink">Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        ${stepsHtml}
                    </tbody>
                </table>
            </div>`;
	});
}

function toggleSteps(tableId) {
	const allTables = document.querySelectorAll(".steps-table");
	allTables.forEach(table => {
		if (table.id !== tableId) {
			table.style.display = "none";
		}
	});

	const table = document.getElementById(tableId);
	table.style.display = table.style.display === "none" ? "table" : "none";
}
