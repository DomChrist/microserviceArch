package de.dom.microservice.arch.eventsourcing.config.builder;

import de.dom.microservice.arch.eventsourcing.bus.EventBus;
import de.dom.microservice.arch.eventsourcing.bus.EventPublisher;

public class LifecycleBuilder {

    private final EventSourceBuilder builder;

    private final EventBus eventBus;
    private EventPublisher publisher;

    protected LifecycleBuilder(EventSourceBuilder builder, EventBus eventBus){
        this.builder = builder;
        this.eventBus = eventBus;
    }

    public LifecycleBuilder publisher( EventPublisher publisher ){
        this.publisher = publisher;
        return this;
    }

    public LifecycleBuilder emptyPublisher(){
        this.publisher = new EmptyPublisher();
        return this;
    }

    public EventSourceBuilder build(){
        this.builder.lifecycle = new AggregateLifecycleProxy( eventBus , publisher );
        return this.builder;
    }


}
