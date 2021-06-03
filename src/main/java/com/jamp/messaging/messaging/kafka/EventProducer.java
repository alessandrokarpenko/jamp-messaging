package com.jamp.messaging.messaging.kafka;

import com.jamp.messaging.messaging.MessageSender;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Profile("kafka")
public class EventProducer implements MessageSender {

    private static final String TOPIC = "events";

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void send(String message) {
        kafkaTemplate.send(TOPIC, message);
    }
}