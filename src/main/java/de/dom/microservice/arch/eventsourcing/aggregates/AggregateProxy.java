package de.dom.microservice.arch.eventsourcing.aggregates;

import de.dom.microservice.arch.eventsourcing.command.AggregateCommandMethod;
import de.dom.microservice.arch.eventsourcing.event.AbstractDomainEvent;
import de.dom.microservice.arch.eventsourcing.event.EventEntityInterface;
import de.dom.microservice.arch.eventsourcing.eventstore.EventStoreEntity;
import java.util.*;

public class AggregateProxy {

    private AggregateType aggregate;

    private DomainEvents events;
    private List<AggregateCommandMethod> commandMethods = new ArrayList<>();

    public AggregateProxy(Class<?> aggregate) {
        this.aggregate = new AggregateType(aggregate);
        this.commandMethods = Aggregates.commandMethods(aggregate);
        this.events = new DomainEvents(aggregate);
    }

    public boolean isAggregateEvent( AbstractDomainEvent event ){
        return aggregate.isAggregateEvent( event );
    }

    public AggregateType getAggregate() {
        return aggregate;
    }

    public List<AbstractDomainEvent> convertAggregateEvents(List<EventStoreEntity> list ){
        List<AbstractDomainEvent> result = new ArrayList<>();
        for( EventEntityInterface entity : list ){
            String name = Aggregates.eventStoreToName(entity);
            Optional<EventType<?>> eventType = events.typeForName(name);
            if( eventType.isPresent() ){
                Optional<?> reCreate = eventType.get().reCreate(entity.getPayload());
                Optional<AbstractDomainEvent> domainEvent = (Optional<AbstractDomainEvent>) reCreate;
                domainEvent.ifPresent(result::add);
            }
        }
        return result;
    }

    public ApplyFlow applyAll( List<? extends AbstractDomainEvent> events ){
        AggregateInstance aggregateInstance = this.aggregate.newInstance();
        ApplyFlow applyFlow = aggregateInstance.invokeOnAggregate(events);
        return applyFlow;
    }

}
