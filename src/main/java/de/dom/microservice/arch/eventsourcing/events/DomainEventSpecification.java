package de.dom.microservice.arch.eventsourcing.events;

import java.time.LocalDateTime;

public interface DomainEventSpecification {

    String getReference();

    void setReference(String reference);

    int getVersion();

    long getSequence();

    LocalDateTime getCreated();

    String getCreator();




}
