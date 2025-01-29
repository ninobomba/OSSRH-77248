package io.github.ninobomba.commons.api.response.record;

import java.io.Serializable;

public final class ApiRecordResponse {

	public sealed interface Response permits Success, Error {
	}

	public record Success(
			String id,
			String field,
			String value,
			String message,
			boolean success
	) implements Serializable, Response {

		public Success {
			success = true;
		}
	}

	public record Error(
			String id,
			String field,
			String value,
			String message,
			String description,
			boolean success
	) implements Serializable, Response {
	}


}
