import {AUTHENTICATION_URI, backendUrl, REGISTRATION_URI} from './constants.js';

// Switch between login and register forms
document.getElementById('switchToRegister').addEventListener('click', function () {
    document.getElementById('loginForm').reset();
    document.getElementById('loginContainer').style.display = 'none';
    document.getElementById('registerContainer').style.display = 'block';
});

document.getElementById('switchToLogin').addEventListener('click', function () {
    document.getElementById('registerForm').reset();
    document.getElementById('registerContainer').style.display = 'none';
    document.getElementById('loginContainer').style.display = 'block';
});

document.getElementById('loginForm').addEventListener('submit', async function (event) {
    event.preventDefault();

    const email = document.getElementById('loginEmail').value;
    const password = document.getElementById('loginPassword').value;

    try {
        const response = await fetch(`${backendUrl}${AUTHENTICATION_URI}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                username: email,
                password: password,
            }),
        });

        const data = await response.json();

        if (response.ok) {
            localStorage.setItem('token', data.token);
            localStorage.setItem('userName', data.username);
            localStorage.setItem('role', data.role);
            localStorage.setItem('userId', data.id);
            console.log(data)
            window.location.href = "../html/main.html"

        } else {
            alert(data.message || 'Login failed');
        }
    } catch (error) {
        console.error('Error during login:', error);
    }
});

// Handle Register form submission
document.getElementById('registerForm').addEventListener('submit', async function (event) {
    event.preventDefault();

    const email = document.getElementById('registerEmail').value;
    const password = document.getElementById('registerPassword').value;
    try {
        const response = await fetch(`${backendUrl}${REGISTRATION_URI}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                username: email,
                password: password
            }),
        });

        const data = await response.json();

        if (response.ok) {
            console.log(data)
            alert("Successfully registered. You can login now!");
        } else {
            alert(data.message || 'Registration failed');
        }
    } catch (error) {
        console.error('Error during registration:', error);
    }
});
