package com.jamp.messaging.repo;

import com.jamp.messaging.dto.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
