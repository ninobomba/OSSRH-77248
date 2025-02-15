package io.github.ninobomba.commons.performance.id;

import io.github.ninobomba.commons.id.IdGeneratorConcurrentLinkedQueueSupport;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class IdGeneratorConcurrentLinkedQueueSupportSupportPerformanceTest {

	//	public static void main ( String[] args ) throws IOException {
	//		org.openjdk.jmh.Main.main ( args );
	//	}

	@Benchmark
	@BenchmarkMode ( Mode.Throughput )
	@Fork ( value = 1, warmups = 1 )
	@Test
	public void perform ( ) {
		Instant start = Instant.now ( );

		var accumulator = new ArrayList < Long > ( );

		int size = 10_000;
		IntStream.range ( 0, size ).forEach ( index -> {
			accumulator.add ( IdGeneratorConcurrentLinkedQueueSupport.getINSTANCE ( ).getNextId ( ) );
		} );

		assert ( accumulator.size ( ) == size );

		var duplicatedValues = accumulator
				.stream ( )
				.collect ( Collectors.groupingBy ( Function.identity ( ), Collectors.counting ( ) ) )
				.entrySet ( ).stream ( )
				.filter ( e -> e.getValue ( ) > 1 )
				.map ( Map.Entry::getKey )
				.collect ( Collectors.toSet ( ) );

		assert ( duplicatedValues.isEmpty ( ) );

		System.out.println ( "Duplicated Values: " + duplicatedValues );
		System.out.println ( "ET " + Duration.between ( start, Instant.now ( ) ).toSeconds ( ) + " toSeconds " );
	}

}
