package de.dom.microservice.arch.eventsourcing.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.dom.microservice.arch.eventsourcing.events.AbstractDomainEvent;
import de.dom.microservice.arch.eventsourcing.events.SignatureGenerator;

public class EventParsingError extends RuntimeException{

    private Exception stack;


    public EventParsingError(Class<AbstractDomainEvent> event , Exception e){
        this(event);
        this.stack = e;
    }

    public EventParsingError(Class<? extends AbstractDomainEvent> aClass, JsonProcessingException e) {
        this(aClass);
        this.stack = e;
    }

    public EventParsingError(Class<? extends AbstractDomainEvent> aClass) {
        super("Parsing error in Event " + SignatureGenerator.signature(aClass));

    }

    public Exception getStack() {
        return stack;
    }
}
