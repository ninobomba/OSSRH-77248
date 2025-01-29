package io.github.ninobomba.commons.api.response.hateoas;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;

/**
 * Represents a response returned by an API.
 */
@EqualsAndHashCode ( callSuper = true )
@Data
public class ApiResponseHateoasSuccess extends RepresentationModel < ApiResponseHateoasSuccess > implements Serializable {

	private final String id;
	private final String message;
	private final Object data;

}
