package io.github.ninobomba.commons.api.response.common;

import java.io.Serializable;

public final class ApiRecordResponse {

	public sealed interface Response permits ApiRecordResponseSuccess, ApiRecordResponseError {
	}

	public record ApiRecordResponseSuccess(
			String id,
			String field,
			String value,
			String message,
			boolean success
	) implements Serializable, Response {
	}

	record ApiRecordResponseError(
			String id,
			String field,
			String value,
			String message,
			String description,
			boolean success
	) implements Serializable, Response {
	}


}
