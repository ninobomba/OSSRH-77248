package io.github.ninobomba.commons.jpa;


import io.github.ninobomba.commons.jpa.audit.AuditableEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class AuditableEntityTest
{

    @Test
    void toStringTest()
    {
        var entity = new AuditableEntity();
        var json = entity.toJsonString(true );
        System.out.println( "Pretty Json (true):" );
        System.out.println( json );
        assertNotNull( json );

        var jsonFalse = entity.toJsonString(false );
        System.out.println( "Pretty Json (false):" );
        System.out.println( jsonFalse );
        assertNotNull( jsonFalse );
    }

}
