package de.dom.microservice.arch.eventsourcing.command;

import de.dom.microservice.arch.eventsourcing.aggregates.Aggregates;
import de.dom.microservice.arch.eventsourcing.examples.logic.TestAggregate;
import org.junit.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AggregateCommandMethodTest {


    @Test
    public void methodTest(){
        List<AggregateCommandMethod> methods = Aggregates.commandMethods(TestAggregate.class);
            for( AggregateCommandMethod m : methods ){
                boolean b = m.aggregate() != null;

                assertTrue(b);
            }
    }

}
