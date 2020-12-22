package de.dom.microservice.arch.eventsourcing.config.builder;

import de.dom.microservice.arch.eventsourcing.bus.EventPublisher;

public class EmptyPublisher implements EventPublisher {

    @Override
    public <T> void publish(T message) {

    }

}
