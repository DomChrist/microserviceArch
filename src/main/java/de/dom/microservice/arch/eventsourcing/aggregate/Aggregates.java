package de.dom.microservice.arch.eventsourcing.aggregate;

import de.dom.microservice.arch.eventsourcing.events.AbstractDomainEvent;
import de.dom.microservice.arch.eventsourcing.events.SignatureGenerator;
import de.dom.microservice.arch.eventsourcing.inspector.AppInspector;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Aggregates {

    private static Aggregates aggregates;

    private HashMap<String,AggregateProxy<?>> proxies;


    public static Aggregates init(AppInspector r ){
        aggregates = new Aggregates();

        r.aggregates().forEach( p-> aggregates.register(p) );

        return aggregates;
    }

    public void register( AggregateProxy<?> aggregate ){
        String name = aggregate.getSignature();
        if( proxies == null ) proxies = new HashMap<>();
        if( proxies.containsKey( name ) ) throw new IllegalArgumentException("Aggregate already registered");
        proxies.put( name , aggregate );
    }

    public Optional<AggregateProxy<?>> findByName( String name){
        return Optional.ofNullable(proxies.get(name));
    }

    public static Aggregates getInstance(){
        if( aggregates != null ) return aggregates;
        return null;
    }


    public Optional<AggregateProxy<?>> findAggregateForCommand(Object c) {
        Optional<AggregateProxy<?>> first = aggregates.proxies.values()
                .stream().filter(e -> e.selector().findHandlerFormCommand(c).isPresent())
                .findFirst();
        return first;
    }

    public List<AggregateProxy<?>> findAggregatesForEvent(AbstractDomainEvent event) {
        return this.proxies.values().stream()
                .filter(a -> a.selector().findHandlerByEventName(SignatureGenerator.signature(event.getClass())).isPresent())
                .collect(Collectors.toList());
    }
}
