package de.dom.microservice.arch.eventsourcing.aggregates;

import de.dom.microservice.arch.eventsourcing.event.AbstractDomainEvent;

public class ApplyFlow {

    private AggregateInstance aggregate;

    public ApplyFlow(AggregateInstance aggregate) {
        this.aggregate = aggregate;
    }

    public ExecuteResult applyToAggregate(EventMethodExecutor executor ){
        return executor.invoke();
    }

    public ExecuteResult applyToAggregate(AbstractDomainEvent event){
        EventMethod eventMethod = aggregate.methodFor(event);
        EventMethodExecutor eventMethodExecutor = new EventMethodExecutor(aggregate, event, eventMethod);
        return this.applyToAggregate( eventMethodExecutor );
    }

    public AggregateInstance getAggregate() {
        return aggregate;
    }
}
