package de.dom.microservice.arch.eventsourcing.aggregate;

import de.dom.microservice.arch.eventsourcing.eventbus.EventBusResult;
import de.dom.microservice.arch.eventsourcing.events.AbstractDomainEvent;

import java.util.Optional;

public class AggregateExecutor<T> {

    private final AggregateProxy<T> proxy;


    public AggregateExecutor(AggregateProxy<T> proxy) {
        this.proxy = proxy;
    }

    public T restore(EventBusResult r) throws Throwable {
        if( r == null || r.getEvents() == null || r.getEvents().isEmpty() ) return null;
        T newInstance = proxy.newInstance();

            for(AbstractDomainEvent spec : r.getEvents() ){
                Optional<EventHandlerMethod> byEvent = proxy.selector().findHandlerByEvent(spec);
                if( byEvent.isPresent() ){
                    byEvent.get().getHandle().invoke( newInstance , spec );
                }
            }

            return newInstance;
    }

    public void execute(Object aggregate, AbstractDomainEvent event) {
        Optional<EventHandlerMethod> first = proxy.events.stream()
                .filter(e -> e.isEvent(event))
                .findFirst();
        first.ifPresent(eventHandlerMethod -> eventHandlerMethod.execute(aggregate, event));
    }
}
