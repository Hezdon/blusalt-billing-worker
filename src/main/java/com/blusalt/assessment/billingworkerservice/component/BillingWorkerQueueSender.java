package com.blusalt.assessment.billingworkerservice.component;

import com.blusalt.assessment.billingworkerservice.dto.Fund;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BillingWorkerQueueSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private DirectExchange exchange;

    @Value("${billing.routing.key}")
    String routingKey;

    public void send(Fund fund) {
        log.info("response : ", fund);
        rabbitTemplate.convertAndSend(exchange.getName(), routingKey, fund);
    }
}
