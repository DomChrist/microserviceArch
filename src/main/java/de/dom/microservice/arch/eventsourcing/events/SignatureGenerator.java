package de.dom.microservice.arch.eventsourcing.events;

import org.apache.commons.lang3.StringUtils;

public class SignatureGenerator {

    private SignatureGenerator() {
    }

    public static String signature(Class<? extends AbstractDomainEvent> event){
        return name( event.getSimpleName() , event.getDeclaredAnnotation(DomainEvent.class) );
    }

    private static String name( String name , DomainEvent event ){
        String finalName = name;
        int finalVersion = 1;
        if( event != null ){
            if(StringUtils.isNotBlank( event.name() ) ){
                finalName = event.name();
                finalVersion = event.version() <= 0 ? 1 : event.version();
            }
        }
        return (finalName + "_" + finalVersion).toLowerCase();
    }

    public static String signature(String name, int version) {
        int finalVersion = version <= 0 ? 1 : version;
        return (name + "_" + finalVersion).toLowerCase();
    }

    public static String aggregateSignature( Class<?> clazz ){
        return clazz.getSimpleName().toLowerCase();
    }

}
