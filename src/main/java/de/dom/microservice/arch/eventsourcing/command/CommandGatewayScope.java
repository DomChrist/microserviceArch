package de.dom.microservice.arch.eventsourcing.command;

import com.google.common.base.Predicate;
import de.dom.microservice.arch.ddd.annotations.Aggregate;
import de.dom.microservice.arch.eventsourcing.aggregates.Aggregates;
import de.dom.microservice.arch.eventsourcing.bus.EventBus;
import de.dom.microservice.arch.eventsourcing.gateways.GatewayInitializer;
import de.dom.microservice.arch.eventsourcing.gateways.QueryGateway;
import org.reflections.ReflectionUtils;

import javax.management.Query;
import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class CommandGatewayScope {

    protected static QueryGateway queryGateway;

    private static List<AggregateCommandMethod> commands = new ArrayList<>();

    static {
        commands();
    }

    protected static List<AggregateCommandMethod> commands(){
        if( commands == null || commands.isEmpty() ){
            Set<Class<?>> aggregates = Aggregates.aggregates();
            for( Class<?> a : aggregates ){
                commands.addAll( Aggregates.commandMethods(a) );
            }
        }
        return commands;
    }

    protected static Optional<AggregateCommandMethod> command(Class c ){
        return commands().stream().filter( e-> e.isCommandHandler(c)).findFirst();
    }


    protected static void setQueryGateway(QueryGateway gateway){ queryGateway = gateway;}
}
