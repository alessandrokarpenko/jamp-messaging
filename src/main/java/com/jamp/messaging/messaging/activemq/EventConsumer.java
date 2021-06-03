package com.jamp.messaging.messaging.activemq;

import com.jamp.messaging.api.EventService;
import com.jamp.messaging.dto.Event;
import com.jamp.messaging.dto.MessengerEventDto;
import com.jamp.messaging.dto.RequestEventDto;
import com.jamp.messaging.messaging.MessageListener;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.core.convert.ConversionService;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Profile("activemq")
public class EventConsumer implements MessageListener {

    @Autowired
    private EventService eventService;

    @Autowired()
    @Qualifier("mvcConversionService")
    private ConversionService conversionService;

    @JmsListener(destination = "${springjms.consumerQueueCreate}")
    public void createEvent(RequestEventDto message) {
        Event event = conversionService.convert(message, Event.class);
        eventService.createEvent(event);
    }

    @JmsListener(destination = "${springjms.consumerQueueUpdate}")
    public void updateEvent(MessengerEventDto message) {
        Event event = conversionService.convert(message, Event.class);
        eventService.updateEvent(event.getEventId(), event);
    }

    @JmsListener(destination = "${springjms.consumerQueueDelete}")
    public void deleteEvent(String eventId) {
        eventService.deleteEvent(Long.parseLong(eventId));
    }
}

