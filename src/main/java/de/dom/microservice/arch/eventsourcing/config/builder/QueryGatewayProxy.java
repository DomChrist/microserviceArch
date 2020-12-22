package de.dom.microservice.arch.eventsourcing.config.builder;

import de.dom.microservice.arch.eventsourcing.bus.EventBus;
import de.dom.microservice.arch.eventsourcing.gateways.QueryGateway;

public class QueryGatewayProxy extends QueryGateway {

    private final EventBus eventBus;

    public QueryGatewayProxy(EventBus eventBus) {
        this.eventBus = eventBus;
        QueryGateway.init(this.eventBus);
    }

}
