package io.github.ninobomba.commons.data.dtos;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
public abstract class AbstractDtoDefaultValues {
	
	private String        createdBy;
	private String        lastModifiedBy;
	
	private LocalDateTime createdAt;
	private LocalDateTime lastModifiedAt;
	
	public AbstractDtoDefaultValues ( ) {
		final LocalDateTime currentDateTime = LocalDateTime.now ( );
		this.createdAt = currentDateTime;
		this.lastModifiedAt = currentDateTime;
	}
	
}
