package io.github.ninobomba.commons.constants;

public interface ApiConstants {

    enum Version {
        ;
        public static final String V1 = "V1";
    }

    enum RequestHeaders {
        ;
        public static final String X_REQUEST_ID = "X-Request-ID";
        public static final String X_EXTERNAL_REQUEST_ID = "External-Request-ID";
    }

    enum Token {
        ;
        public static final String TOKEN = "TOKEN";
        public static final String TOKEN_INVALID_REQUEST = "Invalid request - Token validation failed";
        public static final String TOKEN_SUCCESSFULLY_PROCESSED = "Token successfully processed and set as request attribute";
        public static final String TOKEN_MISSING_ERROR_MESSAGE = "Token is required for this operation";
    }

}
