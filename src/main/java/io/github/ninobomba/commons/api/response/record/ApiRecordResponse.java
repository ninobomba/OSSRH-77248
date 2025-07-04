package io.github.ninobomba.commons.api.response.record;

import java.io.Serializable;

public sealed interface ApiRecordResponse permits ApiRecordResponse.Success, ApiRecordResponse.Error {


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
