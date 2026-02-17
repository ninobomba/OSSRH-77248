package io.github.ninobomba.commons.api.token;


import io.github.ninobomba.commons.api.response.id.IKVWebRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Optional;

@Slf4j
@Component
public class UserTokenValidator implements IUserTokenValidator {

    @Value("${application.token.maximum-length:1000}")
    private int maxTokenLength;

    @Override
    public Optional<String> validateAndGetToken(ServletWebRequest webRequest) {
        var token = IKVWebRequest
                .findAttribute(webRequest, "TOKEN")
                .filter(this::isLongEnough);

        if (token.isEmpty()) {
            log.error("UserTokenValidator::validateAndGetToken - Invalid token provided");
        }

        return token;
    }

    @Override
    public Boolean isValid(String token) {
        return token != null && isLongEnough(token) ? Boolean.TRUE : Boolean.FALSE;
    }

    private boolean isLongEnough(String token) {
        return token.length() <= maxTokenLength;
    }
}
