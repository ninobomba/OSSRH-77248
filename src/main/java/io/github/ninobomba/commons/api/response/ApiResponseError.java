package io.github.ninobomba.commons.api.response;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Builder
@ToString
@EqualsAndHashCode
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
