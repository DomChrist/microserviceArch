package de.dom.microservice.arch.eventsourcing.event;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public abstract class AbstractDomainEvent {

    @JsonIgnore
    private String uniqueEventIdentifier;

    private String reference;

    private String user;
    private LocalDateTime created = LocalDateTime.now();

    public abstract String getEventGroup();

    public AbstractDomainEvent() {
        String time =LocalDateTime.now().toString();
        String id = UUID.randomUUID().toString();
        this. uniqueEventIdentifier = String.format("%s-%s" , id , time);
    }
}
