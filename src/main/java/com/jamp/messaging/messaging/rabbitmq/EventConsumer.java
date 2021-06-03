package com.jamp.messaging.messaging.rabbitmq;

import com.jamp.messaging.api.EventService;
import com.jamp.messaging.dto.Event;
import com.jamp.messaging.dto.MessengerEventDto;
import com.jamp.messaging.dto.RequestEventDto;
import com.jamp.messaging.messaging.MessageListener;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Profile("rabbitmq")
public class EventConsumer implements RabbitListenerConfigurer, MessageListener {

    @Autowired
    private EventService eventService;

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar rabbitListenerEndpointRegistrar) {

    }

    @Autowired()
    @Qualifier("mvcConversionService")
    private ConversionService conversionService;

    @RabbitListener(queues = "${spring.rabbitmq.createEventQueue}")
    public void createEvent(RequestEventDto message) {
        Event event = conversionService.convert(message, Event.class);
        eventService.createEvent(event);
    }

    @RabbitListener(queues = "${spring.rabbitmq.updateEventQueue}")
    public void updateEvent(MessengerEventDto message) {
        Event event = conversionService.convert(message, Event.class);
        eventService.updateEvent(event.getEventId(), event);
    }

    @RabbitListener(queues = "${spring.rabbitmq.deleteEventQueue}")
    public void deleteEvent(String message) {
        eventService.deleteEvent(Long.parseLong(message));
    }

}
