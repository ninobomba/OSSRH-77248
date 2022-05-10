package io.github.ninobomba.commons.api.response;

import lombok.*;

import java.util.Optional;

@Getter
@Builder
public class ApiErrorResponse
{
    private static final long serialVersionUID = -4107701302700942933L;

    private final String field;
    private final String value;
    private final String message;

    @Builder.Default
    private final boolean success = false;

    private final Optional<Throwable> throwable;

}
