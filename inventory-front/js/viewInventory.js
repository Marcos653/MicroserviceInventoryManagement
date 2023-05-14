fetch('http://localhost:8081/api/inventory')
    .then(response => response.json())
    .then(data => {
        let inventoryList = document.getElementById('inventory-list');
        data.forEach(inventory => {
            let inventoryDiv = document.createElement('div');
            inventoryDiv.innerHTML = `
                <h2>Inventory ID: ${inventory.id}</h2>
                <p>Name: ${inventory.name}</p>
                <p>Price: ${inventory.price}</p>
                <p>Quantity: ${inventory.quantity}</p>
            `;
            inventoryList.appendChild(inventoryDiv);
        });
    })
    .catch(error => console.error('Error:', error));
