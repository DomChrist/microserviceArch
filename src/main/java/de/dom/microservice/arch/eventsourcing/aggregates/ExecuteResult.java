package de.dom.microservice.arch.eventsourcing.aggregates;

import java.lang.reflect.InvocationTargetException;

public class ExecuteResult {

    private Throwable throwable;
    private String errorMessage;

    private boolean successful = false;

    public ExecuteResult(Throwable throwable, String errorMessage) {
        this.throwable = throwable;
        this.errorMessage = errorMessage;
        this.successful = false;
    }

    public ExecuteResult() {
        this.successful = true;
    }

    public static ExecuteResult of(IllegalAccessException e) {
        return new ExecuteResult(e,e.getMessage());
    }

    public static ExecuteResult of(InvocationTargetException e) {
        Throwable targetException = e.getTargetException();
        return new ExecuteResult(targetException,targetException.getMessage());
    }

    public static ExecuteResult ok() {
        return new ExecuteResult();
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean isErrorResult(){
        return this.throwable != null;
    }

}
