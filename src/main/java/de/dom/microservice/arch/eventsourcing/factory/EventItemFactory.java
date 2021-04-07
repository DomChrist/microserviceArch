package de.dom.microservice.arch.eventsourcing.factory;


import de.dom.microservice.arch.ddd.AggregateObjectMapper;
import de.dom.microservice.arch.eventsourcing.events.AbstractDomainEvent;
import de.dom.microservice.arch.eventsourcing.events.SignatureGenerator;
import de.dom.microservice.arch.eventsourcing.eventstore.EventStoreItem;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class EventItemFactory {

    private EventItemFactory(){
        //factory
    }

    public static <T extends EventStoreItem> T parse(AbstractDomainEvent event, long sequence, Class agg , T target){
        String eventName = SignatureGenerator.signature( event.getClass() );
        String aggregate = SignatureGenerator.aggregateSignature( agg );

        String payload = AggregateObjectMapper.eventToJson(event).orElse("{}");

        target.setId(UUID.randomUUID().toString() );
        target.setReference( event.getReference() );
        target.setAggregate( eventName );
        target.setEventName( aggregate );
        target.setPayload( payload.getBytes(StandardCharsets.UTF_8) );
        target.setSequence( sequence );
        target.setCreated( event.getCreated() );
        target.setCreator( event.getCreator() );
        target.setVersion( event.getVersion() );

        return target;
    }

}
