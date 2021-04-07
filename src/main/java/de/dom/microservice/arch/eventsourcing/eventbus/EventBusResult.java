package de.dom.microservice.arch.eventsourcing.eventbus;


import de.dom.microservice.arch.eventsourcing.events.AbstractDomainEvent;

import java.util.ArrayList;
import java.util.List;

public class EventBusResult {

    private Object snapshotAggregate;

    private List<? extends AbstractDomainEvent> events = new ArrayList<>();

    public EventBusResult(List<? extends AbstractDomainEvent> abstractDomainEvents) {
        this.events = abstractDomainEvents;
    }

    public EventBusResult(Object snapshotAggregate, List<? extends AbstractDomainEvent> events) {
        this.snapshotAggregate = snapshotAggregate;
        this.events = events;
    }

    public static EventBusResult empty(){
        return new EventBusResult(new ArrayList<>());
    }

    public List<? extends AbstractDomainEvent> getEvents() {
        return events;
    }

    public void setEvents(List<AbstractDomainEvent> events) {
        this.events = events;
    }

    public boolean isEmpty(){
        return ( events == null || events.isEmpty() );
    }

    public boolean hasSnapshot() {
        return snapshotAggregate != null;
    }

    public Object snapshot(){
        return snapshotAggregate;
    }
}
