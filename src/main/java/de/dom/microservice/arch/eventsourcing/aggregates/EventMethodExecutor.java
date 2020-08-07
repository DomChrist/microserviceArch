package de.dom.microservice.arch.eventsourcing.aggregates;

import de.dom.microservice.arch.eventsourcing.event.AbstractDomainEvent;

public class EventMethodExecutor {

    private AggregateInstance instance;
    private AbstractDomainEvent event;
    private EventMethod eventMethod;

    public EventMethodExecutor(AggregateInstance instance, AbstractDomainEvent event, EventMethod eventMethod) {
        this.instance = instance;
        this.event = event;
        this.eventMethod = eventMethod;
    }

    public ExecuteResult invoke(){
        return eventMethod.exec(instance,event);
    }

    public ExecuteResult invoke( AggregateInstance aggregateInstance ){
        return eventMethod.exec(aggregateInstance,event);
    }

}
