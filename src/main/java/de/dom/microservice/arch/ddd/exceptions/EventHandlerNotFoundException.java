package de.dom.microservice.arch.ddd.exceptions;

import de.dom.microservice.arch.eventsourcing.event.AbstractDomainEvent;

public class EventHandlerNotFoundException extends RuntimeException {

    public EventHandlerNotFoundException(String message) {
        super(message);
    }

    public EventHandlerNotFoundException(AbstractDomainEvent e) {
        super(String.format("No event handler found for event %s", e.getClass().getSimpleName()));
    }
}
