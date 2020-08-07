package de.dom.microservice.arch.eventsourcing.examples;

import de.dom.microservice.arch.eventsourcing.bus.EventPublisher;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

public class DefaultEventPublisher implements EventPublisher {

    public static boolean messageSend = false;

    @Override
    public <T> void publish(T message) {
        System.out.println( String.format("Message %s send" , message.getClass().getSimpleName()) );
        messageSend = true;
    }

}
