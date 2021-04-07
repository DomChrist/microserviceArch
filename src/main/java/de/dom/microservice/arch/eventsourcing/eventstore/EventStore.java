package de.dom.microservice.arch.eventsourcing.eventstore;

import java.util.List;

public interface EventStore<T extends EventStoreItem> {

    List<T> items(String reference , Class<?> aggregate );

    void save( T event ,  Class<?> aggregate );

    long nextSequence( String reference , Class<?> aggregate );

}
