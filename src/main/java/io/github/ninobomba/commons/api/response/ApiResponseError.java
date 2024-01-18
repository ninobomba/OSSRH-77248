package io.github.ninobomba.commons.api.response;

import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
public class ApiResponseError implements Serializable {
	
	@Serial
	private static final long serialVersionUID = -4107701302700942933L;
	
	private final String id;
	private final String field;
	private final String value;
	private final String message;
	private final String description;
	
	@Builder.Default
	private final boolean success = false;
	
}
