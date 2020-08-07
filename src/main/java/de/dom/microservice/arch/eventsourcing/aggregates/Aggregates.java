package de.dom.microservice.arch.eventsourcing.aggregates;

import de.dom.microservice.arch.ddd.annotations.Aggregate;
import de.dom.microservice.arch.eventsourcing.annotations.AggregateCommandHandler;
import de.dom.microservice.arch.eventsourcing.annotations.AggregateEventHandler;
import de.dom.microservice.arch.eventsourcing.command.AbstractDomainCommand;
import de.dom.microservice.arch.eventsourcing.command.AggregateCommandMethod;
import de.dom.microservice.arch.eventsourcing.event.AbstractDomainEvent;
import de.dom.microservice.arch.eventsourcing.event.DomainEvent;
import de.dom.microservice.arch.eventsourcing.event.EventEntityInterface;
import de.dom.microservice.arch.eventsourcing.gateways.GatewayInitializer;

import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Collectors;

public class Aggregates {

    public static Set<Class<?>> aggregates(){
        Set<Class<?>> annotatedWith = GatewayInitializer.reflections().getTypesAnnotatedWith(Aggregate.class);
        return annotatedWith;
    }

    public static <T> Optional<T> newInstance(Class<T> clazz) {
        if (clazz.isAnnotationPresent(Aggregate.class)) {
            try {
                Constructor<T> constructor = clazz.getConstructor();
                return Optional.ofNullable(constructor.newInstance());
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return Optional.empty();
    }

    public static Set<EventMethod> eventMethods(Class<?> object) {
        return Arrays.stream(object.getDeclaredMethods())
                .filter(Aggregates::isEventMethod)
                .map( EventMethod::of )
                .collect(Collectors.toSet());
    }

    public static List<AggregateCommandMethod> commandMethods(Class<?> object) {
        List<AggregateCommandMethod> list = new ArrayList<>();
        Arrays.stream(object.getDeclaredConstructors())
                .filter(Aggregates::isCommandHandler)
                .map(c -> AggregateCommandMethod.constructor(object,(Constructor<? extends AbstractDomainCommand>) c))
                .forEach(list::add);
        Arrays.stream(object.getDeclaredMethods())
                .filter(Aggregates::isCommandHandler)
                .map( e->AggregateCommandMethod.methods(object,e))
                .forEach(list::add);
        return list;
    }

    public static <T> String eventToName(Class<T> event) {
        String eventName = event.getSimpleName().toLowerCase();
        int version = 1;
        if (event.isAnnotationPresent(DomainEvent.class)) {
            DomainEvent domainEvent = (DomainEvent) event.getDeclaredAnnotation(DomainEvent.class);
            version = domainEvent.version();
        }
        return String.format("%s_%s", eventName, version);
    }

    public static String eventStoreToName(EventEntityInterface e) {
        return e.getEvent().toLowerCase();
    }

    public static boolean isEventMethod(Method m) {
        if (Modifier.isPublic(m.getModifiers()) && !Modifier.isStatic(m.getModifiers()) && m.isAnnotationPresent(AggregateEventHandler.class)) {
            if (m.getParameterCount() != 1) return false;
            if (!AbstractDomainEvent.class.isAssignableFrom(m.getParameterTypes()[0])) return false;
            return true;
        }
        return false;
    }

    private static boolean isCommandMethod(Method m) {
        if (Modifier.isPublic(m.getModifiers()) && !Modifier.isStatic(m.getModifiers()) && m.isAnnotationPresent(AggregateCommandHandler.class)) {
            if (m.getParameterCount() != 1) return false;
            if (!AbstractDomainCommand.class.isAssignableFrom(m.getParameterTypes()[0])) return false;
            return true;
        }
        return false;
    }

    private static boolean isCommandHandler(Executable c) {
        if (Modifier.isPublic(c.getModifiers()) && !Modifier.isStatic(c.getModifiers()) && c.isAnnotationPresent(AggregateCommandHandler.class)) {
            if (c.getParameterCount() != 1) return false;
            Class<?> type = c.getParameterTypes()[0];
            if (!AbstractDomainCommand.class.isAssignableFrom(c.getParameterTypes()[0])) return false;
            return true;
        }
        return false;
    }

}
