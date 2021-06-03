package com.jamp.messaging.dto;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Expose
    private long eventId;

    @Expose
    private String title;

    @Expose
    private String place;

    @Expose
    private String speaker;

    @Expose
    private EventType eventType;

    @Expose
    private LocalDateTime dateTime;

}