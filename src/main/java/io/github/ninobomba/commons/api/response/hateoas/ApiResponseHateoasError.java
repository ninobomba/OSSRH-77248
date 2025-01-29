package io.github.ninobomba.commons.api.response.hateoas;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;

/**
 * Represents an error response from an API.
 */
@EqualsAndHashCode ( callSuper = true )
@Data
public class ApiResponseHateoasError extends RepresentationModel < ApiResponseHateoasSuccess > implements Serializable {

	private final String id;
	private final String field;
	private final String value;
	private final String message;
	private final String description;

}
