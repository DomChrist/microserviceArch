package de.dom.microservice.arch.eventsourcing.eventbus.impl;


import de.dom.microservice.arch.eventsourcing.eventstore.EventStoreItem;

import java.time.LocalDateTime;

public class InMemoryEventItem implements EventStoreItem {

    private String id;

    private String reference;

    private String aggregate;

    private String eventName;

    private int version;

    private long sequence;

    private byte[] payload;

    private LocalDateTime created;

    private String creator;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getReference() {
        return reference;
    }

    @Override
    public void setReference(String reference) {
        this.reference = reference;
    }

    @Override
    public String getAggregate() {
        return aggregate;
    }

    @Override
    public void setAggregate(String aggregate) {
        this.aggregate = aggregate;
    }

    @Override
    public String getEventName() {
        return eventName;
    }

    @Override
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    @Override
    public int getVersion() {
        return version;
    }

    @Override
    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public long getSequence() {
        return sequence;
    }

    @Override
    public void setSequence(long sequence) {
        this.sequence = sequence;
    }

    @Override
    public byte[] getPayload() {
        return payload;
    }

    @Override
    public void setPayload(byte[] payload) {
        this.payload = payload;
    }

    @Override
    public LocalDateTime getCreated() {
        return created;
    }

    @Override
    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    @Override
    public String getCreator() {
        return creator;
    }

    @Override
    public void setCreator(String creator) {
        this.creator = creator;
    }
}
