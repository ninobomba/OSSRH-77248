package io.github.ninobomba.commons.api.response;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.util.Optional;

@Builder
@Getter
public class ApiResponse implements Serializable
{

    private final String id;
    private final String field;
    private final String value;
    private final String message;

    @Builder.Default
    private final boolean success = false;

    private final Optional<Throwable> throwable;

}
