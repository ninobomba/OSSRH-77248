package io.github.ninobomba.commons.patterns.process;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

class ResultTest {

    @Test
    void testSuccess() {
        Result<String, Integer> result = Result.success("OK");

        assertTrue(result.isSuccess());
        assertFalse(result.isFailure());
        assertEquals("OK", result.getOrNull());
        assertNull(result.errorOrNull());
    }

    @Test
    void testFailure() {
        Result<String, Integer> result = Result.failure(404);

        assertFalse(result.isSuccess());
        assertTrue(result.isFailure());
        assertNull(result.getOrNull());
        assertEquals(404, result.errorOrNull());
    }

    @Test
    void testOnSuccess() {
        AtomicReference<String> value = new AtomicReference<>();
        Result.success("test").onSuccess(value::set);
        assertEquals("test", value.get());
    }

    @Test
    void testOnFailure() {
        AtomicReference<Integer> error = new AtomicReference<>();
        Result.failure(500).onFailure(error::set);
        assertEquals(500, error.get());
    }

    @Test
    void testMap() {
        Result<String, Integer> result = Result.success("123");
        Result<Integer, Integer> mapped = result.map(Integer::parseInt);

        assertTrue(mapped.isSuccess());
        assertEquals(123, mapped.getOrNull());
    }

    @Test
    void testMapError() {
        Result<String, String> result = Result.failure("error");
        Result<String, Integer> mapped = result.mapError(String::length);

        assertTrue(mapped.isFailure());
        assertEquals(5, mapped.errorOrNull());
    }
}
