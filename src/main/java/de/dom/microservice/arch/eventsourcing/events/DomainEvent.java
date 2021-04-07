package de.dom.microservice.arch.eventsourcing.events;

public @interface DomainEvent {

    String name() default "";

    int version() default 1;

}
