package de.dom.microservice.arch.eventsourcing.config;

import de.dom.microservice.arch.eventsourcing.aggregate.AggregateProxy;
import de.dom.microservice.arch.eventsourcing.aggregate.CommandHandler;
import de.dom.microservice.arch.eventsourcing.aggregate.ConstructorCommandHandler;
import de.dom.microservice.arch.eventsourcing.aggregate.MethodCommandHandler;
import de.dom.microservice.arch.eventsourcing.inspector.CommandInspector;

import java.util.Optional;

public class CommandGateway {

    private static final Scope scope = Scope.newInstance();


    public static void sendCommand( Object c ){

        String id = CommandInspector.getId(c).orElseThrow( ()-> new IllegalArgumentException("command id is empty or missing") );

        AggregateProxy<?> proxy = scope.aggregates.findAggregateForCommand(c).orElseThrow( ()->new IllegalArgumentException("no aggregate handler for command") );

        Optional<?> aggregate = QueryGateway.aggregate(id, proxy.getAggregate());
        CommandHandler command = proxy.selector().findHandlerFormCommand(c).orElseThrow(() -> new IllegalArgumentException("No handler for Command"));

        if( !aggregate.isPresent() && command.isConstructor() ){
            ConstructorCommandHandler handler = (ConstructorCommandHandler) command;
            Object execute = handler.execute(c);
        } else if( aggregate.isPresent() && !command.isConstructor() ) {
            Object agg = aggregate.get();
            MethodCommandHandler handler = (MethodCommandHandler) command;
            handler.execute( agg , proxy , c );
        } else {
            StringBuilder errorBuilder = new StringBuilder();
            String error = errorBuilder.append("No execution possible for ")
                    .append(c.getClass().getSimpleName())
                    .append(" with CommandHandler ")
                    .append(command.getMethodHandle().toString())
                    .toString();
            throw new IllegalArgumentException(error);
        }

    }



}
