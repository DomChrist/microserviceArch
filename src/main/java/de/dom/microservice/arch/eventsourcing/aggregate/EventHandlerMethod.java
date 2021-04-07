package de.dom.microservice.arch.eventsourcing.aggregate;

import de.dom.microservice.arch.ddd.AggregateObjectMapper;
import de.dom.microservice.arch.eventsourcing.events.AbstractDomainEvent;
import de.dom.microservice.arch.eventsourcing.events.AggregateEventHandler;
import de.dom.microservice.arch.eventsourcing.events.DomainEventSpecification;
import de.dom.microservice.arch.eventsourcing.events.SignatureGenerator;
import de.dom.microservice.arch.eventsourcing.exceptions.EventParsingError;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class EventHandlerMethod {

    private Class<AbstractDomainEvent> eventType;

    private MethodHandle handle;

    private String eventName;


    public static boolean isEventHandlerMethod(Method method) {
        if( !method.isAnnotationPresent(AggregateEventHandler.class) ) return false;

        if(  method.getReturnType() != void.class  ) return false;

        if( method.getParameterCount() != 1 ) return false;

        if( !DomainEventSpecification.class.isAssignableFrom(method.getParameterTypes()[0]) ) return false;

        return true;
    }


    public static Set<EventHandlerMethod> of( Class<?> aggregate ){
        Set<EventHandlerMethod> collect = Arrays.stream(aggregate.getDeclaredMethods())
                .map(e -> {
                    EventHandlerMethod method = EventHandlerMethod.of(e);
                    return method;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        return collect;
    }

    public static EventHandlerMethod of( Method m ){
        if( isEventHandlerMethod(m) ){
            EventHandlerMethod method = new EventHandlerMethod();
                method.eventType = (Class<AbstractDomainEvent>) m.getParameterTypes()[0];
                method.handle = lookup( m ).orElseThrow( ()->new IllegalArgumentException("could not handle method " + m.getName()) );
                method.eventName = SignatureGenerator.signature((Class<AbstractDomainEvent>) m.getParameterTypes()[0]);

                validateEvent(method.eventType);

                return method;
        }
        return null;
    }

    private static Optional<MethodHandle> lookup(Method m) {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodType type = MethodType.methodType(void.class , m.getParameterTypes()[0]);
        try {
            MethodHandle handle = lookup.unreflect(m);
            return Optional.ofNullable( handle );
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Class<AbstractDomainEvent> getEventType() {
        return eventType;
    }

    public MethodHandle getHandle() {
        return handle;
    }

    public String getEventName() {
        return eventName;
    }

    public boolean isNameEquals( String name ){
        return this.eventName.equals(name);
    }

    public boolean isEvent(AbstractDomainEvent spec) {
        return eventType.equals(spec.getClass());
    }

    public void execute(Object aggregate, AbstractDomainEvent event) {
        try {
            handle.invoke(aggregate , event);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private static void validateEvent( Class<? extends AbstractDomainEvent> event ){
        try{
            AbstractDomainEvent instance = event.getConstructor().newInstance();
            AggregateObjectMapper.isEventParsingPossible( instance );
        }catch (Exception e){
            throw new EventParsingError((Class<AbstractDomainEvent>) event, e);
        }
    }

}
