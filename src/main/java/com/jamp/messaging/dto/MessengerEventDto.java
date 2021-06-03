package com.jamp.messaging.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessengerEventDto {

    private long eventId;

    private String title;

    private String place;

    private String speaker;

    private String eventType;

    private String dateTime;
}
