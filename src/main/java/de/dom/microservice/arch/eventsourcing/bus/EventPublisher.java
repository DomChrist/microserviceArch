package de.dom.microservice.arch.eventsourcing.bus;

public interface EventPublisher {

    <T> void publish( T message );

}
