package com.jamp.messaging.impl;

import com.google.gson.Gson;
import com.jamp.messaging.api.EventService;
import com.jamp.messaging.dto.Event;
import com.jamp.messaging.messaging.MessageSender;
import com.jamp.messaging.repo.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    @Autowired
    EventRepository eventRepository;

    @Autowired
    MessageSender messageSender;

    Gson gson = new Gson();

    @Override
    public Event createEvent(Event event) {
        messageSender.send(gson.toJson(event));
        return eventRepository.save(event);
    }

    @Override
    public Optional<Event> updateEvent(Long id, Event event) {
        Optional<Event> optionalEvent = eventRepository.findById(id);
        if (optionalEvent.isPresent()) {
            Event foundEvent = optionalEvent.get();
            foundEvent.setTitle(event.getTitle());
            foundEvent.setPlace(event.getPlace());
            foundEvent.setSpeaker(event.getSpeaker());
            foundEvent.setEventType(event.getEventType());
            foundEvent.setDateTime(event.getDateTime());
            eventRepository.save(foundEvent);
            messageSender.send(gson.toJson(foundEvent));
            return Optional.of(foundEvent);
        } else {
            messageSender.send("Not updated");
            return Optional.empty();
        }
    }

    @Override
    public Optional<Event> getEvent(Long id) {
        Optional<Event> event = eventRepository.findById(id);
        if (event.isPresent()) {
            messageSender.send(gson.toJson(event.get()));
        } else {
            messageSender.send("Not found event with id: " + id);
        }
        return event;
    }

    @Override
    public void deleteEvent(Long id) {
        messageSender.send("Deleted: " + id);
        eventRepository.deleteById(id);
    }

    @Override
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }
}
