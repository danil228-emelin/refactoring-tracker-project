import {backendUrl, LABELS_URI, LOCATIONS_URI, ORDERS_URI} from './constants.js';

document.addEventListener("DOMContentLoaded", async () => {
    addInfo();
    loadTableContent();
});
const modal = document.getElementById("addPersonModal");
const btn = document.getElementById("addPersonBtn");

btn.onclick = function () {
    modal.style.display = "block";
}


window.onclick = function (event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}

btn.onclick = function (event) {
    event.preventDefault(); // Prevent form submission

    fetch(`${backendUrl}${LABELS_URI}`, {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`,
        },
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Something bad happen');
            }
            return response.json();
        })
        .then(data => {
            console.log(data);
            const labelTable = document.getElementById('tableBody');
            const row = document.createElement('tr');
            row.innerHTML = `<td>${data.id}</td><td>${data.ssccCode}</td><td>${data.generationDate}</td>`;
            labelTable.appendChild(row);
            saveTableContent();

        })
        .catch((error) => {
            console.error('Error:', error);
        });
}
function saveTableContent() {
    const labelTable = document.getElementById('tableBody');
    const rows = labelTable.getElementsByTagName('tr');
    const tableData = [];

    for (let row of rows) {
        const cells = row.getElementsByTagName('td');
        const rowData = {
            id: cells[0].innerText,
            ssccCode: cells[1].innerText,
            generationDate: cells[2].innerText
        };
        tableData.push(rowData);
    }

    localStorage.setItem(`tableContent-${localStorage.getItem("userName")}`, JSON.stringify(tableData));
}

function loadTableContent() {
    const tableData = JSON.parse(localStorage.getItem(`tableContent-${localStorage.getItem("userName")}`)) || [];
    const labelTable = document.getElementById('tableBody');

    tableData.forEach(data => {
        const row = document.createElement('tr');
        row.innerHTML = `<td>${data.id}</td><td>${data.ssccCode}</td><td>${data.generationDate}</td>`;
        labelTable.appendChild(row);
    });
}
function addInfo() {
    const tableBody = document.getElementById('user-info-table');


    const newRow = document.createElement('tr');


    const usernameCell = document.createElement('td');
    usernameCell.textContent = localStorage.getItem("userName");

    const roleCell = document.createElement('td');
    roleCell.textContent = localStorage.getItem("role");

    newRow.appendChild(usernameCell);
    newRow.appendChild(roleCell);
    tableBody.appendChild(newRow);
}