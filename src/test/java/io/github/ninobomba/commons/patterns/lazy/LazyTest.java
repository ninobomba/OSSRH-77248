package io.github.ninobomba.commons.patterns.lazy;

import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;
public class LazyTest {

    @Test
    void example_1() {
        Integer value = compute();
    }

    @Test
    void example_2() {
        Supplier<Integer> value = () -> compute();
    }

    @Test
    void example_3() {
        Optional<Integer> foo = Optional.of(1);
        foo.orElse(compute()); // eager
    }

    @Test
    void example_4() {
        Optional<Integer> foo = Optional.of(1);
        foo.orElseGet(() -> compute()); // lazy
    }

    private static int compute() {
        System.out.println("Computing...");
        return 42;
    }



    public static void main(String[] args) {
        Lazy.of(() -> compute2(42))
                .map(s -> compute2(13))
                .flatMap(s -> lazyCompute(15))
                .filter(v -> v > 0);
        //.get();
    }

    private static int compute2(int val) {
        int result = ThreadLocalRandom.current().nextInt() % val;
        System.out.println("Computed: " + result);
        return result;
    }

    private static Lazy<Integer> lazyCompute(int val) {
        return Lazy.of(() -> {
            int result = ThreadLocalRandom.current().nextInt() % val;
            System.out.println("Computed: " + result);
            return result;
        });
    }

}
