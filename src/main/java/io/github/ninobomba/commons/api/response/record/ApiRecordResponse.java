package io.github.ninobomba.commons.api.response.record;


import io.github.ninobomba.commons.constants.DefaultValueConstants;

import java.io.Serializable;
import java.util.Optional;

public sealed interface ApiRecordResponse permits ApiRecordResponse.BasicError, ApiRecordResponse.BasicErrorWithDescription, ApiRecordResponse.Error, ApiRecordResponse.Success {

	enum Messages {
		;
		public static final String SUCCESS_MESSAGE = "Request processed";
		public static final String FAILED_MESSAGE = "Request Failed ";
	}

	record Success(
			String id,
			String message,
			Object data
	) implements Serializable, ApiRecordResponse {
	}

	record BasicError(
			String id,
			String message
	) implements Serializable, ApiRecordResponse {
		public BasicError {
			id = Optional.ofNullable ( id ).orElse ( DefaultValueConstants.DefaultStringValues.DEFAULT_STRING );
			message = Optional.ofNullable ( message ).orElse ( Messages.FAILED_MESSAGE );
		}
	}

	record BasicErrorWithDescription(
			String id,
			String message,
			String description
	) implements Serializable, ApiRecordResponse {
		public BasicErrorWithDescription {
			id = Optional.ofNullable ( id ).orElse ( DefaultValueConstants.DefaultStringValues.DEFAULT_STRING );
			message = Optional.ofNullable ( message ).orElse ( Messages.FAILED_MESSAGE );
			description = Optional.ofNullable ( description ).orElse ( DefaultValueConstants.DefaultStringValues.DEFAULT_DESCRIPTION_VALUE );
		}
	}

	record Error(
			String id,
			String field,
			String value,
			String message,
			String description
	) implements Serializable, ApiRecordResponse {
		public Error {
			id = Optional.ofNullable ( id ).orElse ( DefaultValueConstants.DefaultStringValues.DEFAULT_STRING );
			field = Optional.ofNullable ( field ).orElse ( DefaultValueConstants.DefaultStringValues.DEFAULT_FIELD_NAME );
			value = Optional.ofNullable ( value ).orElse ( DefaultValueConstants.DefaultStringValues.DEFAULT_FIELD_VALUE );
			message = Optional.ofNullable ( message ).orElse ( Messages.FAILED_MESSAGE );
			description = Optional.ofNullable ( description ).orElse ( DefaultValueConstants.DefaultStringValues.DEFAULT_DESCRIPTION_VALUE );
		}
	}

}
