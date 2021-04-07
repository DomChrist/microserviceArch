package de.dom.microservice.arch.eventsourcing.aggregate;

import de.dom.microservice.arch.eventsourcing.events.AbstractDomainEvent;

import java.util.Optional;
import java.util.Set;

public class AggregateProxySelector {

    private final AggregateProxy<?> proxy;

    public AggregateProxySelector(AggregateProxy<?> proxy) {
        this.proxy = proxy;
    }


    public Optional<EventHandlerMethod> findHandlerByEventName(String eventName ){
        Set<EventHandlerMethod> methodSet = proxy.events;
        return methodSet.stream().filter( e->e.isNameEquals(eventName)  ).findFirst();
    }

    public Optional<EventHandlerMethod> findHandlerByEvent(AbstractDomainEvent spec) {
        Set<EventHandlerMethod> methodSet = proxy.events;
        return methodSet.stream()
                .filter( e->e.isEvent( spec ) )
                .findFirst();
    }

    public Optional<CommandHandler> findHandlerFormCommand(Object cmd) {
        Set<CommandHandler> commands = proxy.commands;
        return commands.stream()
                .filter(c -> c.isMethodForCommand(cmd))
                .findFirst();
    }
}
