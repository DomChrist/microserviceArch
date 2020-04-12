package de.dom.microservice.arch.eventsourcing.command;

public abstract class AbstractDomainCommand {

    public abstract void setReference(String reference);

    public abstract String getReference();

    public abstract void verify();

}
