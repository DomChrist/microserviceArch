package de.dom.microservice.arch.eventsourcing.config;

import de.dom.microservice.arch.eventsourcing.event.AggregateLifecycle;

public class EventSourcingScopeAware {

    private static AggregateLifecycle lifecycle;

    public static AggregateLifecycle getLifecycle() {
        return lifecycle;
    }

    public void setLifecycle(AggregateLifecycle lifecycle) {
        EventSourcingScopeAware.lifecycle = lifecycle;
    }
}
