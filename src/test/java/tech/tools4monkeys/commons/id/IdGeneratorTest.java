package tech.tools4monkeys.commons.id;

import org.junit.jupiter.api.Test;


public class IdGeneratorTest {

    @Test
    public void getNextIdTest() {
        var id = IdGenerator.getInstance().getNextId();
        System.out.println( "IdGenerator: getNextId(): " + id );
        assert ( id > 0 );
    }


}
