package com.jamp.messaging.messaging.rabbitmq;

import com.jamp.messaging.messaging.MessageSender;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Profile("rabbitmq")
public class EventProducer implements MessageSender {

    private final RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.exchange}")
    private String exchange;

    @Value("${spring.rabbitmq.routingkey}")
    private String routingKey;

    @Override
    public void send(String message) {
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }
}