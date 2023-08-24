package io.github.ninobomba.commons.id;

import org.junit.jupiter.api.Test;


class IdGeneratorSnowFlakeSupportTest {

    @Test
    void getNextIdTest() {
        var id = IdGeneratorSnowFlakeSupport.getInstance().getNextId();
        System.out.println( "IdGenerator: getNextId(): " + id );
        assert ( id > 0 );
    }


}
