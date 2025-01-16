    import {backendUrl, CARGO_REQUESTS_URI, CARGOES_URI, INCIDENTS_URI, NOTIFICATIONS_URI} from './constants.js';


    function checkLocalStorage() {
        if (!localStorage.getItem("userName") || !localStorage.getItem("token") || !localStorage.getItem("role")) {
            window.location.href = '../html/authorization.html';
            alert("Some items don't contain in local storage,pass authentication again please ");
        }
    }

    document.addEventListener('DOMContentLoaded', () => {
        checkLocalStorage();
        addInfo();
        fetchIncidents();

    });

    function fetchIncidents() {
        const tableBody = document.getElementById('tableBodyOrders');
        fetch(`${backendUrl}${INCIDENTS_URI}/${localStorage.getItem("userId")}`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${localStorage.getItem('token')}`
            }
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(incidents => {
                tableBody.innerHTML = ''; // Clear existing rows
                if (incidents && incidents.length > 0) {
                    incidents.forEach(incident => {
                        console.log(incident)
                        const row = document.createElement('tr');

                        const idCell = document.createElement('td');
                        idCell.textContent = incident.sscc; // Получите id инцидента

                        const cargoCell = document.createElement('td');
                        cargoCell.textContent = incident.clientUsername; // Предположим, у инцидента есть поле cargo

                        const typeCell = document.createElement('td');
                        typeCell.textContent = incident.incidentType; // Получите тип инцидента

                        const descriptionCell = document.createElement('td');
                        descriptionCell.textContent = incident.description; // Получите описание инцидента


                        const occurrenceDateCell = document.createElement('td');
                        occurrenceDateCell.textContent = new Date(incident.incidentDate).toLocaleDateString(); // Получите дату возникновения

                        row.appendChild(idCell);
                        row.appendChild(cargoCell);
                        row.appendChild(typeCell);
                        row.appendChild(descriptionCell);
                        row.appendChild(occurrenceDateCell);

                        tableBody.appendChild(row);
                    });
                } else {
                    console.log("No incidents found for this user.");
                }
            })
            .catch(error => console.error('Error fetching incidents:', error));
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


    function fetchNotifications() {
        let notificationArea = document.getElementById('notification-area');
        fetch(`${backendUrl}${NOTIFICATIONS_URI}${localStorage.getItem("userId")}`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${localStorage.getItem('token')}`
            }
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                console.log(data);
                notificationArea.innerHTML = '';


                if (data && data.length > 0) {
                    console.log("Some Notifications for user");
                    data.forEach(notification => {
                        displayNotification(notification);
                    });
                    notificationArea.style.display = 'block';
                } else {
                    notificationArea.style.display = 'none';
                }
            })
            .catch(error => console.error('Error fetching notifications:', error));
    }

    function displayNotification(notification) {
        const notificationArea = document.getElementById('notification-area');

        const notificationDiv = document.createElement('div');
        notificationDiv.classList.add('notification');


        if (notification.is_positive) {
            notificationDiv.classList.add('positive');
        } else {
            notificationDiv.classList.add('negative');
        }


        const maxLength = 150;
        let message = notification.description;

        if (message.length > maxLength) {
            message = message.substring(0, maxLength) + '...';
        }

        notificationDiv.textContent = message;


        notificationArea.appendChild(notificationDiv);


        setTimeout(() => {
            notificationDiv.classList.add('enter');
        }, 10);


        setTimeout(() => {
            notificationDiv.style.opacity = '0';
            notificationDiv.style.transform = 'translateY(20px)';
            setTimeout(() => {
                notificationArea.removeChild(notificationDiv);
            }, 500);
        }, 5000);
    }

    //setInterval(fetchNotifications, 15000);
    //fetchNotifications();


    const modal = document.getElementById("addPersonModal");
    const btn = document.getElementById("addPersonBtn");
    const span = document.getElementsByClassName("close")[0];
    const form = document.getElementById("addPersonForm");

    btn.onclick = function () {
        modal.style.display = "block";
    }

    span.onclick = function () {
        modal.style.display = "none";
    }

    window.onclick = function (event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    }

    form.onsubmit = function (event) {
        event.preventDefault(); // Prevent form submission

        const sscc = document.getElementById("nameT").value;

        const description = document.getElementById("receptionCenter").value;
        const type = document.getElementById("destinationCenter").value;
        fetch(`${backendUrl}${INCIDENTS_URI}`, {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${localStorage.getItem('token')}`,
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                "ssccCode": `${sscc}`,
                "description": `${description}`,
                "type": `${type}`
            })
        })
            .then(() => {
                alert("Successfully register incident")
                form.reset();

            })
            .catch((error) => {
                console.error('Error:', error);
            });

        modal.style.display = "none";
    }

    console.log(localStorage.getItem("token"));

