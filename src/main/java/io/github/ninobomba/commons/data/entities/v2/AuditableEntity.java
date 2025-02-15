package io.github.ninobomba.commons.data.entities.v2;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public abstract class AuditableEntity {

	private String createdBy;
	private String lastModifiedBy;

	private Instant createdDate;
	private Instant lastModifiedDate;
	
	private String status;
	private String version;

	public AuditableEntity ( ) {
		this.createdDate = Instant.now ( );
		this.lastModifiedDate = Instant.now ( );
		this.status = "NEW";
		this.version = "1.0.0";
	}

}
