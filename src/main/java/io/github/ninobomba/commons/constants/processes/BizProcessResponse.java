package io.github.ninobomba.commons.constants.processes;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class BizProcessResponse {

    private Status status;
    private String message;

    // Static constants returning INSTANCES of BizProcessResponse, wrapping the status
    public static final BizProcessResponse SUCCESS = new BizProcessResponse(Status.SUCCESS, "Success");
    public static final BizProcessResponse FAILURE = new BizProcessResponse(Status.FAILURE, "Failure");

    public BizProcessResponse() {
    }

    public BizProcessResponse(Status status, String message) {
        this.status = status;
        this.message = message;
    }

    public static BizProcessResponseBuilder builder() {
        return new BizProcessResponseBuilder();
    }

    public static class BizProcessResponseBuilder {
        private Status status;
        private String message;

        public BizProcessResponseBuilder status(Status status) {
            this.status = status;
            return this;
        }

        public BizProcessResponseBuilder message(String message) {
            this.message = message;
            return this;
        }

        public BizProcessResponse build() {
            return new BizProcessResponse(status, message);
        }
    }

    public enum Status {
        SUCCESS,
        FAILURE,
        PROCESSED,
        PROCESSING,
        CREATED,
        PENDING,
        CANCELLED,
        UNAUTHORIZED,
        CONFLICTED,
        ACTIVE,
        INACTIVE,
        SUSPENDED,
        COMPLETED,
        DELETED,
        EXPIRED,
        BLOCKED,
        PAUSED,
        ON_HOLD,
        IN_REVIEW,
        SCHEDULED,
        RETRYING,
        ERROR,
        TIMEOUT,
        ABORTED,
        NOT_FOUND,
        NO_MATCH,
        NOT_IMPLEMENTED,
        VALID,
        VALIDATION_FAILED,
        VALIDATION_ERROR,
        VALIDATION_WARNING,
        INVALID,
        UNKNOWN;

        private static final Set<Status> SUCCESS_STATUSES = Set.of(
                SUCCESS, PROCESSED, CREATED, COMPLETED, VALID, DELETED, ACTIVE, INACTIVE
        );

        public boolean isSuccess() {
            return SUCCESS_STATUSES.contains(this);
        }
    }
}
