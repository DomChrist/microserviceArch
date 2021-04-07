package de.dom.microservice.arch.eventsourcing.snapshot;

import de.dom.microservice.arch.ddd.AggregateObjectMapper;
import de.dom.microservice.arch.eventsourcing.events.SignatureGenerator;
import de.dom.microservice.arch.eventsourcing.eventstore.EventStoreItem;
import de.dom.microservice.arch.eventsourcing.exceptions.AggregateParsingError;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public class AggregateSnapshotFactory {

    public static final String SNAPSHOT = "SNAPSHOT";

    public <T extends EventStoreItem> T createSnapshot(Object aggregate , String reference , T item , long sequence ){
        String payload = AggregateObjectMapper.toJson(aggregate).orElseThrow(() -> new AggregateParsingError(aggregate.getClass()));

        item.setAggregate(SignatureGenerator.aggregateSignature(aggregate.getClass()));
        item.setEventName( SNAPSHOT );
        item.setVersion(1);
        item.setSequence( sequence );
        item.setPayload( payload.getBytes(StandardCharsets.UTF_8) );
        item.setReference( reference );
        item.setId(UUID.randomUUID().toString());
        item.setCreator( SNAPSHOT );
        item.setCreated(LocalDateTime.now());
        return item;
    }

    public static Object fromSnapshot( EventStoreItem event , Class aggregate ){
        Optional json = AggregateObjectMapper.fromJson(new String(event.getPayload()), aggregate);
        if( !json.isPresent() ) throw new AggregateParsingError( aggregate );
        return json.get();
    }

    public static boolean isSnapshot( EventStoreItem event ){
        if( event.getEventName().equalsIgnoreCase(SNAPSHOT) && event.getCreator().equalsIgnoreCase(SNAPSHOT) ) return true;
        return false;
    }

    public static boolean isSnapshotSequence( Class aggregate , long sequence ){
        long snapshotSequence = 0; //TODO aggregate annotation
        boolean b = sequence % snapshotSequence == 0;
        return b;
    }

}
