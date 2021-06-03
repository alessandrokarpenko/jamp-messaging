package com.jamp.messaging.messaging;

import com.jamp.messaging.dto.MessengerEventDto;
import com.jamp.messaging.dto.RequestEventDto;

public interface MessageListener {

    void createEvent(RequestEventDto message);

    void updateEvent(MessengerEventDto message);

    void deleteEvent(String eventId);

}
