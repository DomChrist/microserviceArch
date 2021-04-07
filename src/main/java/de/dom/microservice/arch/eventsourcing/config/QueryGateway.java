package de.dom.microservice.arch.eventsourcing.config;


import de.dom.microservice.arch.eventsourcing.aggregate.AggregateProxy;
import de.dom.microservice.arch.eventsourcing.eventbus.EventBusResult;
import de.dom.microservice.arch.eventsourcing.events.SignatureGenerator;

import java.util.Optional;

public class QueryGateway {

    private static QueryGateway queryGateway;

    private final Scope scope = Scope.newInstance();


    public static  <T> Optional<T> aggregate(String reference , Class<T> aggregate ){
        QueryGateway instance = getInstance();
        EventBusResult result = instance.scope.eventBus.read(reference, aggregate);
        try {
            AggregateProxy<?> proxy = instance.scope.aggregates
                    .findByName(SignatureGenerator.aggregateSignature(aggregate))
                    .orElseThrow( ()-> new IllegalArgumentException("No aggregate found") );
            T restore = (T) proxy.executor().restore(result);
            return Optional.ofNullable(restore);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return Optional.empty();
    }

    private static QueryGateway getInstance(){
        if( queryGateway == null ) queryGateway = new QueryGateway();
        return queryGateway;
    }


}
