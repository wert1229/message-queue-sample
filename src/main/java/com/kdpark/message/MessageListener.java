package com.kdpark.message;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class MessageListener {
    @RabbitListener(queues = RabbitConfig.QUEUE_NANE)
    public void receiveOrder(Message message) {
        System.out.println(message);
    }
}
