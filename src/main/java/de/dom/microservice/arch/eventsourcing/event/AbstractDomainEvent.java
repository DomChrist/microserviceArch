package de.dom.microservice.arch.eventsourcing.event;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public abstract class AbstractDomainEvent {

    private String reference;

    private String user;
    private LocalDateTime created = LocalDateTime.now();

    public abstract String getEventGroup();


}
