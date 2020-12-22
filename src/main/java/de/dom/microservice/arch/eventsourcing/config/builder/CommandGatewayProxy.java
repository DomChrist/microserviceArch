package de.dom.microservice.arch.eventsourcing.config.builder;

import de.dom.microservice.arch.eventsourcing.command.CommandGateway;
import de.dom.microservice.arch.eventsourcing.gateways.QueryGateway;

public class CommandGatewayProxy extends CommandGateway {

    private final QueryGateway queryGateway;


    public CommandGatewayProxy(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
        CommandGateway.queryGateway = queryGateway;
    }
}
