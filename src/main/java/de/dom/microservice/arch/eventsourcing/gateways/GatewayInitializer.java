package de.dom.microservice.arch.eventsourcing.gateways;

import com.google.common.reflect.ClassPath;
import org.reflections.Reflections;
import org.reflections.scanners.*;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;

public class GatewayInitializer {

    static {
        GatewayInitializer.reflection = initReflections();
    }
    private static Reflections reflection;

    public static Reflections reflections(){
        //eturn new Reflections("");
        return GatewayInitializer.reflection;
    }

    private static Reflections initReflections(){
        String javaMainClass = System.getenv("JAVA_MAIN_CLASS");
        ConfigurationBuilder configBuilder =
                new ConfigurationBuilder()
                        .filterInputsBy(new FilterBuilder())
                        .setUrls( ClasspathHelper.forPackage("",ClasspathHelper.contextClassLoader()) )
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
