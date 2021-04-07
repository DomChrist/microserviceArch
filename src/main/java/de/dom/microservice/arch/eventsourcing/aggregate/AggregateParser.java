package de.dom.microservice.arch.eventsourcing.aggregate;

import de.dom.microservice.arch.ddd.AggregateObjectMapper;
import de.dom.microservice.arch.eventsourcing.eventbus.EventBusResult;
import de.dom.microservice.arch.eventsourcing.events.AbstractDomainEvent;
import de.dom.microservice.arch.eventsourcing.eventstore.EventStoreItem;
import de.dom.microservice.arch.eventsourcing.snapshot.AggregateSnapshotFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AggregateParser {

    private final AggregateProxy<?> proxy;

    public AggregateParser(AggregateProxy<?> proxy) {
        this.proxy = proxy;
    }

    public EventBusResult result(List<? extends EventStoreItem> items  ){
        if( items == null ) items = new ArrayList<>();

        List<AbstractDomainEvent> list = new ArrayList<>();
        for (EventStoreItem item : items) {

            if(AggregateSnapshotFactory.isSnapshot(item)){

            } else {
                Optional<EventHandlerMethod> handler = proxy.selector().findHandlerByEventName(item.getEventName());
                if( handler.isPresent() ){
                    Optional<? extends AbstractDomainEvent> domainEvent = parse(handler.get(), item);
                    if( domainEvent.isPresent() ){
                        AbstractDomainEvent abstractDomainEvent = domainEvent.get();
                        list.add( abstractDomainEvent );
                    }
                }
            }
        }
        return new EventBusResult(list);
    }

    private Optional<? extends AbstractDomainEvent> parse(EventHandlerMethod method, EventStoreItem item) {
        String json = new String(item.getPayload());
        Class<? extends AbstractDomainEvent> parameter = method.getEventType();
        return AggregateObjectMapper.toEvent( json , parameter );
    }

}
