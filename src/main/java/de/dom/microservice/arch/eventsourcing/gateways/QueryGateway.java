package de.dom.microservice.arch.eventsourcing.gateways;

import de.dom.microservice.arch.eventsourcing.aggregates.AggregateProxy;
import de.dom.microservice.arch.eventsourcing.aggregates.ApplyFlow;
import de.dom.microservice.arch.eventsourcing.bus.EventBus;
import de.dom.microservice.arch.eventsourcing.bus.EventBusResult;

import java.util.Optional;

public abstract class QueryGateway {

    protected static EventBus eventBus;

    public static <T> Optional<T> aggregate(String reference, Class<T> clazz ){
        EventBusResult read = eventBus.read(reference, clazz);
        if(read.isEmpty() ) return Optional.empty();
        AggregateProxy proxy = new AggregateProxy(clazz);
            ApplyFlow applyFlow = proxy.applyAll(read.getEvents());
            T instance = (T) applyFlow.getAggregate().getInstance();
        return Optional.of(instance );
    }

}
