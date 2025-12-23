import {backendUrl, CARGO_REQUESTS_URI, CARGOES_URI, NOTIFICATIONS_URI} from './constants.js';


function checkLocalStorage() {
    if (!localStorage.getItem("userName") || !localStorage.getItem("token") || !localStorage.getItem("role")) {
        window.location.href = '../html/authorization.html';
        alert("Some items don't contain in local storage,pass authentication again please ");
    }
}

document.addEventListener('DOMContentLoaded', () => {
    checkLocalStorage();
    addInfo();
    loadUserOrders();
});

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


async function loadUserOrders() {
    try {
        const response = await fetch(`${backendUrl}${CARGO_REQUESTS_URI}/${localStorage.getItem("userName")}`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${localStorage.getItem('token')}`
            }
        }).then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
            .then(data => {

                const OrderTable = document.getElementById('tableBodyOrders');
                console.log(data);
                data.forEach(order => {
                    const row = document.createElement('tr');
                    row.innerHTML = `<td>${order.id}</td><td>${order.name}</td><td>${order.cargoType}</td><td>${order.receptionCenterId}</td><td>${order.destinationCenterId}</td>`;
                    OrderTable.appendChild(row);
                });
            })
    } catch (error) {
        console.error('Error loading locations:', error);
    }
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

    const cargoType = document.getElementById("name").value;
    const name = document.getElementById("nameT").value;

    const receptionCenter = document.getElementById("receptionCenter").value;
    const destinationCenter = document.getElementById("destinationCenter").value;

    let alertMessage = 'Успешно создана заявка на груз!';
    let alertColor = 'success';

    fetch(`${backendUrl}${CARGO_REQUESTS_URI}`, {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`,
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            "name": `${name}`,
            "cargoType": `${cargoType}`,
            "receptionCenter": `${receptionCenter}`,
            "destinationCenter": `${destinationCenter}`
        })
    })
        .then(response => {
            if (response.ok) {
                alertMessage = 'Заявка на груз была успешно создана!';
                alertColor = 'success';
                Swal.fire({
                    icon: 'success',
                    title: 'YES!',
                    text: alertMessage,
                    showCloseButton: true,
                });
                form.reset();

                modal.style.display = "none";
            } else {
                return response.json();
            }
        })
        .then(data => {

            Swal.fire({
                icon: 'error',
                title: 'Error!',
                text: data.message,
                showCloseButton: true,
            });
                modal.style.display = "none";
        })

}


const modal_delete = document.getElementById("deletePersonModal");
const btn_delete = document.getElementById("deletePersonBtn");
const form_delete = document.getElementById("deletePersonForm");

btn_delete.onclick = function () {
    modal_delete.style.display = "block";
}

form_delete.onsubmit = function (event) {
    event.preventDefault();
    const id = document.getElementById("delete_id").value;
    console.log(id);
    fetch(`${backendUrl}${CARGO_REQUESTS_URI}/${id}`, {
        method: 'DELETE',
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        },
    }).then(response => {
        if (!response.ok) {
            alert("Error during deletion");
        } else {
            location.reload();
        }
    })
        .catch((error) => {
            console.error('Error:', error);
            alert(error.message);
        });
    modal_delete.style.display = "none";

}


console.log(localStorage.getItem("token"));