package io.github.ninobomba.commons.exceptions.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.SneakyThrows;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.FieldError;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The ExceptionUtils interface provides utility methods for working with exceptions.
 */
public interface IExceptionUtils {
    ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .enable(SerializationFeature.INDENT_OUTPUT);


    /**
     * Converts a Throwable object to a String representation.
     *
     * @param throwable the Throwable object to convert
     * @return the String representation of the Throwable object
     */
    @SneakyThrows
    static String convertToString(Throwable throwable) {
        if (Objects.isNull(throwable)) {
            return null;
        }

        StringJoiner response = new StringJoiner("\n");
        addStackTrace(response, throwable);

        String CAUSED_BY = "Caused By:";

        Throwable cause = throwable.getCause();
        while (Objects.nonNull(cause)) {
            response.add(CAUSED_BY);
            addStackTrace(response, cause);
            cause = cause.getCause();
        }
        return response.toString();
    }

    private static void addStackTrace(StringJoiner response, Throwable throwable) {
        String INDENTATION = "    ";
        response.add(throwable.getClass() + ": " + throwable.getMessage());
        Arrays.stream(throwable.getStackTrace())
                .forEach(trace -> response.add(String.format("%sat %s.%s(%s:%d)",
                        INDENTATION,
                        trace.getClassName(),
                        trace.getMethodName(),
                        trace.getFileName(),
                        trace.getLineNumber())));
    }

    /**
     * Converts a map to its JSON string representation.
     *
     * @param map            The map to be converted
     * @param skipStackTrace Flag indicating whether to skip the stack trace
     * @return JSON string representation of the map
     */
    @SneakyThrows
    static String toJsonString(Map<String, Serializable> map, boolean skipStackTrace) {
        Map<String, Serializable> result = skipStackTrace ? new HashMap<>(map) : map;
        if (skipStackTrace)
            result.remove("throwable");
        return OBJECT_MAPPER.writeValueAsString(result);
    }

    static String formatFieldError(FieldError fieldError) {
        String field = fieldError.getField();
        String value = String.valueOf(fieldError.getRejectedValue());

        // Redact likely sensitive fields
        if (field.toLowerCase(Locale.ROOT).matches(".*(password|secret|token|credential).*")) {
            value = "[redacted]";
        } else if (value != null && value.length() > 64) {
            value = value.substring(0, 61) + "...";
        }

        return String.format("Field '%s' with value '%s': %s",
                field,
                value,
                fieldError.getDefaultMessage());
    }

    static String extractDataIntegrityMessage(DataIntegrityViolationException exception) {
        String message = Optional.ofNullable(exception.getMessage())
                .orElse("")
                .toLowerCase(Locale.ROOT);

        return switch (message) {
            case String m when m.contains("duplicate key") || m.contains("duplicate entry") ->
                    "A record with this information already exists";
            case String m when m.contains("date_of_birth") -> "Date of birth must be in the past (YYYY-MM-DD)";
            case String m when m.contains("foreign key") || m.contains("constraint") ->
                    "Invalid reference to related data";
            case String m when m.contains("not-null") || m.contains("cannot be null") -> "Required field is missing";
            default -> "Data integrity constraint violation";
        };
    }

    static String extractGenericExceptionMessage(Exception exception) {
        String original = exception.getMessage();
        if (original == null) {
            return "An unexpected error occurred";
        }

        // Prefer structured validation extraction if present
        if (original.contains("ConstraintViolation")) {
            var violationMap = new HashMap<String, String>();
            Arrays.stream(original.split("ConstraintViolation"))
                    .filter(violation -> violation.contains("propertyPath="))
                    .forEach(violation -> {
                        String property = violation
                                .replaceAll(".*propertyPath=([^,]+),.*", "$1")
                                .replaceAll("[^\\w\\s.-]", "")
                                .trim();
                        String interpolated = violation
                                .replaceAll(".*interpolatedMessage='([^']+)'.*", "$1")
                                .replaceAll("[^\\w\\s.-]", "")
                                .trim();
                        if (!property.isEmpty() && !interpolated.isEmpty()) {
                            violationMap.put(property, interpolated);
                        }
                    });

            if (!violationMap.isEmpty()) {
                String result = violationMap.entrySet().stream()
                        .map(entry -> String.format("Field '%s': %s", entry.getKey(), entry.getValue()))
                        .collect(Collectors.joining("; "));
                return result.endsWith(";") ? result.substring(0, result.length() - 1) : result;
            }
        }

        // Mask persistence-tech messages before any truncation
        String lower = original.toLowerCase(Locale.ROOT);
        if (lower.contains("jdbc") || lower.contains("sql") || lower.contains("database")) {
            return "Persistence Error";
        }

        // Truncate long messages to keep responses concise
        if (original.length() > 200) {
            return original.substring(0, 197) + "...";
        }

        return original;
    }
}
