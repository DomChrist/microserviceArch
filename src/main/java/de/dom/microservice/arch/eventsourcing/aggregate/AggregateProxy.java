package de.dom.microservice.arch.eventsourcing.aggregate;

import de.dom.microservice.arch.eventsourcing.events.SignatureGenerator;
import de.dom.microservice.arch.eventsourcing.exceptions.AggregateWithoutDefaultConstructorException;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.Set;

public class AggregateProxy<T> {

    private final Class<?> aggregate;

    private final MethodHandle constructor;

    protected Set<EventHandlerMethod> events;

    protected Set<CommandHandler> commands;


    public AggregateProxy( Class<T> clazz ) {
        this.aggregate = clazz;

        commands = CommandHandler.of(clazz);
        events = EventHandlerMethod.of(clazz);

        try {
            constructor = MethodHandles.lookup().findConstructor(aggregate, MethodType.methodType(void.class));
        } catch (Exception e) {
            throw new AggregateWithoutDefaultConstructorException();
        }
    }


    public T newInstance() throws Throwable {
        return (T) constructor.invoke();
    }

    public AggregateExecutor<T> executor(){
        return new AggregateExecutor<>(this);
    }

    public AggregateParser parser(){
        return new AggregateParser(this);
    }

    public AggregateProxySelector selector(){
        return new AggregateProxySelector(this);
    }


    public Class<?> getAggregate() {
        return aggregate;
    }

    public String getSignature(){
        return SignatureGenerator.aggregateSignature(aggregate);
    }

}
