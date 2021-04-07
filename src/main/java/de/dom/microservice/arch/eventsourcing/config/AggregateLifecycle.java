package de.dom.microservice.arch.eventsourcing.config;

import de.dom.microservice.arch.ddd.AggregateObjectMapper;
import de.dom.microservice.arch.eventsourcing.aggregate.AggregateProxy;
import de.dom.microservice.arch.eventsourcing.eventbus.EventBusMessage;
import de.dom.microservice.arch.eventsourcing.events.AbstractDomainEvent;
import de.dom.microservice.arch.eventsourcing.exceptions.EventParsingError;

import java.util.List;
import java.util.Optional;

public class AggregateLifecycle {

    protected static Scope scope = Scope.newInstance();

    private AggregateLifecycle(){

    }


    public static void apply( AbstractDomainEvent event ){
        List<AggregateProxy<?>> list = scope.aggregates.findAggregatesForEvent( event );

        for (AggregateProxy<?> proxy : list) {
            String reference = event.getReference();
            Object aggregate = aggregate(reference, proxy);
            proxy.executor().execute( aggregate , event );

            EventBusMessage message = EventBusMessage.builder()
                    .aggregate(proxy.getAggregate())
                    .event(event)
                    .build();

            scope.eventBus.apply( message );
            if( scope.publisher != null ) scope.publisher.publish(event);
        }

    }

    private static void validate( AbstractDomainEvent event ){
        AggregateObjectMapper.eventToJson(event).orElseThrow( ()-> new EventParsingError(event.getClass()) );
    }

    private static Object aggregate( String reference,  AggregateProxy<?> proxy ){
        Optional<?> aggregate = QueryGateway.aggregate(reference, proxy.getAggregate());
        if( aggregate.isPresent() == false ){
            Object newInstance;
            try {
                newInstance = proxy.newInstance();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
                newInstance = null;
            }
            return newInstance;
        }
        return aggregate.get();
    }


}
