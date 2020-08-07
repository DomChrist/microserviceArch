package de.dom.microservice.arch.eventsourcing.examples;

import de.dom.microservice.arch.eventsourcing.bus.EventBus;
import de.dom.microservice.arch.eventsourcing.gateways.QueryGateway;

import javax.management.Query;

public class TestQueryGateway extends QueryGateway {

    public TestQueryGateway(EventBus bus){
        QueryGateway.eventBus = bus;
    }
}
