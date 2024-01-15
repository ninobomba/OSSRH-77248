package io.github.ninobomba.commons.project;

import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.util.Set;

import static org.reflections.scanners.Scanners.SubTypes;

public interface IPackageUtils {

    static Set<Class<? extends Throwable>> getCustomExceptionSet(String packageName) {
        Reflections reflections = new Reflections (
                new ConfigurationBuilder()
                        .forPackage( packageName )
                        .filterInputsBy( new FilterBuilder().excludePackage("java.lang" ) )
                        .setScanners( SubTypes )
        );
        return reflections.getSubTypesOf( Throwable.class );
    }

}
