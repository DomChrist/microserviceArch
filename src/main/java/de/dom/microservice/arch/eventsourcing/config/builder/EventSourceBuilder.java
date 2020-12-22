package de.dom.microservice.arch.eventsourcing.config.builder;

import de.dom.microservice.arch.eventsourcing.bus.EventBus;
import de.dom.microservice.arch.eventsourcing.command.CommandGateway;
import de.dom.microservice.arch.eventsourcing.event.AggregateLifecycle;
import de.dom.microservice.arch.eventsourcing.eventstore.EventStoreInterface;
import de.dom.microservice.arch.eventsourcing.implementations.InMemoryEventBus;
import de.dom.microservice.arch.eventsourcing.implementations.InMemoryEventStore;

public class EventSourceBuilder {

    private EventBus eventBus;

    private EventStoreInterface eventStore;

    private GatewayBuilder gatewayBuilder;
    private LifecycleBuilder lifecycleBuilder;

    protected AggregateLifecycle lifecycle;
    protected CommandGateway commandGateway;

    public EventSourceBuilder inMemoryEventStore(){
        this.eventStore = new InMemoryEventStore();
        return this;
    }

    public EventSourceBuilder inMemoryEventBus(){
        if( eventStore == null ) inMemoryEventStore();
        this.eventBus = new InMemoryEventBus( eventStore );
        return this;
    }

    public EventSourceBuilder eventStore( EventStoreInterface eventStore ){
        this.eventStore = eventStore;
        return this;
    }

    public EventSourceBuilder eventBus( EventBus bus ){
        this.eventBus = bus;
        return this;
    }

    public GatewayBuilder GatewayBuilder(){
        gatewayBuilder = new GatewayBuilder(this,eventStore,eventBus);
        return gatewayBuilder;
    }

    public LifecycleBuilder LifecycleBuilder(){
        lifecycleBuilder = new LifecycleBuilder(this,eventBus);
        return lifecycleBuilder;
    }

    public CommandGateway buildCommandGateway(){
        return commandGateway;
    }

    public AggregateLifecycle buildLifecycle(){
        return lifecycle;
    }

}
