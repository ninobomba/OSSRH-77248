package io.github.ninobomba.commons.api.response.record;

import java.io.Serializable;

public final class ApiRecordResponse {

	public sealed interface Response permits Success, Error {
	}

	public record Success(
			String id,
			String message,
			Object data
	) implements Serializable, Response {
	}

	public record Error(
			String id,
			String field,
			String value,
			String message,
			String description
	) implements Serializable, Response {
	}


}
