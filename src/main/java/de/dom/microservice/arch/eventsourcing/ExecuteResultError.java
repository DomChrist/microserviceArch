package de.dom.microservice.arch.eventsourcing;

import de.dom.microservice.arch.eventsourcing.aggregates.ExecuteResult;

public class ExecuteResultError extends RuntimeException {

    private ExecuteResult result;

    public ExecuteResultError(ExecuteResult invoke) {
        this.result = invoke;
    }

    public ExecuteResult getResult() {
        return result;
    }
}
