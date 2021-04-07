package de.dom.microservice.arch.eventsourcing.config;


import de.dom.microservice.arch.eventsourcing.aggregate.Aggregates;
import de.dom.microservice.arch.eventsourcing.eventbus.EventBus;
import de.dom.microservice.arch.eventsourcing.eventbus.EventPublisher;
import de.dom.microservice.arch.eventsourcing.eventstore.EventStore;
import de.dom.microservice.arch.eventsourcing.inspector.AppInspector;

public class EventSourcingConfigurator {

    private final EventBus eventBus;

    private final EventStore<?> eventStore;

    private final EventPublisher publisher;

    public EventSourcingConfigurator(EventBus eventBus, EventStore<?> eventStore, EventPublisher publisher) {
        this.eventBus = eventBus;
        this.eventStore = eventStore;
        this.publisher = publisher;
    }

    public void init(AppInspector appInspector){
        Scope scope = Scope.newInstance();
            scope.eventBus = eventBus;
            scope.publisher = publisher;
            scope.aggregates = Aggregates.init(appInspector);
    }


}
