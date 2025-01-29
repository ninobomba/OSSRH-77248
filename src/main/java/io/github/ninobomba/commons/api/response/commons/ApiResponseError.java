package io.github.ninobomba.commons.api.response.commons;

import lombok.Data;

import java.io.Serializable;

/**
 * Represents an error response from an API.
 */
@Data
public class ApiResponseError implements Serializable {

	private final String id;
	private final String field;
	private final String value;
	private final String message;
	private final String description;

	private final boolean success = false;

}
