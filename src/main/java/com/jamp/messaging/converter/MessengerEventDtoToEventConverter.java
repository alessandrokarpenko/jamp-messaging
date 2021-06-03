package com.jamp.messaging.converter;

import com.jamp.messaging.dto.Event;
import com.jamp.messaging.dto.EventType;
import com.jamp.messaging.dto.MessengerEventDto;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MessengerEventDtoToEventConverter implements Converter<MessengerEventDto, Event> {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Override
    public Event convert(MessengerEventDto messengerEventDto) {
        return Event.builder()
                .eventId(messengerEventDto.getEventId())
                .title(messengerEventDto.getTitle())
                .place(messengerEventDto.getPlace())
                .speaker(messengerEventDto.getSpeaker())
                .eventType(EventType.valueOf(messengerEventDto.getEventType().toUpperCase()))
                .dateTime(LocalDateTime.parse(messengerEventDto.getDateTime(), formatter))
                .build();
    }
}
