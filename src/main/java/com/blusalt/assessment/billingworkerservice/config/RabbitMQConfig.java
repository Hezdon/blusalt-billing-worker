package com.blusalt.assessment.billingworkerservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class RabbitMQConfig {

    @Value("${billing.queue.in}") String billingQueueIn;

    @Value("${billing.routing.key}") String billingWorkerRoutingKey;

    @Value("${spring.rabbitmq.host}") String rabbitmqUrl;

    @Value("${spring.rabbitmq.port}") int rabbitmqPort;

    @Value("${spring.rabbitmq.username}") String rabbitmqUsername;

    @Value("${spring.rabbitmq.password}") String rabbitmqPassword;

    @Value("${billing.worker.exchange}") String billingWorkerExchange;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(rabbitmqUrl);
        connectionFactory.setUsername(rabbitmqUsername);
        connectionFactory.setPassword(rabbitmqPassword);
        connectionFactory.setPort(rabbitmqPort);
        return connectionFactory;
    }


    @Bean
    public Queue billingWorkerQueueIn() {
        return new Queue(billingQueueIn, true);
    }

    @Bean
    DirectExchange billingWorkerExchange() {
        return new DirectExchange(billingWorkerExchange);
    }

    @Bean
    Binding billingWorkerBinding(Queue QueueIn, DirectExchange exchange) {
        return BindingBuilder.bind(QueueIn).to(exchange).with(billingWorkerRoutingKey);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}
