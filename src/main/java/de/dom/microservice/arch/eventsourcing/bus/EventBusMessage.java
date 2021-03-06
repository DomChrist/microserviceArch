package de.dom.microservice.arch.eventsourcing.bus;

import de.dom.microservice.arch.ddd.annotations.Aggregate;
import de.dom.microservice.arch.eventsourcing.event.AbstractDomainEvent;

public class EventBusMessage {

    private Class<?> aggregate;
    private AbstractDomainEvent event;

    private EventBusMessage() {
    }

    public static Builder builder() {
        return new Builder();
    }


    public Class<?> getAggregate() {
        return aggregate;
    }

    public AbstractDomainEvent getEvent() {
        return event;
    }

    public String key(){
        return event.getReference();
    }

    public static class Builder{
        private Class<?> aggregate;
        private AbstractDomainEvent event;

        public Builder aggregate( Class<?> clazz ){
            if( !clazz.isAnnotationPresent(Aggregate.class)) throw new IllegalArgumentException("Aggregate annotation missing on class");
            this.aggregate = clazz;
            return this;
        }

        public Builder event( AbstractDomainEvent event ){
            this.event = event;
            return this;
        }

        public EventBusMessage build(){
            if( aggregate == null ) throw new NullPointerException("aggregate is null");
            if( event == null ) throw new NullPointerException("event is null");
            EventBusMessage eventBusMessage = new EventBusMessage();
                eventBusMessage.aggregate = aggregate;
                eventBusMessage.event = event;
            return eventBusMessage;
        }

    }

}
