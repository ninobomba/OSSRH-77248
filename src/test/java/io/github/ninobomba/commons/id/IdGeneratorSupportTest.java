package io.github.ninobomba.commons.id;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


class IdGeneratorSupportTest {

    @Test
    void getNextIdTest() {
        var id = IdGenerator.getInstance().getNextId();
        System.out.println( "IdGenerator: getNextId(): " + id );
        assert ( id > 0 );
    }

    @Test
    void getNextIdTest_10_000() {

        Instant start = Instant.now();

        var accumulator = new ArrayList<Long>();

        int size = 10_000;
        IntStream.range( 0, size ).forEach( index -> {
            accumulator.add( IdGenerator.getInstance().getNextId() );
        });

        assert( accumulator.size() == size );

        var duplicatedValues = accumulator
                .stream()
                .collect( Collectors.groupingBy( Function.identity(), Collectors.counting()) )
                .entrySet().stream()
                .filter( e -> e.getValue () > 1 )
                .map( Map.Entry::getKey )
                .collect( Collectors.toSet() );

        System.out.println( "Duplicated Values: " + duplicatedValues );

        assert ( duplicatedValues.isEmpty() );

        System.out.println( "ET "  + Duration.between( start, Instant.now() ).toSeconds() + " toSeconds " );
    }

}
