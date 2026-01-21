package io.github.ninobomba.commons.patterns.process;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

public sealed interface Result<T, E> permits Result.Success, Result.Failure {

    record Success<T, E>(T value) implements Result<T, E> {
        public Success {
        }
    }

    record Failure<T, E>(E error) implements Result<T, E> {
        public Failure {
            Objects.requireNonNull(error, "error cannot be null");
        }
    }

    static <T, E> Result<T, E> success(T value) {
        return new Success<>(value);
    }

    static <T, E> Result<T, E> failure(E error) {
        return new Failure<>(error);
    }

    default boolean isSuccess() {
        return this instanceof Success;
    }

    default boolean isFailure() {
        return this instanceof Failure;
    }

    default T getOrNull() {
        if (this instanceof Success<T, E>(T value)) {
            return value;
        }
        return null;
    }

    default E errorOrNull() {
        if (this instanceof Failure<T, E>(E error)) {
            return error;
        }
        return null;
    }

    default Result<T, E> onSuccess(Consumer<? super T> action) {
        if (this instanceof Success<T, E>(T value)) {
            action.accept(value);
        }
        return this;
    }

    default Result<T, E> onFailure(Consumer<? super E> action) {
        if (this instanceof Failure<T, E>(E error)) {
            action.accept(error);
        }
        return this;
    }

    default <U> Result<U, E> map(Function<? super T, ? extends U> mapper) {
        if (this instanceof Success<T, E>(T value)) {
            return Result.success(mapper.apply(value));
        }
        @SuppressWarnings("unchecked")
        Result<U, E> failure = (Result<U, E>) this;
        return failure;
    }

    default <F> Result<T, F> mapError(Function<? super E, ? extends F> mapper) {
        if (this instanceof Failure<T, E>(E error)) {
            return Result.failure(mapper.apply(error));
        }
        @SuppressWarnings("unchecked")
        Result<T, F> success = (Result<T, F>) this;
        return success;
    }
}
