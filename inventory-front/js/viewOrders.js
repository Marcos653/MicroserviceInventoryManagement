document.getElementById('load-orders').addEventListener('click', function() {
    fetch('http://localhost:8080/api/orders')
        .then(function(response) {
            if (!response.ok) {
                throw new Error('HTTP error, status = ' + response.status);
            }
            return response.json();
        })
        .then(function(json) {
            var ordersContainer = document.getElementById('orders-container');
            ordersContainer.innerHTML = ''; // Clear the container

            for (var i = 0; i < json.length; i++) {
                var order = json[i];

                var orderDiv = document.createElement('div');
                orderDiv.classList.add('order');

                var idP = document.createElement('p');
                idP.textContent = 'ID: ' + order.id;
                orderDiv.appendChild(idP);

                var statusP = document.createElement('p');
                statusP.textContent = 'Status: ' + order.status;
                orderDiv.appendChild(statusP);

                var itemsDiv = document.createElement('div');
                itemsDiv.classList.add('items');
                for (var j = 0; j < order.items.length; j++) {
                    var item = order.items[j];

                    var itemP = document.createElement('p');
                    itemP.textContent = item.name + ' (x' + item.quantity + ') - $' + item.price;
                    itemsDiv.appendChild(itemP);
                }
                orderDiv.appendChild(itemsDiv);

                ordersContainer.appendChild(orderDiv);
            }
        })
        .catch(function(error) {
            console.log('Fetch error: ' + error.message);
        });
});
