package de.dom.microservice.arch.eventsourcing.implementations;

import de.dom.microservice.arch.eventsourcing.eventstore.EventStoreEntity;
import de.dom.microservice.arch.eventsourcing.eventstore.EventStoreInterface;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InMemoryEventStore implements EventStoreInterface {

    private List<EventStoreEntity> list = new ArrayList<>();

    @Override
    public List<EventStoreEntity> aggregate(String reference, Class<?> aggregate) {
        if (StringUtils.isEmpty(reference)) throw new IllegalArgumentException("reference is null");
        if (aggregate == null) throw new IllegalArgumentException("aggregate class is null");

        return list.stream().filter(e -> e.getReference().equals(reference)).collect(Collectors.toList());
    }

    @Override
    public long sequence(String reference, Class<?> aggregate) {
        return list.size() + 1;
    }

    @Override
    public void save(EventStoreEntity entity) {
        this.list.add(entity);
    }

}

