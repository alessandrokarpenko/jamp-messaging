package com.jamp.messaging.api;

import com.jamp.messaging.dto.Event;

import java.util.List;
import java.util.Optional;

public interface EventService {

    Event createEvent(Event event);
    Optional<Event> updateEvent(Long id, Event event);
    Optional<Event> getEvent(Long id);
    void deleteEvent(Long id);
    List<Event> getAllEvents();

}
