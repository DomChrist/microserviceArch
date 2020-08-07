package de.dom.microservice.arch.eventsourcing.event;

import de.dom.microservice.arch.eventsourcing.aggregates.AggregateProxy;
import de.dom.microservice.arch.eventsourcing.aggregates.ApplyFlow;
import de.dom.microservice.arch.eventsourcing.bus.EventBus;
import de.dom.microservice.arch.eventsourcing.bus.EventBusMessage;
import de.dom.microservice.arch.eventsourcing.bus.EventBusResult;
import de.dom.microservice.arch.eventsourcing.bus.EventPublisher;
import de.dom.microservice.arch.eventsourcing.config.EventSourcingScope;

import java.util.Optional;

public abstract class AggregateLifecycle extends EventSourcingScope {

    public static <T extends AbstractDomainEvent> void apply( T event ) {
        try {
            getInstance().applyEvent(event);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private static AggregateLifecycle getInstance(){
        return aggregateLifecycle;
    }

    protected <T extends AbstractDomainEvent> void applyEvent( T event ) throws Throwable {
        Optional<AggregateProxy> proxy = EventSourcingScope.of(event);
            if( proxy.isPresent() ){
                AggregateProxy aggregateProxy = proxy.get();
                EventBusResult read = eventBus.read(event.getReference(), aggregateProxy.getAggregate().getAggregateType());
                ApplyFlow applyFlow = aggregateProxy.applyAll(read.getEvents());
                    applyFlow.applyToAggregate( event );
                    EventBusMessage build = EventBusMessage.builder().aggregate(aggregateProxy.getAggregate().getAggregateType()).event(event).build();
                eventBus.apply( build );
                if( publisher != null) publisher.publish(event);
            }
    }


    protected static void setEventBus(EventBus bus){
        EventSourcingScope.eventBus = bus;
    }

    protected static void setLifecycle( AggregateLifecycle lifecycle ){
        EventSourcingScope.aggregateLifecycle = lifecycle;
    }

    protected static void setEventPublisher(EventPublisher publisher){
        EventSourcingScope.publisher = publisher;
    }

}
