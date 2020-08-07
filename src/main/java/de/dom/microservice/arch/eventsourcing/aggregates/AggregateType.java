package de.dom.microservice.arch.eventsourcing.aggregates;

import de.dom.microservice.arch.ddd.annotations.Aggregate;
import de.dom.microservice.arch.eventsourcing.event.AbstractDomainEvent;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Arrays;

public class AggregateType {

    private final Class<?> aggregateType;
    private final DomainEvents domainEvents;
    private Constructor<?> createInstanceConstructor;

    public AggregateType(Class<?> aggregateType) {
        if( !aggregateType.isAnnotationPresent(Aggregate.class) ){
            throw new IllegalArgumentException("class is not a aggregate");
        }
        this.validate(aggregateType);
        this.aggregateType = aggregateType;
        this.domainEvents = new DomainEvents(aggregateType);
    }

    public boolean isAggregateEvent(AbstractDomainEvent e){
        return this.domainEvents.methodFor(e).isPresent();
    }

    public Class<?> getAggregateType() {
        return aggregateType;
    }

    private void validate( Class<?> clazz ){
        if( !clazz.isAnnotationPresent(Aggregate.class)) throw new IllegalArgumentException("class is not a aggregate");
            createInstanceConstructor = Arrays.stream(clazz.getConstructors())
                .filter(c -> c.getParameterCount() == 0 && Modifier.isPublic(c.getModifiers()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No empty public constructor found"));
    }

    public AggregateInstance newInstance(){
        try {
            return new AggregateInstance(createInstanceConstructor.newInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
