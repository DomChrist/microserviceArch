package de.dom.microservice.arch.eventsourcing.config.builder;

import de.dom.microservice.arch.eventsourcing.bus.EventBus;
import de.dom.microservice.arch.eventsourcing.command.CommandGateway;
import de.dom.microservice.arch.eventsourcing.eventstore.EventStoreInterface;
import de.dom.microservice.arch.eventsourcing.gateways.QueryGateway;

public class GatewayBuilder {

    private final EventSourceBuilder builder;
    private final EventStoreInterface eventStore;
    private final EventBus eventBus;

    private QueryGateway queryGateway;
    private CommandGateway commandGateway;


    public GatewayBuilder(EventSourceBuilder builder, EventStoreInterface eventStore, EventBus eventBus) {
        this.builder = builder;
        this.eventStore = eventStore;
        this.eventBus = eventBus;
    }

    public GatewayBuilder query( QueryGateway g ){
        this.queryGateway = g;
        return this;
    }

    public GatewayBuilder inMemoryQueryGateway(){
        this.queryGateway = new QueryGatewayProxy(eventBus);
        return this;
    }



    public EventSourceBuilder build(){
        commandGateway = new CommandGatewayProxy(queryGateway);
        return this.builder;
    }



}
