package de.dom.microservice.arch.eventsourcing.config;

import de.dom.microservice.arch.eventsourcing.aggregates.AggregateProxy;
import de.dom.microservice.arch.eventsourcing.aggregates.Aggregates;
import de.dom.microservice.arch.eventsourcing.aggregates.EventMethod;
import de.dom.microservice.arch.eventsourcing.bus.EventBus;
import de.dom.microservice.arch.eventsourcing.bus.EventPublisher;
import de.dom.microservice.arch.eventsourcing.event.AbstractDomainEvent;
import de.dom.microservice.arch.eventsourcing.event.AggregateLifecycle;
import de.dom.microservice.arch.eventsourcing.gateways.QueryGateway;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public abstract class EventSourcingScope {

    protected static Set<AggregateProxy> aggregateProxies = new HashSet<>();

    static {
        aggregateProxies = Aggregates.aggregates().stream()
                .map(AggregateProxy::new)
                .collect(Collectors.toSet());
    }

    protected static AggregateLifecycle aggregateLifecycle;
    protected static EventBus eventBus;
    protected static EventPublisher publisher;

    protected static Optional<AggregateProxy> of(AbstractDomainEvent e){
        return aggregateProxies.stream()
                .filter(a->a.isAggregateEvent(e))
                .findFirst();
    }

}
