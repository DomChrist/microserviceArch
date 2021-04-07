package de.dom.microservice.arch.eventsourcing.snapshot;

import de.dom.microservice.arch.eventsourcing.eventstore.EventStoreItem;

public class AggregateSnapshot {

    private Class aggregateClass;

    private Object aggregate;

    private String payload;

    private long sequence;


    public static AggregateSnapshot of(EventStoreItem item ){
        AggregateSnapshot snapshot = new AggregateSnapshot();
        return snapshot;
    }

}
