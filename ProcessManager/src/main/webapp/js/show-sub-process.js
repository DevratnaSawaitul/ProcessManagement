document.addEventListener("DOMContentLoaded", () => {
	loadSubProcesses();
});

/**
 * Fetches all sub-processes from the API and displays them in the table.
 */
function loadSubProcesses() {
	const apiUrl = window.location.origin + "/ProcessManager/webapi/org/show_sub_process";
	const jsonData = JSON.stringify({ load_type: "all" });

	const tableBody = document.getElementById("subprocesses-table-body");
	tableBody.innerHTML = "<tr><td colspan='3'>Loading...</td></tr>";

	fetch(apiUrl, {
		method: "POST",
		headers: { "Content-Type": "application/json" },
		body: jsonData
	})
		.then(response => response.json())
		.then(data => {
			tableBody.innerHTML = "";

			if (data.status && data.sub_process.length > 0) {
				populateSubProcessTable(data.sub_process);
			} else {
				tableBody.innerHTML = "<tr><td colspan='3'>No sub-processes available.</td></tr>";
			}
		})
		.catch(error => {
			console.error("Error fetching sub-process data:", error);
			tableBody.innerHTML = `<tr><td colspan='3'>Error: ${error.message}</td></tr>`;
		});
}

/**
 * Populates the table with the fetched sub-process data.
 * @param {Array} subProcesses - Array of sub-process objects returned from the API.
 */
function populateSubProcessTable(subProcesses) {
	const tableBody = document.getElementById("subprocesses-table-body");
	subProcesses.forEach(subProcess => {
		const row = document.createElement("tr");
		row.innerHTML = `
            <td>${subProcess.process_name}</td>
            <td>${subProcess.subprocess_name}</td>
            <td>${subProcess.active ? "Yes" : "No"}</td>
        `;
		tableBody.appendChild(row);
	});
}
