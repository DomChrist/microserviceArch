package de.dom.microservice.arch.eventsourcing.examples.logic;

import de.dom.microservice.arch.eventsourcing.command.AbstractDomainCommand;

import java.util.UUID;

public class CreateTest extends AbstractDomainCommand {

    private String reference = UUID.randomUUID().toString();

    @Override
    public void setReference(String reference) {
        reference = reference;
    }

    @Override
    public String getReference() {
        return reference;
    }

    @Override
    public void verify() {

    }
}
