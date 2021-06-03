package com.jamp.messaging.rest;

import com.jamp.messaging.api.EventService;
import com.jamp.messaging.dto.Event;
import com.jamp.messaging.dto.RequestEventDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@RestController
@RequestMapping("/api/v1/events")
@Tag(name = "Event service", description = "API for interaction with events")
public class EventServiceController {

    @Autowired
    EventService eventService;

    @Autowired()
    @Qualifier("mvcConversionService")
    private ConversionService conversionService;

    @GetMapping("/{id}")
    @Operation(summary = "Find event by id", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200"),
            @ApiResponse(description = "Event is not found", responseCode = "404")})
    public ResponseEntity<Event> getEvent(@PathVariable Long id) {
        return eventService.getEvent(id).map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Event with id: %s not found", id)
                ));
    }

    @GetMapping()
    @Operation(summary = "Find all events", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200")})
    public ResponseEntity<List<Event>> getAllEvents() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an event", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "204")})
    public ResponseEntity<Void> deleteEventById(@PathVariable long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping()
    @Operation(summary = "Create an event", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "201")},
            description = "DateTime in yyyy-MM-dd HH-mm format (2020-02-02 02:02)")
    public ResponseEntity<Event> createEvent(@RequestBody RequestEventDto requestEventDto) {
        Event event = conversionService.convert(requestEventDto, Event.class);
        return ResponseEntity.ok(eventService.createEvent(event));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an event", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200"),
            @ApiResponse(description = "Event is not found", responseCode = "404")},
            description = "DateTime in yyyy-MM-dd HH-mm format (2020-02-02 02:02)")
    public ResponseEntity<Event> updateEvent(@PathVariable long id, @RequestBody RequestEventDto requestEventDto) {
        Event event = conversionService.convert(requestEventDto, Event.class);
        return eventService.updateEvent(id, event).map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Event with id: %s not found", id)
                ));
    }
}