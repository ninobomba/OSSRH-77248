package io.github.ninobomba.commons.playground;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.function.Function;

import static java.util.stream.Collectors.*;

class lambdasSample {

    private static final Logger logger = LoggerFactory.getLogger(lambdasSample.class);
    List<Integer> numbers = Arrays.asList( 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10 );

    int result;
    double doubleResult;

    @Test
    void sample() {

        result = numbers.stream().reduce( 0, Integer::sum );
        System.out.println( result );

        result = numbers.stream().reduce( ( a, b ) -> a + b ).get();
        System.out.println( result );

        result = numbers.stream().mapToInt( e -> e ).sum();
        System.out.println( result );



        doubleResult = numbers.stream().mapToInt( e -> e ).average().getAsDouble();
        System.out.println( doubleResult );

        doubleResult = numbers.stream().mapToInt( Integer::intValue ).average().getAsDouble();
        System.out.println( doubleResult );


        System.out.println();
        numbers.stream().mapToInt( Integer::intValue ).map( e -> e * e ).filter( e -> e > 5 ).forEach( System.out::print );

        System.out.println();
        numbers.stream().filter( e -> e % 2 == 0 ).mapToInt(Integer::intValue).forEach( System.out::print );

        System.out.println();
        numbers.stream().filter( e -> e % 2 != 0 ).mapToInt(Integer::intValue).forEach( System.out::print );

        System.out.println();
        numbers.stream().mapToInt( Integer::intValue ).filter( e -> e % 2 != 0 ).forEach( System.out::print );


        System.out.println();
        numbers.stream().map( String::valueOf ).filter( e -> e.startsWith( "2" ) ).toList().forEach( System.out::print );
        System.out.println();

        // duplicates
        System.out.println( "duplicates > " );
        numbers.stream().mapToInt(Integer::intValue).filter( e -> Collections.frequency( numbers, e ) > 1 ).distinct().forEach( System.out::print );

        System.out.println();
        numbers.stream().mapToInt( Integer::intValue ).min().ifPresent( System.out::print );

        System.out.println();
        int min = numbers.stream().min( Comparator.comparing( Integer::valueOf )).get();

        System.out.println();
        numbers.stream().mapToInt( Integer::intValue ).max().ifPresent( System.out::print );

        System.out.println();
        int max = numbers.stream().max( Comparator.comparing(Integer::valueOf)).get();

        System.out.println();
        numbers.stream().sorted().toList().forEach( System.out::print );

        System.out.println();
        numbers.stream().sorted( Collections.reverseOrder( ) ).toList().forEach( System.out::print );

        System.out.println(); // Second Highest
        numbers.stream().sorted( Collections.reverseOrder( ) ).distinct().limit(2 ).skip(1 ).findFirst().ifPresent( System.out::print );
        numbers.stream().sorted( Collections.reverseOrder( ) ).distinct().skip(1 ).findFirst().ifPresent( System.out::print );

        numbers.stream().sorted().limit(2 ).skip(1 ).findFirst().ifPresent( System.out::print );

    }

    @Test
    void map() {


        var map = Arrays
                .stream( "aabbppc".split( "" ) )
                .collect( groupingBy( Function.identity(), counting() ) );

        System.out.println( map );

        var evenFilteredMap = map
                .entrySet()
                .stream()
                .filter( e -> e.getValue() % 2 == 0 )
                .collect( toMap( Map.Entry::getKey, Map.Entry::getValue ) );

        if( ! evenFilteredMap.isEmpty() )
            return;

        var oddFilteredMap = map
                .entrySet()
                .stream()
                .filter( e -> e.getValue() % 2 != 0 )
                .collect( toMap( Map.Entry::getKey, Map.Entry::getValue ) );

        oddFilteredMap.forEach((k,v) -> logger.error("k: " + k + " v: " + v));

        if( oddFilteredMap.isEmpty() )
            System.out.println( "palindrome !!!" );
        else if ( oddFilteredMap.size() == 1 && oddFilteredMap.values().stream().findFirst().get() ==  1  )
            System.out.println( "-> palindrome !!!" );
        else
            System.out.println( "no palindrome" );
    } // ccc c ccc


}
