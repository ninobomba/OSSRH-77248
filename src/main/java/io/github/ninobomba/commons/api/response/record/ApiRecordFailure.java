package io.github.ninobomba.commons.api.response.record;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Represents a general-purpose failure mechanism that encapsulates detailed error information.
 * The `ApiRecordFailure` record is designed to handle error metadata such as messages, reasons, error codes,
 * internationalization details, causes, and nested failure details.
 *
 * @param message       The human-readable message describing the failure.
 * @param reason        An optional field specifying the reason behind the failure.
 * @param errorCode     An optional field for a specific error code to categorize the failure.
 * @param i18nCode      An optional field specifying an internationalization code for localization purposes.
 * @param i18nArgs      An optional set of arguments for internationalization formatting.
 * @param cause         An optional field representing the root cause of the failure as a Throwable.
 * @param details       An optional list of nested `ApiRecordFailure` objects representing additional details about the error.
 * @param errorData     An optional field allowing clients to attach error-specific data.
 * @param <ErrorType>   The type of the error-specific data.
 * @param <ErrorDetail> The type of data contained in nested failure details.
 */
public record ApiRecordFailure <ErrorType, ErrorDetail>(
    String message,
    Optional<String> reason,
    Optional<String> errorCode,
    Optional<String> i18nCode,
    Optional<Set<?>> i18nArgs,
    Optional<Throwable> cause,
    Optional<List< ApiRecordFailure <ErrorDetail, Void> >> details,
    Optional<ErrorType> errorData
) {
    public static ApiRecordFailure <String, ?> of( String message) {
        return new ApiRecordFailure <> (
            message,
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.of(message)
        );
    }

    public static ApiRecordFailure <String, ?> of( String message, String reason) {
        return new ApiRecordFailure <> (
            message,
            Optional.ofNullable(reason),
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.of(message)
        );
    }

    public static <T> ApiRecordFailure <T, ?> of( String message, T errorData) {
        return new ApiRecordFailure <T, Void> (
            message,
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.of(errorData)
        );
    }

    public static <ErrorDetail> ApiRecordFailure <String, ErrorDetail> ofViolations(
        String message,
        List< ApiRecordFailure <ErrorDetail, Void> > details
    ) {
        return ApiRecordFailure.of(message, message, details);
    }

    public static <T, ErrorDetail> ApiRecordFailure <T, ErrorDetail> of(
        String message,
        T errorData,
        List< ApiRecordFailure <ErrorDetail, Void> > details
    ) {
        return new ApiRecordFailure <> (
            message,
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.of(details),
            Optional.of(errorData)
        );
    }
}
