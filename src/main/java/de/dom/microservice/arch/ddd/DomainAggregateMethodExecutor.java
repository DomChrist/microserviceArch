package de.dom.microservice.arch.ddd;

import de.dom.microservice.arch.eventsourcing.annotations.AggregateEventHandler;
import de.dom.microservice.arch.eventsourcing.event.AbstractDomainEvent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

public class DomainAggregateMethodExecutor {

    public static  <T extends AbstractDomainEvent , A extends DomainAggregate> Optional<Method> findMethod(A aggregate , T event){

        Method[] methods = aggregate.getClass().getDeclaredMethods();
        return Arrays.stream(methods)
                .filter( m-> m.isAnnotationPresent(AggregateEventHandler.class) )
                .filter( m-> m.getParameterCount() == 1 && m.getParameterTypes()[0] == event.getClass() )
                .findFirst();
    }

    public static <T extends AbstractDomainEvent , A extends DomainAggregate> void invoke( A root , T event ) throws InvocationTargetException, IllegalAccessException {
        Optional<Method> optional = findMethod(root, event);
            Method method = optional.orElseThrow(IllegalArgumentException::new);
                method.setAccessible(true);
                method.invoke( root , event );
    }

}
