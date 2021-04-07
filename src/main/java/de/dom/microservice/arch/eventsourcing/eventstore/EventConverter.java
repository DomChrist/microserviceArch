package de.dom.microservice.arch.eventsourcing.eventstore;


import de.dom.microservice.arch.eventsourcing.events.DomainEventSpecification;

public interface EventConverter {

    <T extends EventStoreItem> T of(DomainEventSpecification event , T clazz );

    <T extends DomainEventSpecification> T of( EventStoreItem item , T clazz );

}
