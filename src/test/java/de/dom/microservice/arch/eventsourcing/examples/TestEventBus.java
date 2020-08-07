package de.dom.microservice.arch.eventsourcing.examples;

import de.dom.microservice.arch.eventsourcing.aggregates.AggregateProxy;
import de.dom.microservice.arch.eventsourcing.bus.EventBus;
import de.dom.microservice.arch.eventsourcing.bus.EventBusMessage;
import de.dom.microservice.arch.eventsourcing.bus.EventBusResult;
import de.dom.microservice.arch.eventsourcing.event.AbstractDomainEvent;
import de.dom.microservice.arch.eventsourcing.eventstore.EventStoreEntity;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TestEventBus implements EventBus {

    private TestRepository testRepository;

    public TestEventBus(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    @Override
    public EventBusResult read(String key, Class<?> aggregate) {
        List<EventStoreEntity> entities = testRepository.aggregate(key, aggregate);
        AggregateProxy proxy = new AggregateProxy(aggregate);
        List<AbstractDomainEvent> abstractDomainEvents = proxy.convertAggregateEvents(entities);
        return new EventBusResult(abstractDomainEvents);
    }

    @Override
    public void apply(EventBusMessage message) {
        long sequence = testRepository.sequence(message.key(),message.getAggregate());
        EventStoreEntity from = EventStoreEntity.from(message.getAggregate(), message.getEvent(), sequence);
        testRepository.save(from);
    }

}
