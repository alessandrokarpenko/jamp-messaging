package com.jamp.messaging.config;

import com.github.javafaker.Faker;
import com.jamp.messaging.dto.Event;
import com.jamp.messaging.dto.EventType;
import com.jamp.messaging.repo.EventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Date;
import java.time.ZoneId;
import java.util.stream.IntStream;

@Configuration
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);
    private final Faker faker = new Faker();

    @Autowired
    private EventRepository eventRepository;

    @Bean
    CommandLineRunner initDatabase() {

        return args -> {
            eventRepository.deleteAll();
            IntStream.range(0, 3).forEach(i -> eventRepository.save(createNewEvent()));
            eventRepository.findAll().forEach(event -> log.info("Preloaded " + event));
        };
    }

    private Event createNewEvent() {
        return Event.builder()
                .title(faker.book().title())
                .place(faker.address().city())
                .speaker(faker.name().fullName())
                .eventType(EventType.random())
                .dateTime(faker.date()
                        .between(Date.valueOf("2020-01-01"), Date.valueOf("2022-01-01"))
                        .toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
                .build();
    }
}
