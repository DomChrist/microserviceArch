package de.dom.microservice.arch.ddd;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.dom.microservice.arch.ddd.annotations.AggregateRoot;
import de.dom.microservice.arch.eventsourcing.event.AbstractDomainEvent;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Optional;

public class AggregateObjectMapper extends ObjectMapper {

    private AggregateObjectMapper() {
        this.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        this.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        this.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.registerModule(new JavaTimeModule());
    }

    public static String objectToString(Object obj) {
        try {
            return new AggregateObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{}";
        }
    }

    public static <T extends AggregateRoot> Optional<String> toJson(T agg) {
        return AggregateObjectMapper.toJsonString(agg);
    }

    public static <T extends AggregateRoot> Optional<T> fromJson(String json, Class<T> clazz) {
        T aggregate = null;
        try {
            aggregate = new AggregateObjectMapper().readValue(json, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(aggregate);
    }

    public static <T extends AbstractDomainEvent> Optional<String> eventToJson(T event) {
        return AggregateObjectMapper.toJsonString(event);
    }

    public static <T extends AbstractDomainEvent> Optional<T> toEvent(String json, Class<T> clazz) {
        try {
            return Optional.of(new AggregateObjectMapper().readValue(json, clazz));
        } catch (IOException e) {
            //log.error(e.getMessage(),e);
            return Optional.empty();
        }
    }

    public static Optional<AbstractDomainEvent> toAbstractEvent(String json, Class<AbstractDomainEvent> clazz) {
        try {
            return Optional.of(new AggregateObjectMapper().readValue(json, clazz));
        } catch (IOException e) {
            //log.error(e.getMessage(),e);
            return Optional.empty();
        }
    }

    private static <T> Optional<String> toJsonString(T t) {
        try {
            return Optional.of(new AggregateObjectMapper().writeValueAsString(t));
        } catch (Exception e) {
            //log.error(e.getMessage(),e);
            return Optional.empty();
        }
    }

}
