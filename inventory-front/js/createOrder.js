document.getElementById('order-form').addEventListener('submit', function(event) {
    event.preventDefault();

    var name = document.getElementById('item-name').value;
    var price = document.getElementById('item-price').value;
    var quantity = document.getElementById('item-quantity').value;

    var order = {
        items: [
            {
                name: name,
                price: price,
                quantity: quantity
            }
        ]
    };

    fetch('http://localhost:8080/api/orders', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(order)
    }).then(function(response) {
        if (response.ok) {
            alert('Order created successfully!');
        } else {
            alert('Error: ' + response.statusText);
        }
    }).catch(function(error) {
        alert('Error: ' + error);
    });
});
