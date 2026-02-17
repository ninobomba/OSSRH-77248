package io.github.ninobomba.commons.api.response.id;

import org.springframework.web.context.request.ServletWebRequest;

import java.util.Optional;
import java.util.UUID;

public interface IKVWebRequest {

    /**
     * Returns an Optional containing the attribute value as String if present, otherwise empty.
     * Null-safe for both webRequest and attribute presence.
     */
    static Optional<String> findAttribute(ServletWebRequest webRequest, String attributeName) {
        return getRequestAttribute(webRequest, attributeName).map(Object::toString);
    }

    /**
     * Returns the attribute value as String or an empty String if not present.
     */
    static String getAttributeAsString(ServletWebRequest webRequest, String attributeName) {
        return getAttributeAsString(webRequest, attributeName, "");
    }

    /**
     * Returns the attribute value as String or the provided defaultValue if not present.
     */
    static String getAttributeAsString(ServletWebRequest webRequest, String attributeName, String defaultValue) {
        return findAttribute(webRequest, attributeName).orElse(defaultValue);
    }

    static Long getAttributeAsLong(ServletWebRequest webRequest, String attributeName) {
        return getRequestAttribute(webRequest, attributeName)
                .map(Object::toString)
                .flatMap(IKVWebRequest::parseLong)
                .orElse(null);
    }

    static Long getAttributeAsLong(ServletWebRequest webRequest, String attributeName, Long defaultValue) {
        return getRequestAttribute(webRequest, attributeName)
                .map(Object::toString)
                .flatMap(IKVWebRequest::parseLong)
                .orElse(defaultValue);
    }

    static UUID getAttributeAsUUID(ServletWebRequest webRequest, String attributeName) {
        return getRequestAttribute(webRequest, attributeName)
                .map(Object::toString)
                .flatMap(IKVWebRequest::parseUUID)
                .orElse(null);
    }

    static UUID getAttributeAsUUID(ServletWebRequest webRequest, String attributeName, UUID defaultValue) {
        return getRequestAttribute(webRequest, attributeName)
                .map(Object::toString)
                .flatMap(IKVWebRequest::parseUUID)
                .orElse(defaultValue);
    }

    /**
     * Null-safe retrieval of a request attribute as Optional<Object>.
     */
    private static Optional<Object> getRequestAttribute(ServletWebRequest webRequest, String attributeName) {
        return Optional
                .ofNullable(webRequest)
                .map(ServletWebRequest::getRequest)
                .map(request -> request.getAttribute(attributeName));
    }

    /**
     * Safely parse a string into along, returning Optional.empty for invalid numbers.
     */
    private static Optional<Long> parseLong(String value) {
        try {
            return Optional.of(Long.valueOf(value));
        } catch (NumberFormatException ex) {
            return Optional.empty();
        }
    }

    /**
     * Safely parse a string into a UUID, returning Optional.empty for invalid values.
     */
    private static Optional<UUID> parseUUID(String value) {
        try {
            return Optional.of(UUID.fromString(value));
        } catch (IllegalArgumentException ex) {
            return Optional.empty();
        }
    }
}
