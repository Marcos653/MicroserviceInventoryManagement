package inventoryapi.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Value("${inventoryapp.rabbitmq.orderqueue}")
    private String orderQueue;

    @Value("${inventoryapp.rabbitmq.exchange}")
    private String exchange;

    @Value("${inventoryapp.rabbitmq.confirmationqueue}")
    private String confirmationQueue;

    @Value("${inventoryapp.rabbitmq.unavailablequeue}")
    private String unavailableQueue;

    @Bean
    public Queue orderQueue() {
        return new Queue(orderQueue, true);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(exchange);
    }

    @Bean
    public Queue confirmationQueue() {
        return new Queue(confirmationQueue, true);
    }

    @Bean
    public Queue unavailableQueue() {
        return new Queue(unavailableQueue, true);
    }

    @Bean
    public Binding orderBinding(Queue orderQueue, TopicExchange exchange) {
        return BindingBuilder.bind(orderQueue).to(exchange).with(orderQueue.getName());
    }

    @Bean
    public Binding confirmationBinding(Queue confirmationQueue, TopicExchange exchange) {
        return BindingBuilder.bind(confirmationQueue).to(exchange).with(confirmationQueue.getName());
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }

}


