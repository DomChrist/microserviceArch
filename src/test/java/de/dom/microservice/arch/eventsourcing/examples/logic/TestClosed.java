package de.dom.microservice.arch.eventsourcing.examples.logic;

import de.dom.microservice.arch.eventsourcing.command.AbstractDomainCommand;
import de.dom.microservice.arch.eventsourcing.event.AbstractDomainEvent;

import java.time.LocalDateTime;

public class TestClosed extends AbstractDomainEvent {

    private LocalDateTime created = null;

    public TestClosed(String reference , LocalDateTime localDateTime) {
        this.reference = reference;
        created = localDateTime;
    }

    public TestClosed() {
    }

    @Override
    public String getEventGroup() {
        return "test";
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public static TestClosed of( CloseTest cmd ){
        return new TestClosed(cmd.getReference(),LocalDateTime.now());
    }

}
