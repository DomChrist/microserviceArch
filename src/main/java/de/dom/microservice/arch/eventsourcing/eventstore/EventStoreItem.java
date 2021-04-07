package de.dom.microservice.arch.eventsourcing.eventstore;

import java.time.LocalDateTime;

public interface EventStoreItem {

    String getId();
    void setId(String id);

    String getReference();
    void setReference(String reference);

    String getAggregate();
    void setAggregate( String aggregate );

    String getEventName();
    void setEventName(String name);

    int getVersion();
    void setVersion( int version );

    long getSequence();
    void setSequence( long sequence );

    byte[] getPayload();
    void setPayload( byte[] payload );

    LocalDateTime getCreated();
    void setCreated( LocalDateTime created );

    String getCreator();
    void setCreator( String creator );


}
