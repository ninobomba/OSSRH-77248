package io.github.ninobomba.commons.api.response.record;

import io.github.ninobomba.commons.constants.DefaultValueConstants;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;

import static io.github.ninobomba.commons.api.response.record.ApiRecordResponse.Messages.FAILED_MESSAGE;
import static io.github.ninobomba.commons.api.response.record.ApiRecordResponse.Messages.SUCCESS_MESSAGE;

public interface ApiResponseCreator {

    @NotNull
    private static < T > ApiRecordResponse success (
            String id,
            T data
    ) {
        return new ApiRecordResponse.Success(
                id,
                SUCCESS_MESSAGE,
                data
        );
    }

    @NotNull
    private static < T > ApiRecordResponse success (
            String id,
            T data,
            String message
    ) {
        return new ApiRecordResponse.Success(
                id,
                Optional.ofNullable( message ).orElse( SUCCESS_MESSAGE ),
                data
        );
    }

    @NotNull
    private static ApiRecordResponse failure (
            String id,
            String message
    ) {
        return new ApiRecordResponse.Error(
                id,
                DefaultValueConstants.DefaultStringValues.DEFAULT_FIELD_NAME,
                DefaultValueConstants.DefaultStringValues.DEFAULT_FIELD_VALUE,
                Optional.ofNullable( message ).orElse( FAILED_MESSAGE ),
                DefaultValueConstants.DefaultStringValues.DEFAULT_DESCRIPTION_VALUE
        );
    }

    @NotNull
    private static ApiRecordResponse failure (
            String id,
            String message,
            String description
    ) {
        return new ApiRecordResponse.Error(
                id,
                DefaultValueConstants.DefaultStringValues.DEFAULT_FIELD_NAME,
                DefaultValueConstants.DefaultStringValues.DEFAULT_FIELD_VALUE,
                Optional.ofNullable( message ).orElse( FAILED_MESSAGE ),
                description
        );
    }

    @NotNull
    private static ApiRecordResponse failure (
            String id,
            String field,
            String value,
            String message,
            String description
    ) {
        return new ApiRecordResponse.Error(
                id,
                field,
                value,
                Optional.ofNullable( message ).orElse( FAILED_MESSAGE ),
                description
        );
    }
}
