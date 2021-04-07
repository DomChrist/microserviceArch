package de.dom.microservice.arch.eventsourcing.eventbus;


import de.dom.microservice.arch.eventsourcing.eventstore.EventStoreItem;

public class DefaultEventBus implements EventBus{

    @Override
    public EventBusResult read(String key, Class<?> aggregate) {
        return null;
    }

    @Override
    public void apply(EventBusMessage message) {

    }

    public static void recreate( String reference , Class aggregate , EventBus eventBus ){
        EventBusResult read = eventBus.read(reference, aggregate);
        boolean hasSnapshots = read.hasSnapshot();
        if( hasSnapshots ){
            EventStoreItem item = (EventStoreItem) read.snapshot();

        }
    }


}
