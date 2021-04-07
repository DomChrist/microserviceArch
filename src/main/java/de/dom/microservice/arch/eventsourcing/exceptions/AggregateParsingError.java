package de.dom.microservice.arch.eventsourcing.exceptions;

public class AggregateParsingError extends RuntimeException{

    private Exception stack;


    public AggregateParsingError(){
        super();
    }

    public AggregateParsingError(Class<?> aClass) {
        super("cannot create "+aClass.getSimpleName());
    }
}
