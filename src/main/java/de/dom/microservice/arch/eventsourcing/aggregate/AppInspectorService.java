package de.dom.microservice.arch.eventsourcing.aggregate;

import org.reflections.Reflections;
import org.reflections.scanners.*;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

public class AppInspectorService {

    public static Reflections reflections(String app){
        String javaMainClass = System.getenv("JAVA_MAIN_CLASS");
        ConfigurationBuilder configBuilder =
                new ConfigurationBuilder()
                        .filterInputsBy(new FilterBuilder())
                        .setUrls( ClasspathHelper.forPackage(app,ClasspathHelper.contextClassLoader()) )
                        .setExpandSuperTypes(false)
                        .setScanners(
                                new TypeAnnotationsScanner(),
                                new MethodParameterScanner(),
                                new MethodAnnotationsScanner(),
                                new FieldAnnotationsScanner(),
                                new SubTypesScanner()
                        );
        return new Reflections( configBuilder );
    }

}
