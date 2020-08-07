package de.dom.microservice.arch.eventsourcing.examples.logic;

import de.dom.microservice.arch.eventsourcing.event.AbstractDomainEvent;

public class TestCreated extends AbstractDomainEvent {

    private String reference;

    @Override
    public String getEventGroup() {
        return "test";
    }

    public TestCreated(String reference) {
        this.reference = reference;
    }

    public TestCreated() {
    }

    @Override
    public String getReference() {
        return reference;
    }
}
