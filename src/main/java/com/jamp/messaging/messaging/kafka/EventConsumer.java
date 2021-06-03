package com.jamp.messaging.messaging.kafka;

import com.google.gson.Gson;
import com.jamp.messaging.api.EventService;
import com.jamp.messaging.dto.Event;
import com.jamp.messaging.dto.MessengerEventDto;
import com.jamp.messaging.dto.RequestEventDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.core.convert.ConversionService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Profile("kafka")
public class EventConsumer {

    @Autowired
    private EventService eventService;

    @Autowired()
    @Qualifier("mvcConversionService")
    private ConversionService conversionService;

    Gson gson = new Gson();

    @KafkaListener(topics = "createEventListener", groupId = "${spring.kafka.consumer.group-id}")
    public void createEvent(String message) {
        RequestEventDto requestEventDto = gson.fromJson(message, RequestEventDto.class);
        Event event = conversionService.convert(requestEventDto, Event.class);
        eventService.createEvent(event);
    }

    @KafkaListener(topics = "updateEventListener", groupId = "${spring.kafka.consumer.group-id}")
    public void updateEvent(String message) {
        MessengerEventDto requestEventDto = gson.fromJson(message, MessengerEventDto.class);
        Event event = conversionService.convert(requestEventDto, Event.class);
        eventService.updateEvent(event.getEventId(), event);
    }

    @KafkaListener(topics = "deleteEventListener", groupId = "${spring.kafka.consumer.group-id}")
    public void deleteEvent(String message) {
        eventService.deleteEvent(Long.parseLong(message));
    }
}