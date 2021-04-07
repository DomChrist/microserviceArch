package de.dom.microservice.arch.eventsourcing.eventbus.impl;

import de.dom.microservice.arch.eventsourcing.eventstore.EventStore;

import java.util.List;

public class InMemoryEventStore implements EventStore<InMemoryEventItem> {

    private InMemoryDataSource source = new InMemoryDataSource();

    @Override
    public List<InMemoryEventItem> items(String reference, Class<?> aggregate) {
        List<InMemoryEventItem> items = source.items(reference, aggregate);
        return items;
    }

    @Override
    public void save(InMemoryEventItem event, Class<?> aggregate) {
        source.save( event , aggregate );
    }

    @Override
    public long nextSequence(String reference, Class<?> aggregate) {
        List<InMemoryEventItem> items = source.items(reference, aggregate);
        if( items == null || items.isEmpty() ) return 1;
        return items.size() + 1;
    }


}
