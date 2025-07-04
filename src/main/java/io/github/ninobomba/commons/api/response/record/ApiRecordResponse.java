package io.github.ninobomba.commons.api.response.record;

import io.github.ninobomba.commons.constants.DefaultValueConstants;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Optional;

import static io.github.ninobomba.commons.api.response.record.ApiRecordResponse.Messages.FAILED_MESSAGE;
import static io.github.ninobomba.commons.api.response.record.ApiRecordResponse.Messages.SUCCESS_MESSAGE;

public sealed interface ApiRecordResponse permits ApiRecordResponse.Success, ApiRecordResponse.Error {

	enum Messages {
		;
		public static final String SUCCESS_MESSAGE = "Request processed";
		public static final String FAILED_MESSAGE = "Failed request";
	}

	record Success(
			String id,
			String message,
			Object data
	) implements Serializable, ApiRecordResponse {
	}

	record Error(
			String id,
			String field,
			String value,
			String message,
			String description
	) implements Serializable, ApiRecordResponse {
	}

}
