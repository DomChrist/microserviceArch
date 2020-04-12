package de.dom.microservice.arch.eventsourcing.bus;

public interface EventBus {

    EventBusResult read(String key , Class<?> aggregate );

    void apply( EventBusMessage message );

}
