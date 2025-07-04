package io.github.ninobomba.commons.unit.checkpoints;

import io.github.ninobomba.commons.checkpoints.CheckPointFactory;
import io.github.ninobomba.commons.checkpoints.CheckPointPersistence;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CheckPointPersistenceTest
{

    @Test
    @Disabled
    void invalidPathLocationTest()
    {
        assertThrows( Exception.class, ()-> {
            List<String> paths = new ArrayList<>();
            paths.add( "/signup.json" );
            CheckPointFactory.getInstance().build( paths );
        });
    }

    @Test
    void saveTest()
    {
        List<String> paths = new ArrayList<>();
        paths.add( "checkpoints/signup.json" );

        CheckPointFactory.getInstance().build( paths );

        var map = CheckPointFactory.getInstance().getCheckPointMap( "signup" );
        assertThat( map ).isNotEmpty();

        System.out.println();

        map.forEach( (k, v) -> {

            System.out.println( "Before: " + k + ":" + v );

            assertThat( v.getCompleted() ).isNull();
            assertThat( v.getLocalDateTime() ).isNull();
            assertThat( v.getFormattedTimestamp() ).isNull();

            v.update();
            System.out.println( "After: " + k + ":" + v );

            assertThat( v.getCompleted() ).isNotNull();
            assertThat( v.getLocalDateTime() ).isNotNull();
            assertThat( v.getFormattedTimestamp() ).isNotNull();

            System.out.println();
        });

        System.out.println();

        CheckPointPersistence.getInstance().save( map );
    }

}
