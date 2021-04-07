package de.dom.microservice.arch.eventsourcing.aggregate;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class CommandHandler extends HandlerMethod {


    protected Class<?> parameter;

    public static Set<CommandHandler> of(Class<?> aggregate ) {
        Set<CommandHandler> handler = new HashSet<>();

        Set<ConstructorCommandHandler> constructors = Arrays.stream(aggregate.getDeclaredConstructors())
                .map(e -> {
                    ConstructorCommandHandler commandHandler = ConstructorCommandHandler.of(e);
                    return commandHandler;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        handler.addAll( constructors );

        Set<MethodCommandHandler> methods = Arrays.stream(aggregate.getDeclaredMethods())
                .map(m -> {
                    MethodCommandHandler commandHandler = MethodCommandHandler.of(m);
                    return commandHandler;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        handler.addAll( methods );

        return handler;

    }

    @Override
    public Class<?> getParameter() {
        return parameter;
    }

    public boolean isMethodForCommand(Object cmd) {
        return this.parameter.equals( cmd.getClass() );
    }

    public abstract boolean isConstructor();


}
