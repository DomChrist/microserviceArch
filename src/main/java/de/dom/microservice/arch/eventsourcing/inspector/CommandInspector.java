package de.dom.microservice.arch.eventsourcing.inspector;

import de.dom.microservice.arch.eventsourcing.annotations.AggregateId;
import org.apache.commons.lang3.StringUtils;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;

public class CommandInspector {

    public static Optional<String> getId(Object ob ){
        if( ob == null ) return Optional.empty();
        Field[] fields = ob.getClass().getDeclaredFields();
        Optional<Field> field = Arrays.stream(fields)
                .filter(f -> f.isAnnotationPresent(AggregateId.class))
                .findFirst();
        if( !field.isPresent() ) throw new IllegalArgumentException("Command does not have a id");

        String id = id( field.get()  ,ob);
        if( id == null || StringUtils.isBlank(id)) return Optional.empty();

        return Optional.ofNullable(id);
    }

    private static String id( Field field, Object obj ){
        field.setAccessible( true );
        Object o = null;
        try {
            o = field.get(obj);
        } catch (IllegalAccessException e) {
            return null;
        }
        return Optional.ofNullable(o).orElse("").toString();
    }

}
