package com.jamp.messaging.dto;

import java.util.List;
import java.util.Random;

public enum EventType {
    WORKSHOP,
    TECH_TALK;

    private static final List<EventType> VALUES = List.of(values());
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static EventType random() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
