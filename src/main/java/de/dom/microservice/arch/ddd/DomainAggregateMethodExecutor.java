package de.dom.microservice.arch.ddd;

import de.dom.microservice.arch.ddd.exceptions.EventHandlerNotFoundException;
import de.dom.microservice.arch.eventsourcing.annotations.AggregateCommandHandler;
import de.dom.microservice.arch.eventsourcing.annotations.AggregateEventHandler;
import de.dom.microservice.arch.eventsourcing.command.AbstractDomainCommand;
import de.dom.microservice.arch.eventsourcing.event.AbstractDomainEvent;
import de.dom.microservice.arch.eventsourcing.event.EventBasedAggregate;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

public class DomainAggregateMethodExecutor {

    public static <T extends AbstractDomainEvent, A extends DomainAggregate> Optional<Method> findMethod(A aggregate, T event) {

        Method[] methods = aggregate.getClass().getDeclaredMethods();
        return Arrays.stream(methods)
                .filter(m -> m.isAnnotationPresent(AggregateEventHandler.class))
                .filter(m -> m.getParameterCount() == 1 && m.getParameterTypes()[0] == event.getClass())
                .findFirst();
    }

    private static <A extends DomainAggregate, T extends AbstractDomainCommand> Optional<Method> findCommand(A root, T cmd) {
        Method[] methods = root.getClass().getDeclaredMethods();
        return Arrays.stream(methods)
                .filter(m -> m.isAnnotationPresent(AggregateCommandHandler.class))
                .filter(m -> m.getParameterCount() == 1 && m.getParameterTypes()[0] == cmd.getClass())
                .findFirst();
    }

    public static <T extends AbstractDomainCommand, A extends DomainAggregate> void handle(A root, T cmd) throws InvocationTargetException, IllegalAccessException {
        Optional<Method> method = findCommand(root, cmd);
        Method m = method.orElseThrow(IllegalArgumentException::new);
        m.setAccessible(true);
        m.invoke(root, cmd);
    }


    public static <T extends AbstractDomainEvent, A extends DomainAggregate> void invoke(A root, T event) throws InvocationTargetException, IllegalAccessException {
        Optional<Method> optional = findMethod(root, event);
        if (optional.isPresent()) {
            Method method = optional.get();
            method.setAccessible(true);
            method.invoke(root, event);
        } else {
            boolean throwError = true;
            if (root.getClass().isAnnotationPresent(EventBasedAggregate.class)) {
                EventBasedAggregate a = root.getClass().getDeclaredAnnotation(EventBasedAggregate.class);
                throwError = !a.ignoreMissingEventHandler();
            }
            if (throwError) throw new EventHandlerNotFoundException(event);
        }
    }

}
