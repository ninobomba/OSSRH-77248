package io.github.ninobomba.commons.exceptions.core.factories;

import io.github.ninobomba.commons.exceptions.types.system.SystemIllegalAccessException;
import io.github.ninobomba.commons.project.IPackageUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ExceptionProviderFactoryPoolTest {

    @Test
    void test() throws InterruptedException {

        var factoryPool = new ExceptionProviderFactoryPool<>(SystemIllegalAccessException.class);

        var exception = factoryPool.getException();
        assert exception instanceof SystemIllegalAccessException;
        System.out.println( exception );

        exception.initCause( new Throwable( "XYZ" ) );
        System.out.println(  "getCause: " + exception.getCause() );

        exception.addSuppressed( new Throwable(" Suppresed ACX" ) );
        System.out.println( "getSuppressed" + ReflectionToStringBuilder.toString( exception.getSuppressed() ) );

        System.out.println( "getLocalizedMessage: " + exception.getLocalizedMessage());

//        exception.setMessage( exception, "hola");
        System.out.println( "getMessage: " + exception.getMessage() );

        System.out.println( "Factory size " + factoryPool.getSize() );

        factoryPool.shutdown();

        Assertions.assertThrows(SystemIllegalAccessException.class, () -> {
            throw exception;
        });

        // TODO: // Implement return
//        factoryPool.returnObject(f );
    }

    @Test
    void testAll() {

        var classes = IPackageUtils.getCustomExceptionSet("io.github.ninobomba.commons.exceptions.types");
        classes.forEach(e -> {
//            System.out.println(e);

            var factoryPool = new ExceptionProviderFactoryPool<>( e );
            var exception = factoryPool.getException();
            System.out.println( exception );
        });

    }

}
