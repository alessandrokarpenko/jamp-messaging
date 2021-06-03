package com.jamp.messaging.converter;

import com.jamp.messaging.dto.Event;
import com.jamp.messaging.dto.EventType;
import com.jamp.messaging.dto.RequestEventDto;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RequestEventDtoToEventConverter implements Converter<RequestEventDto, Event> {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Override
    public Event convert(RequestEventDto requestEventDto) {
        return Event.builder()
                .title(requestEventDto.getTitle())
                .place(requestEventDto.getPlace())
                .speaker(requestEventDto.getSpeaker())
                .eventType(EventType.valueOf(requestEventDto.getEventType().toUpperCase()))
                .dateTime(LocalDateTime.parse(requestEventDto.getDateTime(), formatter))
                .build();
    }
}
