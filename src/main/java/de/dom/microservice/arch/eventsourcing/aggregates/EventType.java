package de.dom.microservice.arch.eventsourcing.aggregates;

import de.dom.microservice.arch.ddd.AggregateObjectMapper;
import de.dom.microservice.arch.eventsourcing.event.AbstractDomainEvent;

import java.util.Optional;

public class EventType<T extends AbstractDomainEvent> {

    private final Class<T> type;

    public EventType(Class<T> type) {
        this.type = type;
    }

    public Optional<T> reCreate(String payload ){
        return AggregateObjectMapper.toEvent(payload, type);
    }

}
