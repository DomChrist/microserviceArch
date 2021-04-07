package de.dom.microservice.arch.eventsourcing.aggregate;


import de.dom.microservice.arch.eventsourcing.commands.AggregateCommandHandler;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;

public class MethodCommandHandler extends CommandHandler {

    public MethodCommandHandler() {

    }

    public static boolean isCommandHandlerMethod(Method method) {
        boolean annotation = method.isAnnotationPresent(AggregateCommandHandler.class);
        if( annotation  && methodCheck(method) ) return true;

        return false;
    }

    public static MethodCommandHandler of( Method m ) {
        if( isCommandHandlerMethod(m) ){
            MethodCommandHandler hm = new MethodCommandHandler();
            MethodHandles.Lookup lookup = MethodHandles.lookup();
            try {
                hm.parameter = m.getParameterTypes()[0];
                hm.handle = lookup.unreflect(m);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return null;
            }
            return hm;
        }
        return null;
    }

    @Override
    public boolean isConstructor() {
        return false;
    }

    public void execute(Object agg, AggregateProxy<?> proxy, Object c) {
        try {
            handle.invoke( agg , c );
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
