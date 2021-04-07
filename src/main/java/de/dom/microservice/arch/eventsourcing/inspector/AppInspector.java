package de.dom.microservice.arch.eventsourcing.inspector;


import de.dom.microservice.arch.eventsourcing.aggregate.AggregateProxy;

import java.util.Set;

public interface AppInspector {

    Set<AggregateProxy<?>> aggregates();

}
