package de.dom.microservice.arch.eventsourcing.config;

import de.dom.microservice.arch.eventsourcing.aggregate.AggregateProxy;
import de.dom.microservice.arch.eventsourcing.aggregate.Aggregates;
import de.dom.microservice.arch.eventsourcing.eventbus.EventBus;
import de.dom.microservice.arch.eventsourcing.eventbus.EventPublisher;
import de.dom.microservice.arch.eventsourcing.events.SignatureGenerator;

import java.util.Optional;

public class Scope {

    private static Scope scope;
    protected EventPublisher publisher;

    protected Aggregates aggregates;

    protected EventBus eventBus;

    private Scope() {
    }

    protected Scope(EventBus eventBus ) {
        this.eventBus = eventBus;
    }


    public static Scope newInstance() {
        Scope.scope = Optional.ofNullable( scope ).orElse( new Scope() );
        return Scope.scope;
    }

    public Optional<AggregateProxy<?>> proxy(Class<?> clazz ){
        return aggregates.findByName(SignatureGenerator.aggregateSignature(clazz));
    }

}
