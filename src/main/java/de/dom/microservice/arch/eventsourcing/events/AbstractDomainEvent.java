package de.dom.microservice.arch.eventsourcing.events;

import java.time.LocalDateTime;

public abstract class AbstractDomainEvent implements DomainEventSpecification {

    private String reference;

    private long sequence;

    private LocalDateTime localDateTime;

    private String creator;

    @Override
    public String getReference() {
        return reference;
    }

    @Override
    public void setReference(String reference) {
        this.reference = reference;
    }

    @Override
    public long getSequence() {
        return sequence;
    }

    @Override
    public LocalDateTime getCreated() {
        return localDateTime;
    }

    @Override
    public String getCreator() {
        return creator;
    }

    public final String getEventName(){
        String name = getClass().getSimpleName().toLowerCase();
        int version = getVersion();
        return SignatureGenerator.signature( name , version );
    }

}
