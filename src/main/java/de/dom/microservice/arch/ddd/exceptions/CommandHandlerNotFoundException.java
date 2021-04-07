package de.dom.microservice.arch.ddd.exceptions;


import de.dom.microservice.arch.eventsourcing.events.AbstractDomainEvent;

public class CommandHandlerNotFoundException extends RuntimeException {

    public CommandHandlerNotFoundException(String message) {
        super(message);
    }

    public CommandHandlerNotFoundException(AbstractDomainEvent e) {
        super(String.format("No command handler found for command %s", e.getClass().getSimpleName()));
    }
}
