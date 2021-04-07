package de.dom.microservice.arch.eventsourcing.aggregate;

import de.dom.microservice.arch.eventsourcing.commands.AggregateCommandHandler;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.util.Optional;

public class ConstructorCommandHandler extends CommandHandler {


    public static ConstructorCommandHandler of(Constructor<?> c ) {
        ConstructorCommandHandler h = new ConstructorCommandHandler();
        if( isCommandConstructor(c) ){
            h.parameter = c.getParameterTypes()[0];
            h.handle = handle( c ).orElseThrow( ()->new IllegalArgumentException("constructor not callable") );
            return h;
        }
        return null;
    }

    private static Optional<MethodHandle> handle(Constructor<?> c) {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        try {
            MethodHandle methodHandle = lookup.unreflectConstructor(c);
            return Optional.of(methodHandle);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }


    private static boolean isCommandConstructor(Constructor<?> c) {
        boolean annotationPresent = c.isAnnotationPresent(AggregateCommandHandler.class);
        if( annotationPresent && constructorCheck(c) ) return true;
        return false;
    }

    private static boolean constructorCheck(Constructor<?> c) {
        if( c.getParameterCount() != 1 ) return false;
        return true;
    }


    @Override
    public Class<?> getParameter() {
        return parameter;
    }

    @Override
    public boolean isConstructor() {
        return true;
    }

    public Object execute( Object cmd ){

            try {
                Object invoke = handle.invoke(cmd);
                return Optional.ofNullable( invoke );
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        return Optional.empty();
    }

}
