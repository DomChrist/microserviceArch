package de.dom.microservice.arch.eventsourcing.implementations;

import de.dom.microservice.arch.eventsourcing.bus.EventBus;
import de.dom.microservice.arch.eventsourcing.gateways.QueryGateway;

public class InMemoryQueryGateway extends QueryGateway {

    public InMemoryQueryGateway(EventBus bus) {
        QueryGateway.eventBus = bus;
    }
}
