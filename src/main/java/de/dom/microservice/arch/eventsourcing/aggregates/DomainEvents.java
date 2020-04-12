package de.dom.microservice.arch.eventsourcing.aggregates;

import de.dom.microservice.arch.eventsourcing.event.AbstractDomainEvent;

import java.util.Optional;
import java.util.Set;

public class DomainEvents {

    private Class<?> aggregates;
    Set<EventMethod> eventMethods;

    protected DomainEvents( Class<?> aggregate ){
        this.aggregates = aggregate;
        this.eventMethods = Aggregates.eventMethods(aggregate);
    }

    public Class<?> getAggregates() {
        return aggregates;
    }

    public Set<EventMethod> getEventMethods() {
        return eventMethods;
    }

    public <E extends AbstractDomainEvent> Optional<EventMethod> methodFor( E event ){
        return eventMethods.stream()
                .filter( e->e.canHandle(event) )
                .findFirst();
    }


    public Optional<EventType<? extends AbstractDomainEvent>> typeForName(String name) {
        Optional<EventMethod> first = eventMethods.stream()
                .filter(e -> e.getEventName().equals(name))
                .findFirst();
        if( first.isEmpty() )return Optional.empty();
        Class<? extends AbstractDomainEvent> type = first.get().getEventType();
        EventType<? extends AbstractDomainEvent> eventType = new EventType<>(type);
        return Optional.of(eventType);
    }
}
