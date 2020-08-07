package de.dom.microservice.arch.ddd;

import de.dom.microservice.arch.eventsourcing.command.AbstractDomainCommand;
import de.dom.microservice.arch.eventsourcing.event.AbstractDomainEvent;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class AggregateInspector {

    private AggregateInspector() {

    }

    public static <A> Set<Method> commandMethods(Class<A> aggregate) {
        return Arrays.stream(aggregate.getDeclaredMethods())
                .filter(c -> Modifier.isPublic(c.getModifiers()))
                .filter(c -> c.getParameterCount() == 1)
                .filter(c -> AbstractDomainCommand.class.isAssignableFrom(c.getParameterTypes()[0]))
                .collect(Collectors.toSet());
    }

    public static <A> Set<Constructor> commandConstructors(Class<A> aggregate) {
        return Arrays.stream(aggregate.getDeclaredConstructors())
                .filter(c -> Modifier.isPublic(c.getModifiers()))
                .filter(c -> c.getParameterCount() == 1)
                .filter(c -> AbstractDomainCommand.class.isAssignableFrom(c.getParameterTypes()[0]))
                .collect(Collectors.toSet());
    }

    public static <A> Set<Method> eventMethods(Class<A> aggregate) {
        return Arrays.stream(aggregate.getDeclaredMethods())
                .filter(c -> Modifier.isPublic(c.getModifiers()))
                .filter(c -> c.getParameterCount() == 1)
                .filter(c -> AbstractDomainEvent.class.isAssignableFrom(c.getParameterTypes()[0]))
                .collect(Collectors.toSet());
    }

    public static <A, E extends AbstractDomainEvent> void invoke(A aggregate, E event) {

        Optional<Method> eventMethod = eventMethods(aggregate.getClass()).stream().filter(e -> isMethodForEvent(e, event)).findFirst();
        eventMethod.ifPresent(m -> {
            try {
                m.invoke(aggregate, event);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        });
    }

    private static <E extends AbstractDomainEvent> boolean isMethodForEvent(Method m, E event) {
        Class<?> parameterType = m.getParameterTypes()[0];
        boolean instance = parameterType.isInstance(event);
        return instance;
    }

}
