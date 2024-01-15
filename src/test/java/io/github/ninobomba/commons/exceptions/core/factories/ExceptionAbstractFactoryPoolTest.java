package io.github.ninobomba.commons.exceptions.core.factories;

import io.github.ninobomba.commons.exceptions.types.system.SystemIllegalAccessException;
import org.junit.jupiter.api.Test;

class ExceptionAbstractFactoryPoolTest {


    @Test
    void testList() {
        var abstractFactory = ExceptionAbstractFactoryPool.getInstance();
        assert abstractFactory .getFactoryNames().size() > 0;
        abstractFactory.getFactoryNames().forEach(System.out::println);
    }

    @Test
    void test() {

        var abstractFactory = ExceptionAbstractFactoryPool.getInstance();
        assert abstractFactory != null;

        var factory = abstractFactory.getFactory( SystemIllegalAccessException.class );
        assert factory != null;
        System.out.println( "size: " + factory.getSize() );

        var pool = factory.getPool();
        assert pool != null;
        System.out.println( "pool: " + pool );

        var exception = pool.getObject();
        assert exception != null;
        System.out.println( "exception: " + exception );

        factory.returnObject( pool );
        pool.returnObject();

        abstractFactory.shutdown();
    }


}
