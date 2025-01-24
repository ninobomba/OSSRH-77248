package io.github.ninobomba.commons.api.response.record;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * Represents a response returned by an API.
 */
@Data
@Builder
public final class ApiResponseSuccess implements Serializable {

	private final String id;
	private final String field;
	private final String value;
	private final String message;

	@Builder.Default
	private final boolean success = false;

}
