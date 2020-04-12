package de.dom.microservice.arch.eventsourcing.command;

import de.dom.microservice.arch.eventsourcing.aggregates.AggregateProxy;
import de.dom.microservice.arch.eventsourcing.aggregates.Aggregates;
import de.dom.microservice.arch.eventsourcing.config.EventSourcingScope;
import de.dom.microservice.arch.eventsourcing.event.AbstractDomainEvent;
import de.dom.microservice.arch.eventsourcing.gateways.GatewayInitializer;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Optional;
import java.util.Set;

public abstract class CommandGateway extends CommandGatewayScope {

    public static <T extends AbstractDomainCommand> void send(T command){
        try{
            getInstance().sendCommand(command);
        } catch (Throwable throwable) {
            throw new IllegalArgumentException(throwable);
        }
    }

    private static CommandGateway getInstance(){
        return commandGateway;
    }

    protected <T extends AbstractDomainCommand> void sendCommand( T cmd ) throws Throwable {
        Optional<AggregateCommandMethod> method = commands.stream().filter(e -> e.isCommandHandler(cmd.getClass())).findFirst();
        AggregateCommandMethod aggregateCommandMethod = method.orElseThrow(()->new IllegalArgumentException(String.format("no handler for %s",cmd.getClass().getName())));
        Class<Object> aClass = aggregateCommandMethod.aggregate();
        String reference = cmd.getReference();
        Optional<?> aggregate = queryGateway.aggregate(reference, aClass);

        if( aggregate.isEmpty() && !aggregateCommandMethod.isMethod() ){
            Object construct = aggregateCommandMethod.construct(cmd);
        } else {
            aggregateCommandMethod.invoke(aggregate.get(),cmd);
        }
    }

}
