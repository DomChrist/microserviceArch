package de.dom.microservice.arch.eventsourcing.examples;

import de.dom.microservice.arch.eventsourcing.bus.EventBus;
import de.dom.microservice.arch.eventsourcing.bus.EventPublisher;
import de.dom.microservice.arch.eventsourcing.event.AggregateLifecycle;

public class DefaultAggregateLifecycle extends AggregateLifecycle {


    public DefaultAggregateLifecycle(EventBus eventBus, EventPublisher publisher) {
        AggregateLifecycle.setEventBus(eventBus);
        AggregateLifecycle.setLifecycle(this);
        AggregateLifecycle.setEventPublisher(publisher);
    }
}
