package de.dom.microservice.arch.eventsourcing.aggregate;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public abstract class HandlerMethod {

    protected MethodHandle handle;


    public MethodHandle getMethodHandle() {
        return handle;
    }

    public abstract Class<?> getParameter();



    protected static boolean parameterCountCheck( Method m ){
        return m.getParameterCount() == 1;
    }

    protected static boolean voidReturnCheck( Method m ){
        return m.getReturnType() == void.class || m.getReturnType() == Void.class;
    }

    protected static boolean isPublic( Method m ){
        return Modifier.isPublic( m.getModifiers() );
    }

    protected static boolean methodCheck( Method m ){
        if( parameterCountCheck(m) && voidReturnCheck(m) && isPublic(m) ) return true;
        return false;
    }
}
