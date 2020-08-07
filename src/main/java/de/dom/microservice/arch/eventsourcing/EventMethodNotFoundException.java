package de.dom.microservice.arch.eventsourcing;

public class EventMethodNotFoundException extends RuntimeException {

    public EventMethodNotFoundException() {
    }

    public EventMethodNotFoundException(String message) {
        super(message);
    }

    public EventMethodNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public EventMethodNotFoundException(Throwable cause) {
        super(cause);
    }

    public EventMethodNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
