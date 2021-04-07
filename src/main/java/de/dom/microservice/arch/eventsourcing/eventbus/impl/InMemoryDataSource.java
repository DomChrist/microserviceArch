package de.dom.microservice.arch.eventsourcing.eventbus.impl;

import de.dom.microservice.arch.eventsourcing.events.SignatureGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryDataSource {

    private AggregateStore aggregateStore = new AggregateStore();

    public List<InMemoryEventItem> items(String reference, Class<?> aggregate) {

        String signature = SignatureGenerator.aggregateSignature(aggregate);

        List<InMemoryEventItem> items = aggregateStore.findByAggregate(reference, signature);
        return items;
    }

    public void save(InMemoryEventItem event, Class<?> aggregate) {
        String signature = SignatureGenerator.aggregateSignature(aggregate);

        event.setAggregate( signature );

        aggregateStore.save( event , signature );

        if( aggregateStore == null ) aggregateStore = new AggregateStore();


        aggregateStore.save( event , signature );

    }


    private class AggregateStore {

        private Map<String , EventItemStore> storage = new HashMap<>();


        public void save(InMemoryEventItem event , String aggregateSignature ) {
            EventItemStore eventItemStore;
            if( this.storage.containsKey(aggregateSignature) ){
                eventItemStore = this.storage.get(aggregateSignature);
            } else {
                eventItemStore = new EventItemStore();
                this.storage.put( aggregateSignature , eventItemStore );
            }
            eventItemStore.save( event );
        }

        public List<InMemoryEventItem> findByAggregate(String reference , String signature) {
            if( storage.containsKey(signature) ){
                EventItemStore eventItemStore = storage.get(signature);
                List<InMemoryEventItem> items = eventItemStore.items(reference);
                return items;
            }
            return new ArrayList<InMemoryEventItem>();
        }
    }

    private class EventItemStore {

        private Map<String , List<InMemoryEventItem>> storage = new HashMap<>();

        protected List<InMemoryEventItem> items( String reference ){
            List<InMemoryEventItem> items = storage.get(reference);
            if( items == null ) return new ArrayList<>();
            return items;
        }

        protected void save( InMemoryEventItem item ){
            List<InMemoryEventItem> list;
            if( storage.containsKey(item.getReference()) ){
                list = storage.get(item.getReference());
            } else {
                list = new ArrayList<>();
                this.storage.put( item.getReference() , list );
            }
            list.add( item );
        }

    }
}
