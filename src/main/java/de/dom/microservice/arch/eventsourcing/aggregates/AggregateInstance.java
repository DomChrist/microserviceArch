package de.dom.microservice.arch.eventsourcing.aggregates;

import de.dom.microservice.arch.eventsourcing.EventMethodNotFoundException;
import de.dom.microservice.arch.eventsourcing.ExecuteResultError;
import de.dom.microservice.arch.eventsourcing.event.AbstractDomainEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AggregateInstance {

    private Object instance;
    private DomainEvents events;

    protected AggregateInstance(Object instance) {
        this.instance = instance;
        this.events = new DomainEvents(instance.getClass());
    }

    public Object getInstance() {
        return instance;
    }

    public EventMethod methodFor(AbstractDomainEvent event){
        EventMethod method = events.methodFor(event).orElseThrow(EventMethodNotFoundException::new);
        return method;
    }

    public ApplyFlow invokeOnAggregate(AbstractDomainEvent event){
        ApplyFlow applyFlow = new ApplyFlow(this);
        EventMethod method = methodFor(event);
        EventMethodExecutor executor = new EventMethodExecutor(this, event, method);
        ExecuteResult executeResult = applyFlow.applyToAggregate(executor);
            if( executeResult.isErrorResult() ) throw new ExecuteResultError(executeResult);
        return applyFlow;
    }

    public ApplyFlow invokeOnAggregate(ApplyFlow flow , AbstractDomainEvent event){
        EventMethod method = events.methodFor(event).orElseThrow(EventMethodNotFoundException::new);
        EventMethodExecutor executor = new EventMethodExecutor(flow.getAggregate(), event, method);
        ExecuteResult executeResult = flow.applyToAggregate(executor);
            if( executeResult.isErrorResult() ) throw new ExecuteResultError(executeResult);
        return flow;
    }

    public ApplyFlow invokeOnAggregate(List<? extends AbstractDomainEvent> events) {
        ApplyFlow applyFlow = new ApplyFlow(this);
        for( AbstractDomainEvent e : events ){
            this.invokeOnAggregate(applyFlow,e);
        }
        return applyFlow;
    }
}
