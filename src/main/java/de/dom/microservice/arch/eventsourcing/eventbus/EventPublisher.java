package de.dom.microservice.arch.eventsourcing.eventbus;

public interface EventPublisher {

    void publish( Object message );

}
