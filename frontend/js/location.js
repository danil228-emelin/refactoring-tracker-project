import {backendUrl, LOCATIONS_URI} from './constants.js';

document.addEventListener("DOMContentLoaded", async () => {
    await loadLocations();
});

// Function to load locations
async function loadLocations() {
    try {
        const response = await fetch(`${backendUrl}${LOCATIONS_URI}`,{
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${localStorage.getItem('token')}`
            }});
        const locations = await response.json();
        const locationsTable = document.getElementById('tableBody');
        console.log(locations);
        locations.forEach(location => {
            const row = document.createElement('tr');
            row.innerHTML = `<td>${location.id}</td><td>${location.name}</td><td>${location.address}</td><td>${location.type}</td>`;
            locationsTable.appendChild(row);
        });
    } catch (error) {
        console.error('Error loading locations:', error);
    }
}

