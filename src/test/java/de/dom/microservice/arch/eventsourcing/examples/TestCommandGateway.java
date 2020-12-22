package de.dom.microservice.arch.eventsourcing.examples;

import de.dom.microservice.arch.eventsourcing.command.CommandGateway;
import de.dom.microservice.arch.eventsourcing.gateways.QueryGateway;

public class TestCommandGateway extends CommandGateway {

    public TestCommandGateway(QueryGateway queryGateway) {
        CommandGateway.setQueryGateway(queryGateway);
    }

    public TestCommandGateway() {

    }
}
