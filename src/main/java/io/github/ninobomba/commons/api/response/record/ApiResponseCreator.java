package io.github.ninobomba.commons.api.response.record;


import io.github.ninobomba.commons.constants.DefaultValueConstants;
import io.github.ninobomba.commons.constants.processes.BizProcessResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;
import java.util.Optional;

import static io.github.ninobomba.commons.api.response.record.ApiRecordResponse.Messages.FAILED_MESSAGE;
import static io.github.ninobomba.commons.api.response.record.ApiRecordResponse.Messages.SUCCESS_MESSAGE;
import static io.github.ninobomba.commons.api.response.record.IMappingResponse.mapStatus;
import static java.util.Optional.ofNullable;

public interface ApiResponseCreator {

    @NotNull
    private static <T> ApiRecordResponse success(
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
    private static ApiRecordResponse failure(
            String id,
            String message
    ) {
        return new ApiRecordResponse.BasicError(
                id,
                ofNullable(message).orElse(FAILED_MESSAGE)
        );
    }

    @NotNull
    private static ApiRecordResponse failure(
            String id,
            String message,
            String description
    ) {
        return new ApiRecordResponse.BasicErrorWithDescription(
                id,
                Optional.ofNullable(message).orElse(FAILED_MESSAGE),
                Optional.ofNullable(description).orElse(DefaultValueConstants.DefaultStringValues.DEFAULT_DESCRIPTION_VALUE)
        );
    }

    static <T> ResponseEntity<ApiRecordResponse> createSuccessResponse(String requestId, T response, HttpStatus status) {
        var apiResponse = success(requestId, response);
        return ResponseEntity.status(status).body(apiResponse);
    }

    // Failure - Object
    static <T> ResponseEntity<ApiRecordResponse> createFailureResponse(String requestId, T object, HttpStatus status) {
        var message = Objects.isNull(object) ? DefaultValueConstants.DefaultStringValues.DEFAULT_STRING : object.toString();
        return createFailureResponse(requestId, message, status);
    }

    static <T> ResponseEntity<ApiRecordResponse> createFailureResponse(String requestId, T object, String description, HttpStatus status) {
        var message = Objects.isNull(object) ? DefaultValueConstants.DefaultStringValues.DEFAULT_STRING : object.toString();
        return createFailureResponse(requestId, message, description, status);
    }

    // Failure String message
    static ResponseEntity<ApiRecordResponse> createFailureResponse(String requestId, String message, HttpStatus status) {
        var apiResponse = failure(requestId, message);
        return ResponseEntity.status(status).body(apiResponse);
    }

    static ResponseEntity<ApiRecordResponse> createFailureResponse(String requestId, String message, String description, HttpStatus status) {
        var apiResponse = failure(requestId, message, description);
        return ResponseEntity.status(status).body(apiResponse);
    }

    static ResponseEntity<ApiRecordResponse> toApiResponse(
            String requestId,
            Boolean response,
            BizProcessResponse.Status status,
            Object payload
    ) {
        if (response) {
            return createSuccessResponse(requestId, payload, mapStatus(status));
        }
        return createFailureResponse(requestId, payload, mapStatus(status));
    }

    static ResponseEntity<ApiRecordResponse> toApiResponse(
            String requestId,
            BizProcessResponse response,
            BizProcessResponse.Status status,
            Object payload
    ) {
        if (response == BizProcessResponse.SUCCESS) {
            return createSuccessResponse(requestId, payload, mapStatus(status));
        } else {
            return createFailureResponse(requestId, payload, mapStatus(status));
        }
    }

}
