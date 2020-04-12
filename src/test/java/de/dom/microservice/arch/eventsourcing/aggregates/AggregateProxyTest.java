package de.dom.microservice.arch.eventsourcing.aggregates;

import de.dom.microservice.arch.eventsourcing.bus.EventBus;
import de.dom.microservice.arch.eventsourcing.command.AggregateCommandMethod;
import de.dom.microservice.arch.eventsourcing.command.CommandGateway;
import de.dom.microservice.arch.eventsourcing.event.AbstractDomainEvent;
import de.dom.microservice.arch.eventsourcing.event.AggregateLifecycle;
import de.dom.microservice.arch.eventsourcing.eventstore.EventStoreInterface;
import de.dom.microservice.arch.eventsourcing.examples.*;
import de.dom.microservice.arch.eventsourcing.examples.logic.TestAggregate;
import de.dom.microservice.arch.eventsourcing.examples.logic.TestCreated;
import de.dom.microservice.arch.eventsourcing.gateways.QueryGateway;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class AggregateProxyTest {

    private CommandGateway gateway;

    private QueryGateway queryGateway;

    private EventBus eventBus;

    private TestRepository repository;

    @Before
    public void setUp(){
        repository = new TestRepository();
        eventBus = new TestEventBus(repository);
        DefaultEventPublisher eventPublisher = new DefaultEventPublisher();
        DefaultAggregateLifecycle defaultAggregateLifecycle = new DefaultAggregateLifecycle(eventBus,eventPublisher);
        gateway = new TestCommandGateway(queryGateway);
    }

    @Test
    public void testName(){
        String s = Aggregates.eventToName(TestCreated.class);
        assertTrue(s.equals("testcreated_1"),String.format("result is %s",s));
    }

    @Test
    public void findCommand(){
        List<AggregateCommandMethod> commandMethods = Aggregates.commandMethods(TestAggregate.class);
        assertFalse(commandMethods.isEmpty());
    }

    @Test
    public void findEvent(){
        Set<EventMethod> eventMethods = Aggregates.eventMethods(TestAggregate.class);
        assertFalse(eventMethods.isEmpty());
    }



}
