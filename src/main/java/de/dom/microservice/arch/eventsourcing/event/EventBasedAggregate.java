package de.dom.microservice.arch.eventsourcing.event;

import de.dom.microservice.arch.ddd.annotations.Aggregate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target( ElementType.TYPE)
@Retention( value = RetentionPolicy.RUNTIME)
public @interface EventBasedAggregate {

    boolean ignoreMissingEventHandler() default false;
    boolean ignoreMissingCommandHandler() default false;

}
