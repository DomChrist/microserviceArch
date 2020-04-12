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
import java.util.Set;

public class CommandGatewayScope {

    protected static CommandGateway commandGateway;
    protected static QueryGateway queryGateway;

    protected static List<AggregateCommandMethod> commands = new ArrayList<>();

    static {
        Set<Class<?>> aggregates = Aggregates.aggregates();
        for( Class<?> a : aggregates ){
            List<AggregateCommandMethod> commandMethods = Aggregates.commandMethods(a);
            commands.addAll(commandMethods);
        }
    }


    protected static void setCommandGateway( CommandGateway gateway ){
        commandGateway = gateway;
    }

    protected static void setQueryGateway(QueryGateway gateway){ queryGateway = gateway;}
}
