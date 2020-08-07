package de.dom.microservice.arch.eventsourcing.examples.logic;

import de.dom.microservice.arch.eventsourcing.command.AbstractDomainCommand;

public class CloseTest extends AbstractDomainCommand {

    private String reference;

    @Override
    public String getReference() {
        return reference;
    }

    @Override
    public void setReference(String reference) {
        this.reference = reference;
    }

    @Override
    public void verify() {

    }
}
