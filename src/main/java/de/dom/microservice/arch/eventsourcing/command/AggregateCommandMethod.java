package de.dom.microservice.arch.eventsourcing.command;

import de.dom.microservice.arch.eventsourcing.annotations.AggregateCommandHandler;

import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AggregateCommandMethod {

    private Class<?> aggregate;

    private Class<? extends AbstractDomainCommand> command;

    private Constructor<? extends AbstractDomainCommand> constructor;

    private Method method;

    public static AggregateCommandMethod methods(Class<?> aggregate , Method m) {
        if( aggregate == null ) throw new IllegalArgumentException("Aggregate is null");
        AggregateCommandMethod method = new AggregateCommandMethod();
        method.command = (Class<? extends AbstractDomainCommand>) m.getParameterTypes()[0];
        method.method = m;
        method.aggregate = aggregate;
        return method;
    }

    public static AggregateCommandMethod constructor(Class<?> aggregate, Constructor<? extends AbstractDomainCommand> m) {
        if( aggregate == null ) throw new IllegalArgumentException("Aggregate is null");
        AggregateCommandMethod method = new AggregateCommandMethod();
        method.command = (Class<? extends AbstractDomainCommand>) m.getParameterTypes()[0];
        method.constructor = m;
        method.aggregate = aggregate;
        return method;
    }

    public Class<? extends AbstractDomainCommand> getCommand() {
        return command;
    }

    public boolean isMethod() {
        return method != null;
    }

    public Object construct(AbstractDomainCommand cmd) {
        try {
            return this.constructor.newInstance(cmd);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object invoke(Object aggregate, AbstractDomainCommand cmd) throws Throwable {
        try {
            if (method != null) {
                method.invoke(aggregate, cmd);
                return aggregate;
            }
            if (constructor != null) return constructor.newInstance(cmd);
            method.invoke(aggregate, cmd);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            throw e.getCause();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Class aggregate() {
        return aggregate;
    }

    public boolean isCommandHandler(Class obj) {
        return obj.equals(command);
    }


}
