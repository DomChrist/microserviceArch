package de.dom.microservice.arch.eventsourcing.event;

import java.time.LocalDateTime;

public interface EventEntityInterface {

    int getId();

    String getEvent();

    String getAggregate();

    String getEventGroup();

    int getVersion();

    long getSequence();

    String getReference();

    String getPayload();

    LocalDateTime getCreated();

    String getCreator();

    public enum EventType {
        EVENT, COMMAND, SNAPSHOT
    }

}
