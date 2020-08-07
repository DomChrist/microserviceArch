package de.dom.microservice.arch.eventsourcing.aggregates;

import de.dom.microservice.arch.eventsourcing.annotations.AggregateEventHandler;
import de.dom.microservice.arch.eventsourcing.event.AbstractDomainEvent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class EventMethod {

    private Method method;
    private String eventName;
    private Class<? extends AbstractDomainEvent> eventType;


    public <T extends AbstractDomainEvent> boolean canHandle( T event ){
        if( eventType.isAssignableFrom(event.getClass()) ){
            return true;
        }
        return false;
    }

    public String getEventName() {
        return eventName;
    }

    protected Class<? extends AbstractDomainEvent> getEventType() {
        return eventType;
    }

    public ExecuteResult exec( AggregateInstance aggregateInstance , AbstractDomainEvent event ){
        try {
            method.invoke(aggregateInstance.getInstance() , event);
        } catch (IllegalAccessException e) {
            return ExecuteResult.of(e);
        } catch (InvocationTargetException e) {
            return ExecuteResult.of(e);
        }
        return ExecuteResult.ok();
    }

    public static EventMethod of(Method method) {
        if( !isEventMethod(method) ) throw new IllegalArgumentException("Method is not a event method");

        EventMethod m = new EventMethod();
            m.method = method;
            m.eventType = (Class<? extends AbstractDomainEvent>) method.getParameterTypes()[0];
            m.eventName = Aggregates.eventToName(m.eventType);
        return m;
    }

    public static boolean isEventMethod(Method m) {
        if (Modifier.isPublic(m.getModifiers()) && !Modifier.isStatic(m.getModifiers()) && m.isAnnotationPresent(AggregateEventHandler.class)) {
            if (m.getParameterCount() != 1) return false;
            if (!AbstractDomainEvent.class.isAssignableFrom(m.getParameterTypes()[0])) return false;
            return true;
        }
        return false;
    }


}
