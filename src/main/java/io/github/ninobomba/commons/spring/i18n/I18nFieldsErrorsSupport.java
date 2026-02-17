package io.github.ninobomba.commons.spring.i18n;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;

import java.util.Locale;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class I18nFieldsErrorsSupport {

    public static String build(BindingResult bindingResult) {
        return bindingResult
                .getFieldErrors()
                .stream()
                .map(e ->
                        String.format(
                                "%s . %s . %s . %s",
                                StringUtils.isNotBlank(e.getField()) ? "Field Name : [" + e.getField() + "] " : "",
                                StringUtils.isNotBlank(String.valueOf(e.getRejectedValue())) ? "Rejected Value : [" + e.getRejectedValue() + "] " : "",
                                StringUtils.isNotBlank(e.getCode()) ? "Code : [" + e.getCode() + "] " : "",
                                StringUtils.isNotBlank(e.getDefaultMessage()) ? "Error : [" + e.getDefaultMessage() + "]" : ""
                        )
                )
                .collect(Collectors.joining(" | "));
    }

    public static String getMessage(MessageSource messageSource, String code, String defaultMessage, Locale locale) {
        return messageSource.getMessage(code, null, "-> " + defaultMessage, locale);
    }

}
