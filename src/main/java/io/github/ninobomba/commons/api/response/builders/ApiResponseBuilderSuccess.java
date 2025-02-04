package io.github.ninobomba.commons.api.response.builders;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * Represents a response returned by an API.
 */
@Data
@Builder
public final class ApiResponseBuilderSuccess implements Serializable {

	private final String id;
	private final String message;
	private final Object data;

}
