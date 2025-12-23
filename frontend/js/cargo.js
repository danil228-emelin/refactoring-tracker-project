import {backendUrl, CARGO_REQUESTS_URI, CARGO_STATUS_URI, CARGOES_URI, NOTIFICATIONS_URI} from './constants.js';


function checkLocalStorage() {
    if (!localStorage.getItem("userName") || !localStorage.getItem("token") || !localStorage.getItem("role")) {
        window.location.href = '../html/authorization.html';
        alert("Some items don't contain in local storage,pass authentication again please ");
    }
}

document.addEventListener('DOMContentLoaded', () => {
    checkLocalStorage();
    addInfo();
    fetchSsccCodes();
});

function fetchSsccCodes() {
    const tableContentKey = `tableContent-${localStorage.getItem("userName")}`;
    const tableContent = JSON.parse(localStorage.getItem(tableContentKey));

    if (Array.isArray(tableContent)) {
        tableContent.forEach(item => {
            if (item.ssccCode) {
                sendSsccCodeToBackend(item.ssccCode);
            }
        });
    } else {
        console.warn('Нет данных для отправки на бекенд.');
    }
}

function sendSsccCodeToBackend(ssccCode) {
    fetch(`${backendUrl}${CARGOES_URI}/${ssccCode}`, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`,
            'Content-Type': 'application/json'
        },
    })
        .then(response => {
            if (!response.ok) {
                return Promise.reject('Error during cargoes fetching');
            }
            return response.json();
        })
        .then(data => {
            Swal.fire({
                icon: 'success',
                title: 'Success!',
                text: 'Find cargoes'
            }).then(() => {
                addToOrdersTable(data);
            });
        })
        .catch(error => {
            Swal.fire({
                icon: 'error',
                title: 'can not find cargoes by sscc code!',
                text: error.message
            });
        });
}

function addToOrdersTable(data) {
    console.log(data)


    const tableBodyOrders = document.getElementById('tableBodyOrders');

    const newRow = document.createElement('tr');

    const id = document.createElement('td');
    id.textContent = data.id;
    newRow.appendChild(id);

    const name = document.createElement('td');
    name.textContent = data.name;
    newRow.appendChild(name);

    const destinationCenter = document.createElement('td');
    destinationCenter.textContent = data.destinationCenter;
    newRow.appendChild(destinationCenter);


    const receptionCenter = document.createElement('td');
    receptionCenter.textContent = data.receptionCenter;
    newRow.appendChild(receptionCenter)

    const cargoType = document.createElement('td');
    cargoType.textContent = data.cargoType;
    newRow.appendChild(cargoType)

    const cargoStatusDelivery = document.createElement('td');
    cargoStatusDelivery.textContent = data.cargoStatusDelivery;
    newRow.appendChild(cargoStatusDelivery)

    const currentLocationType = document.createElement('td');
    currentLocationType.textContent = data.currentLocationType;
    newRow.appendChild(currentLocationType)


    const currentLocation = document.createElement('td');
    currentLocation.textContent = data.currentLocation;
    newRow.appendChild(currentLocation)

    const weight = document.createElement('td');
    weight.textContent = data.weight;
    newRow.appendChild(weight)

    const ssccCode = document.createElement('td');
    ssccCode.textContent = data.ssccCode;
    newRow.appendChild(ssccCode)

    const clientUserName = document.createElement('td');
    clientUserName.textContent = data.clientUsername;
    newRow.appendChild(clientUserName)


    tableBodyOrders.appendChild(newRow);
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

    const cargoRequestId = document.getElementById("nameT").value;
    const weight = document.getElementById("receptionCenter").value;
    const orderId = document.getElementById("destinationCenter").value;
    const labelId = document.getElementById("labelId").value;

    fetch(`${backendUrl}${CARGOES_URI}`, {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`,
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            "cargoRequestId": cargoRequestId,
            "weight": weight,
            "orderId": orderId,
            "labelId": labelId
        })
    })
        .then(response => {
            if (!response.ok) {
                return response.json().then(errData => {
                    // Extract the error message from the response JSON
                    const errorMessage = errData.message || 'Error registering cargo';
                    // Reject with the error message for the catch block
                    return Promise.reject(errorMessage);
                });
            }
            return response.json(); // Assuming the server returns a JSON response
        })
        .then(data => {
            // Show success alert
            Swal.fire({
                icon: 'success',
                title: 'Success!',
                text: 'Cargo registered successfully!',
                showCloseButton: true,
            });
        })
        .catch((error) => {
            // Show error alert
            Swal.fire({
                icon: 'error',
                title: 'Error!',
                text: error,
                showCloseButton: true,
            });
        });

    modal.style.display = "none"; // Close modal after submission
}


const modal1 = document.getElementById("upgradePersonModal");
const btn1 = document.getElementById("upgradePersonBtn");
const form1 = document.getElementById("upgradePersonForm");

btn1.onclick = function () {
    modal1.style.display = "block";
}



window.onclick = function (event) {
    if (event.target == modal1) {
        modal1.style.display = "none";
    }
}


form1.onsubmit = function (event) {
    event.preventDefault(); // Prevent form submission
    const scc = document.getElementById("Upgrade").value;
    console.log(scc)

    const locationId = document.getElementById("location").value;
    const cargoStatusDelivery = document.getElementById("cargoStatusDelivery").value;

    fetch(`${backendUrl}${CARGO_STATUS_URI}/${scc}`, {
        method: 'PATCH',
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`,
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
        "locationId": `${locationId}`,
        "cargoStatusDelivery": `${cargoStatusDelivery}`
        })
    })
        .then(response => {
            if (!response.ok) {
                return response.json().then(errData => {
                    // Extract the error message from the response JSON
                    const errorMessage = errData.message || 'Error updating cargo';
                    // Reject with the error message for the catch block
                    return Promise.reject(errorMessage);
                });
            }
            return response.json(); // Assuming the server returns a JSON response
        })
        .then(() => {
            Swal.fire({
                icon: 'success',
                title: 'Success!',
                text: 'Cargo updated successfully!',
                showCloseButton: true,
            });
        })
        .catch((error) => {
            // Show error alert
            Swal.fire({
                icon: 'error',
                title: 'Error!',
                text: error,
                showCloseButton: true,
            });
        });

    modal1.style.display = "none"; // Close modal after submission
}

console.log(localStorage.getItem("token"));