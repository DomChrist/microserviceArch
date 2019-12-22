package de.dom.microservice.arch.eventsourcing;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.dom.microservice.arch.ddd.annotations.Aggregate;
import de.dom.microservice.arch.eventsourcing.event.AbstractDomainEvent;
import de.dom.microservice.arch.eventsourcing.event.DomainEvent;
import lombok.Getter;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class EventSourcingController {

    private final Reflections reflections;
    @Getter
    private Map<String,Class> events = new HashMap<>();
    @Getter
    private Map<String,Class> aggregates = new HashMap<>();

    public EventSourcingController( String reflectionPath ) {
        reflections = new Reflections(reflectionPath);
        this.listAllEvents();
        this.listAllAggregates();
    }

    private void listAllEvents(){
        reflections.getTypesAnnotatedWith( DomainEvent.class ).forEach( e->{
            DomainEvent domainEvent = e.getDeclaredAnnotation(DomainEvent.class);
            String name = e.getSimpleName().toLowerCase().trim();
            String group = domainEvent.eventGroup().toLowerCase().trim();

            String title = String.format("%s.%s" , group,name);
            this.events.put( title , e );
        });
    }

    private void listAllAggregates(){
        reflections.getTypesAnnotatedWith( Aggregate.class ).forEach(e->{
            Aggregate aggregate = e.getDeclaredAnnotation(Aggregate.class);
            aggregates.put( aggregate.eventGroup().toLowerCase().trim() , e );
        });
    }

    public <T> T toAggregate(Class<T> clazz ){
        Aggregate aggregate = clazz.getDeclaredAnnotation(Aggregate.class);
        Class<T> aClass = this.aggregates.get(aggregate.eventGroup());
        try {
            return aClass.getConstructor().newInstance();
        } catch (Exception e) {
            throw new AggregateCreateException();
        }
    }




}
