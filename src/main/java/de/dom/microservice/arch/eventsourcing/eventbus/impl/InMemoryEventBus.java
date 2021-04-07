package de.dom.microservice.arch.eventsourcing.eventbus.impl;


import de.dom.microservice.arch.eventsourcing.aggregate.AggregateProxy;
import de.dom.microservice.arch.eventsourcing.eventbus.EventBus;
import de.dom.microservice.arch.eventsourcing.eventbus.EventBusMessage;
import de.dom.microservice.arch.eventsourcing.eventbus.EventBusResult;
import de.dom.microservice.arch.eventsourcing.events.AbstractDomainEvent;
import de.dom.microservice.arch.eventsourcing.factory.EventItemFactory;

public class InMemoryEventBus implements EventBus {

    private final InMemoryEventStore eventStore;

    public InMemoryEventBus(InMemoryEventStore eventStore) {
        this.eventStore = eventStore;
    }

    @Override
    public EventBusResult read(String key, Class<?> aggregate) {
        AggregateProxy proxy = new AggregateProxy(aggregate);
        EventBusResult eventBusResult = proxy.parser().result(eventStore.items(key, aggregate));
        return eventBusResult;
    }

    @Override
    public void apply(EventBusMessage message) {
        Class<?> aggregate = message.getAggregate();
        AbstractDomainEvent event = message.getEvent();

        long sequence = eventStore.nextSequence(event.getReference(), aggregate);

        InMemoryEventItem eventItem = EventItemFactory.parse(event, sequence, aggregate, new InMemoryEventItem());

        this.eventStore.save( eventItem, aggregate);
    }

}
