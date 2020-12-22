package de.dom.microservice.arch.eventsourcing.config.builder;

import de.dom.microservice.arch.eventsourcing.bus.EventBus;
import de.dom.microservice.arch.eventsourcing.bus.EventPublisher;
import de.dom.microservice.arch.eventsourcing.event.AggregateLifecycle;

public class AggregateLifecycleProxy extends AggregateLifecycle {


    public AggregateLifecycleProxy(EventBus eventBus, EventPublisher publisher) {
            AggregateLifecycle.setEventBus(eventBus);
            AggregateLifecycle.setLifecycle(this);
            AggregateLifecycle.setEventPublisher(publisher);
    }

}
