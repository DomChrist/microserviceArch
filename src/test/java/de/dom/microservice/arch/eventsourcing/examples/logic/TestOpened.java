package de.dom.microservice.arch.eventsourcing.examples.logic;

import de.dom.microservice.arch.eventsourcing.event.AbstractDomainEvent;

import java.time.LocalDateTime;

public class TestOpened extends AbstractDomainEvent {

    private LocalDateTime created = LocalDateTime.now();

    @Override
    public String getEventGroup() {
        return "test";
    }

    public TestOpened(String ref) {
        this.reference = ref;
    }

    public TestOpened() {

    }

    public LocalDateTime getCreated() {
        return created;
    }

}
