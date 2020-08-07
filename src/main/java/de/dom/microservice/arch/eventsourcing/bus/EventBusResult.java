package de.dom.microservice.arch.eventsourcing.bus;

import de.dom.microservice.arch.eventsourcing.event.AbstractDomainEvent;

import java.util.ArrayList;
import java.util.List;

public class EventBusResult {

    private List<? extends AbstractDomainEvent> events = new ArrayList<>();

    public EventBusResult(List<AbstractDomainEvent> abstractDomainEvents) {
        this.events = abstractDomainEvents;
    }

    public static EventBusResult empty(){
        return new EventBusResult(new ArrayList<>());
    }

    public List<? extends AbstractDomainEvent> getEvents() {
        return events;
    }

    public void setEvents(List<? extends AbstractDomainEvent> events) {
        this.events = events;
    }

    public boolean isEmpty(){
        return ( events == null || events.isEmpty() );
    }
}
