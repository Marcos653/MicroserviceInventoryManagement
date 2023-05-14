# Microservice Inventory Management

Este projeto é um exemplo de um sistema simples de pedidos e inventário, construído como um conjunto de microsserviços usando as seguintes tecnologias:

- Java com Spring Boot para o desenvolvimento dos microsserviços.
- RabbitMQ para a comunicação assíncrona entre os serviços.
- MySQL como banco de dados para armazenar os pedidos e itens de inventário.
- HTML, CSS e JavaScript para a interface de usuário básica.

## Funcionalidades

O sistema possui as seguintes funcionalidades:

- Order Service: permite criar, listar, atualizar e excluir pedidos. Cada pedido contém uma lista de itens.
- Inventory Service: gerencia o inventário, permitindo criar itens com seus respectivos nomes, preços e quantidades disponíveis.
- Comunicação assíncrona: o Order Service envia pedidos para uma fila RabbitMQ, e o Inventory Service consome os pedidos, verificando a disponibilidade de itens em estoque e atualizando o inventário. O Order Service também escuta uma fila de confirmações para atualizar o status dos pedidos.
- Interface de usuário simples: uma interface web básica permite criar pedidos, visualizar o inventário e consultar os pedidos existentes.

## Como executar o projeto

1. Clone o repositório para sua máquina local.
2. Certifique-se de ter o Java, RabbitMQ e MySQL instalados e configurados.
3. Configure as propriedades de conexão do RabbitMQ e do banco de dados nos arquivos de configuração do Order Service e Inventory Service.
4. Execute os microsserviços Order Service e Inventory Service.
5. Acesse a interface de usuário pelo navegador usando a URL apropriada.

## Contribuições

Contribuições são bem-vindas! Se você encontrar algum problema ou tiver sugestões de melhoria, sinta-se à vontade para abrir uma nova issue ou enviar um pull request.

