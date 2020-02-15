package de.dom.microservice.arch.ddd;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.dom.microservice.arch.ddd.annotations.Aggregate;
import de.dom.microservice.arch.eventsourcing.command.AbstractDomainCommand;
import de.dom.microservice.arch.eventsourcing.event.AbstractDomainEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Aggregate
public abstract class DomainAggregate {

    @JsonIgnore
    private List<AbstractDomainEvent> domainEvents = new ArrayList<>();

    public <T extends AbstractDomainEvent>  void  add( T event ){
        boolean empty = this.domainEvents.stream().filter( e-> e.getUniqueEventIdentifier().equalsIgnoreCase( event.getUniqueEventIdentifier() ) ).findFirst().isEmpty();
        if( empty ) this.domainEvents.add( event );
    }

    @JsonIgnore
    public List<AbstractDomainEvent> getDomainEvents(){
        return Collections.unmodifiableList( domainEvents );
    }

    public void clear(){
        this.domainEvents.clear();
    }

    public < T extends AbstractDomainCommand> void handle( T cmd ){
        try{
            DomainAggregateMethodExecutor.handle( this , cmd);
        } catch (Exception e){
          e.printStackTrace();
        }
    }

    public <T extends AbstractDomainEvent> void applyEvent( T event ){
        try {
            DomainAggregateMethodExecutor.invoke(this, event);
            this.add( event );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
