package io.github.ninobomba.commons.id;

import org.junit.jupiter.api.Test;


class IdGeneratorTest {

    @Test
    void getNextIdTest() {
        var id = IdGenerator.getInstance().getNextId();
        System.out.println( "IdGenerator: getNextId(): " + id );
        assert ( id > 0 );
    }


}
