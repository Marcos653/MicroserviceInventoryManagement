package orderapi.service;

import lombok.RequiredArgsConstructor;
import orderapi.dto.OrderRequest;
import orderapi.dto.OrderResponse;
import orderapi.model.Order;

import orderapi.repository.OrderRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final RabbitTemplate rabbitTemplate;
    private static final String ORDER_QUEUE_NAME = "order.queue";
    private static final String CONFIRMATION_QUEUE_NAME = "confirmation.queue";
    private static final String UNAVAILABLE_QUEUE_NAME = "unavailable.queue";

    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream().map(OrderResponse::of).collect(Collectors.toList());
    }

    public OrderResponse getOrderById(Long id) {
        return OrderResponse.of(orderRepository.findById(id).get());
    }

    @Transactional
    public OrderResponse createOrder(OrderRequest request) {
        var order = Order.of(request);
        order.setStatus("pending");
        var savedOrder = orderRepository.save(order);

        rabbitTemplate.convertAndSend(ORDER_QUEUE_NAME, savedOrder);

        return OrderResponse.of(savedOrder);
    }

    @RabbitListener(queues = CONFIRMATION_QUEUE_NAME)
    public void receiveConfirmation(Order order) {
        order.setStatus("confirmed");
        orderRepository.save(order);
    }

    @RabbitListener(queues = UNAVAILABLE_QUEUE_NAME)
    public void receiveUnavailable(Order order) {
        order.setStatus("unavailable");
        orderRepository.save(order);
    }

    @Transactional
    public OrderResponse updateOrder(OrderRequest request, Long id) {
        var order = orderRepository.findById(id).get();
        BeanUtils.copyProperties(request, order);
        return OrderResponse.of(orderRepository.save(order));
    }

    @Transactional
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

}
