package io.github.ninobomba.commons.api.response.builders;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * Represents an error response from an API.
 */
@Data
@Builder
public final class ApiResponseBuilderError implements Serializable {

	private final String id;
	private final String field;
	private final String value;
	private final String message;
	private final String description;

}
