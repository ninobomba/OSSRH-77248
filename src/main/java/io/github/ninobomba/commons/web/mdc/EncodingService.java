package io.github.ninobomba.commons.web.mdc;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

import static org.springframework.web.util.HtmlUtils.htmlEscape;
import static org.springframework.web.util.JavaScriptUtils.javaScriptEscape;

public enum EncodingService {

    INSTANCE;

    // Pattern matches common special characters and potential XSS/injection characters
    private static final Pattern SANITIZE_PATTERN = Pattern.compile("[<>\"'&;/\\\\()${}#%]");

    public String encode(String value, Type type) {
        if (StringUtils.isBlank(value)) return value;
        return switch (type) {
            case REPLACE_SPECIAL_CHARACTERS -> sanitizeUsingReplaceSpecialCharacters(value);
            case SPRING -> sanitizeUsingSpring(value);
        };
    }

    public String sanitizeUsingReplaceSpecialCharacters(String value) {
        return SANITIZE_PATTERN.matcher(value).replaceAll(" ");
    }

    public String sanitizeUsingSpring(String input) {
        return htmlEscape(javaScriptEscape(input));
    }

    public enum Type {
        REPLACE_SPECIAL_CHARACTERS,
        SPRING
    }
}
