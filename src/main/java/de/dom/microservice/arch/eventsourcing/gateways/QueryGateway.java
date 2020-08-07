package de.dom.microservice.arch.eventsourcing.gateways;

import de.dom.microservice.arch.eventsourcing.aggregates.AggregateProxy;
import de.dom.microservice.arch.eventsourcing.aggregates.ApplyFlow;
import de.dom.microservice.arch.eventsourcing.bus.EventBus;
import de.dom.microservice.arch.eventsourcing.bus.EventBusResult;
import de.dom.microservice.arch.eventsourcing.event.AbstractDomainEvent;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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

    public static <T> Optional<T> aggregate(String reference, Class<T> clazz , Predicate<AbstractDomainEvent> eventFilter ) {
        EventBusResult read = eventBus.read(reference, clazz);
        if(read.isEmpty() ) return Optional.empty();
        List<? extends AbstractDomainEvent> events = read.getEvents().stream().filter(eventFilter).collect(Collectors.toList());
        AggregateProxy proxy = new AggregateProxy(clazz);
        ApplyFlow applyFlow = proxy.applyAll(events);
        T instance = (T) applyFlow.getAggregate().getInstance();
        return Optional.of(instance );
    }

}
