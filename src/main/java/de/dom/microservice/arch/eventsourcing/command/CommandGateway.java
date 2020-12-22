package de.dom.microservice.arch.eventsourcing.command;

import de.dom.microservice.arch.eventsourcing.aggregates.AggregateProxy;
import de.dom.microservice.arch.eventsourcing.aggregates.Aggregates;
import de.dom.microservice.arch.eventsourcing.config.EventSourcingScope;
import de.dom.microservice.arch.eventsourcing.event.AbstractDomainEvent;
import de.dom.microservice.arch.eventsourcing.gateways.GatewayInitializer;
import de.dom.microservice.arch.eventsourcing.gateways.QueryGateway;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Optional;
import java.util.Set;

public abstract class CommandGateway extends CommandGatewayScope {

    public static <T extends AbstractDomainCommand> void send(T command){
        try{
            sendCommand(command);
        } catch (Throwable throwable) {
            throw new IllegalArgumentException(throwable);
        }
    }

    protected static <T extends AbstractDomainCommand> void sendCommand( T cmd ) throws Throwable {
        Optional<AggregateCommandMethod> method = command(cmd.getClass());
        AggregateCommandMethod aggregateCommandMethod = method.orElseThrow(()->new IllegalArgumentException(String.format("no handler for %s",cmd.getClass().getName())));

        invoke( aggregateCommandMethod , cmd );

    }


    private static <T extends AbstractDomainCommand> void invoke( AggregateCommandMethod m , T cmd ) throws Throwable {

        if( StringUtils.isBlank( cmd.getReference() ) ) throw new IllegalArgumentException("reference is null");

        Optional<?> aggregate = QueryGateway.aggregate(cmd.getReference(), m.aggregate());

        if( aggregate.isEmpty() && m.isConstructor() ){
            m.construct( cmd );
            return;
        } else if( aggregate.isPresent() && m.isMethod() ){
            m.invoke( aggregate.get() , cmd );
            return;
        }
        throw new NoSuchMethodError("Mehtod is not executable");
    }


}
