package io.github.ninobomba.commons.exceptions.core.factories;

import io.github.ninobomba.commons.project.IPackageUtils;

import java.util.concurrent.ConcurrentHashMap;

public final class ExceptionAbstractFactory {

    private static final String PACKAGE = "io.github.ninobomba.commons.exceptions.types";


    private static final ConcurrentHashMap<String, ExceptionProviderFactoryPool<? extends Exception>> factoryMap = new ConcurrentHashMap<>();

    private ExceptionAbstractFactory() {
        init();
    }

    private void init() {
        var classes = IPackageUtils.getCustomExceptionSet( PACKAGE );
        classes.forEach( e -> {
            var factoryPool = new ExceptionProviderFactoryPool<>( e );
//            factoryMap.putIfAbsent( e.getSimpleName(), e );
        });

        factoryMap.forEach((k,v) -> {
            System.out.println( k + " ========= " + v);

        });
    }

    public static void main(String[] args) {
        new ExceptionAbstractFactory().init();
    }

}
