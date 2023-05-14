document.getElementById('inventory-form').addEventListener('submit', createInventory);

function createInventory(event) {
    event.preventDefault();

    let name = document.getElementById('inventory-name').value;
    let price = document.getElementById('inventory-price').value;
    let quantity = document.getElementById('inventory-quantity').value;

    fetch('http://localhost:8081/api/inventory', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            name: name,
            price: price,
            quantity: quantity
        })
    })
    .then(response => response.json())
    .then(data => console.log(data))
    .catch(error => console.error('Error:', error));
}
