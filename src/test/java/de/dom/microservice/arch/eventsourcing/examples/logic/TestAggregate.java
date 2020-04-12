package de.dom.microservice.arch.eventsourcing.examples.logic;

import de.dom.microservice.arch.ddd.annotations.Aggregate;
import de.dom.microservice.arch.eventsourcing.annotations.AggregateCommandHandler;
import de.dom.microservice.arch.eventsourcing.annotations.AggregateEventHandler;
import de.dom.microservice.arch.eventsourcing.event.AggregateLifecycle;
import de.dom.microservice.arch.eventsourcing.examples.DefaultAggregateLifecycle;
import de.dom.microservice.arch.eventsourcing.examples.logic.values.TestStatus;

@Aggregate
public class TestAggregate {

    public static boolean CREATED = false;

    private String reference;
    private TestStatus status = TestStatus.defaultStatus();

    public TestAggregate() {
    }

    @AggregateCommandHandler
    public TestAggregate(CreateTest cmd){
        TestCreated testCreated = new TestCreated(cmd.getReference());
        DefaultAggregateLifecycle.apply( testCreated);
    }

    @AggregateEventHandler
    public void created( TestCreated event ){
        reference = event.getReference();
        CREATED = true;
    }

    @AggregateCommandHandler
    public void open( OpenTest cmd ){
        if( status.isTestClosed() ){
            throw new IllegalArgumentException("Test is already closeed");
        }
        AggregateLifecycle.apply( new TestOpened(cmd.getReference()));
    }

    @AggregateEventHandler
    public void openTest( TestOpened open ){
        this.status = this.status.open(open.getCreated());
    }

    @AggregateCommandHandler
    public void closeTest( CloseTest cmd ){
        if( !status.isOpen() ) throw new IllegalArgumentException("Test is not open");
        AggregateLifecycle.apply( TestClosed.of(cmd) );
    }

    @AggregateEventHandler
    public void testClosed(TestClosed event){
        this.status = status.closed(event.getCreated());
    }


    public String getReference() {
        return reference;
    }

    public TestStatus getStatus() {
        return status;
    }
}
