package com.kdpark.message;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageListener {

    private final RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = RabbitConfig.REQUEST_QUEUE)
    public void receiveOrder(Message message) {
        System.out.println(message);
        try {
            Thread.sleep(3000);
            rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE, "result.coffee.first", new String(message.getBody()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
