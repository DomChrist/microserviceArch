package de.dom.microservice.arch.eventsourcing.eventstore;

import de.dom.microservice.arch.ddd.annotations.Aggregate;
import de.dom.microservice.arch.eventsourcing.event.AbstractDomainEvent;
import de.dom.microservice.arch.eventsourcing.event.EventEntityInterface;
import org.w3c.dom.events.Event;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.List;

public class EventStoreEntity implements EventEntityInterface {


    public EventStoreEntity() {
    }

    private int id;

    private String event;

    private String aggregate;

    private String eventGroup;

    private int version = 1;

    private long sequence;

    private String reference;

    private String payload;

    private LocalDateTime created = LocalDateTime.now();

    private String creator;

    private static <T extends EventStoreEntity> T newInstance(Class<T> from ){
        try {
            T t = from.getConstructor(Void.class).newInstance();
            return t;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static EventStoreEntity from(Class<?> aggregate , AbstractDomainEvent event, long sequence){
        aggregate.isAnnotationPresent(Aggregate.class);
        EventStoreEntity entity =new EventStoreEntity();
        entity.setReference(event.getReference());
        entity.setEvent(event.getEvent());
        entity.setAggregate( aggregate.getName() );
        entity.setEventGroup(event.getEventGroup());
        entity.setSequence(sequence);
        entity.setPayload(event.payload());
        entity.setVersion(1);
        entity.setCreator( event.getUser() );
        entity.setCreated(event.getCreated());
        return entity;
    }

    public  static <T extends EventStoreEntity> EventStoreEntity from(Class<?> aggregate , AbstractDomainEvent event, String payload, long sequence , Class<T> to ){
        aggregate.isAnnotationPresent(Aggregate.class);
        T entity = newInstance(to);
        entity.setReference(event.getReference());
        entity.setEvent(event.getEvent());
        entity.setAggregate( aggregate.getName() );
        entity.setEventGroup(event.getEventGroup());
        entity.setSequence(sequence);
        entity.setPayload(payload);
        entity.setVersion(1);
        entity.setCreator( event.getUser() );
        entity.setCreated(event.getCreated());
        return entity;
    }

    public void validate(){
        /*
        if(StringUtils.isEmpty(creator)) throw new RuntimeException("creator cannot be null");
        if(StringUtils.isEmpty(reference)) throw new RuntimeException("reference cannot be null");
        if(StringUtils.isEmpty(event)) throw new RuntimeException("event cannot be null");
        if(StringUtils.isEmpty(eventGroup)) throw new RuntimeException("reference cannot be null");
         */
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getAggregate() {
        return aggregate;
    }

    public void setAggregate(String aggregate) {
        this.aggregate = aggregate;
    }

    public String getEventGroup() {
        return eventGroup;
    }

    public void setEventGroup(String eventGroup) {
        this.eventGroup = eventGroup;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public long getSequence() {
        return sequence;
    }

    public void setSequence(long sequence) {
        this.sequence = sequence;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

}
