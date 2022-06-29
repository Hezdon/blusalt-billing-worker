package com.blusalt.assessment.billingworkerservice.component;

import com.blusalt.assessment.billingworkerservice.dto.Fund;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class BillingWorkerQueueConsumer {

    @Autowired
    BillingWorkerQueueSender billingWorkerQueueSender;

    @RabbitListener(queues = {"${billing.queue.out}"})
    public void receive(@Payload Fund fund) {

        log.info("Message received : " + fund);

        charge();
        fund.setStatus("Success");
        billingWorkerQueueSender.send(fund);
    }

    void charge() {
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        }catch(Exception ex){
            log.error("An error occur : {}", ex.getMessage());
        }
    }
}

