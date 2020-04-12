package de.dom.microservice.arch.eventsourcing.eventstore;

import java.util.List;

public interface EventStoreInterface {

    List<EventStoreEntity> aggregate(String reference, Class<?> aggregate);

    long sequence(String reference, Class<?> aggregate);

    void save(EventStoreEntity entity);

}
