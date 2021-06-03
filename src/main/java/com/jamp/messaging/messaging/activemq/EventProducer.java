package com.jamp.messaging.messaging.activemq;

import com.jamp.messaging.messaging.MessageSender;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Profile("activemq")
public class EventProducer implements MessageSender {

    private final JmsTemplate jmsTemplate;

    @Value("${springjms.producerQueue}")
    private String queue;

    public void send(String message) {
        jmsTemplate.convertAndSend(queue, message);
    }

}
