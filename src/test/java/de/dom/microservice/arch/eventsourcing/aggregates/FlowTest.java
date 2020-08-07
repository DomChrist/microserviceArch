package de.dom.microservice.arch.eventsourcing.aggregates;

import de.dom.microservice.arch.eventsourcing.bus.EventBus;
import de.dom.microservice.arch.eventsourcing.bus.EventPublisher;
import de.dom.microservice.arch.eventsourcing.command.CommandGateway;
import de.dom.microservice.arch.eventsourcing.event.AggregateLifecycle;
import de.dom.microservice.arch.eventsourcing.examples.*;
import de.dom.microservice.arch.eventsourcing.examples.logic.CloseTest;
import de.dom.microservice.arch.eventsourcing.examples.logic.CreateTest;
import de.dom.microservice.arch.eventsourcing.examples.logic.OpenTest;
import de.dom.microservice.arch.eventsourcing.examples.logic.TestAggregate;
import de.dom.microservice.arch.eventsourcing.gateways.QueryGateway;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FlowTest {

    private AggregateLifecycle aggregateLifecycle;

    private CommandGateway gateway;

    private QueryGateway queryGateway;

    private EventBus eventBus;

    private TestRepository repository;

    @Before
    public void setUp(){
        DefaultEventPublisher publisher = new DefaultEventPublisher();
        repository = new TestRepository();
        eventBus = new TestEventBus(repository);
        aggregateLifecycle = new DefaultAggregateLifecycle(eventBus,publisher);
        queryGateway = new TestQueryGateway( eventBus );
        gateway = new TestCommandGateway(queryGateway);

        TestAggregate.CREATED = false;

    }

    @Test
    public void command(){
        CreateTest createTest = new CreateTest();
            String reference = createTest.getReference();
        CommandGateway.send(createTest);
        assertTrue( TestAggregate.CREATED );

        OpenTest openTest = new OpenTest();
            openTest.setReference(reference);
        CommandGateway.send(openTest);

        CloseTest closeTest = new CloseTest();
            closeTest.setReference(reference);
        CommandGateway.send(closeTest);

        OpenTest reOpen = new OpenTest();
            reOpen.setReference(reference);

        try{
            CommandGateway.send( reOpen );
            assertFalse("exception must thrown",true);
        }catch (Exception e){
            assertFalse("exception must thrown",false);
        }

        Optional<?> aggregate1 = QueryGateway.aggregate(createTest.getReference(), TestAggregate.class);

        Optional<TestAggregate> aggregate = QueryGateway.aggregate(createTest.getReference(), TestAggregate.class);
        assertTrue(aggregate.isPresent());

        TestAggregate testAggregate = aggregate.get();
        assertTrue(DefaultEventPublisher.messageSend);

    }

}
