package de.dom.microservice.arch.eventsourcing.event;


import com.fasterxml.jackson.annotation.JsonIgnore;
import de.dom.microservice.arch.ddd.AggregateObjectMapper;
import de.dom.microservice.arch.eventsourcing.aggregates.Aggregates;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Data
public abstract class AbstractDomainEvent {

    @JsonIgnore
    private String uniqueEventIdentifier;

    protected String reference;

    private String user;
    private LocalDateTime created = LocalDateTime.now();

    public abstract String getEventGroup();

    public String getEvent() {
        return Aggregates.eventToName(getClass());
    }

    public AbstractDomainEvent() {
        String time = LocalDateTime.now().toString();
        String id = UUID.randomUUID().toString();
        this.uniqueEventIdentifier = String.format("%s-%s", id, time);
    }

    public String payload() {
        Optional<String> json = AggregateObjectMapper.eventToJson(this);
        return json.orElse("{}");
    }

    public String getUniqueEventIdentifier() {
        return uniqueEventIdentifier;
    }

    public String getReference() {
        return reference;
    }
}
