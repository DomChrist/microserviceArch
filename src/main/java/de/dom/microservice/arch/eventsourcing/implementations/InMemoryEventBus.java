package de.dom.microservice.arch.eventsourcing.implementations;

import de.dom.microservice.arch.eventsourcing.aggregates.AggregateProxy;
import de.dom.microservice.arch.eventsourcing.bus.EventBus;
import de.dom.microservice.arch.eventsourcing.bus.EventBusMessage;
import de.dom.microservice.arch.eventsourcing.bus.EventBusResult;
import de.dom.microservice.arch.eventsourcing.event.AbstractDomainEvent;
import de.dom.microservice.arch.eventsourcing.eventstore.EventStoreEntity;
import de.dom.microservice.arch.eventsourcing.eventstore.EventStoreInterface;

import java.util.List;

public class InMemoryEventBus implements EventBus {

    private final EventStoreInterface eventStore;

    public InMemoryEventBus(EventStoreInterface eventStore) {
        this.eventStore = eventStore;
    }

    @Override
    public EventBusResult read(String key, Class<?> aggregate) {
        List<EventStoreEntity> entities = eventStore.aggregate(key, aggregate);
        AggregateProxy proxy = new AggregateProxy(aggregate);
        List<AbstractDomainEvent> abstractDomainEvents = proxy.convertAggregateEvents(entities);
        return new EventBusResult(abstractDomainEvents);
    }

    @Override
    public void apply(EventBusMessage message) {
        long sequence = eventStore.sequence(message.key(),message.getAggregate());
        EventStoreEntity from = EventStoreEntity.from(message.getAggregate(), message.getEvent(), sequence);
        eventStore.save(from);
    }
}
