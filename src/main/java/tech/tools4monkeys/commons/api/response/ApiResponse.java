package tech.tools4monkeys.commons.api.response;

import lombok.Builder;
import lombok.Getter;

import java.util.Optional;

@Builder
@Getter
public class ApiResponse
{

    private final String field;
    private final String value;
    private final String message;

    @Builder.Default
    private final boolean success = false;

    private final Optional<Throwable> throwable;

}
