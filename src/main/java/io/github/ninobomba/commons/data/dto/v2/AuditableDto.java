package io.github.ninobomba.commons.data.dto.v2;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

/**
 * An abstract class that provides default values for properties commonly used in DTOs (Data Transfer Objects).
 *
 * <p>
 * This class provides default values for the following properties:
 * </p>
 * <ul>
 *   <li>createdBy: the user who created the entity represented by the DTO</li>
 *   <li>lastModifiedBy: the last user who modified the entity represented by the DTO</li>
 *   <li>createdAt: the date and time when the entity represented by the DTO was created</li>
 *   <li>lastModifiedAt: the date and time when the entity represented by the DTO was last modified</li>
 * </ul>
 *
 * <p>
 * It also includes a single constructor that initializes the createdAt and lastModifiedAt properties with the
 * current date and time.
 * </p>
 */
@Getter
@Setter
public abstract class AuditableDto {

	private String createdBy;
	private String lastModifiedBy;

	private Instant createdDate;
	private Instant lastModifiedDate;

	private String status;
	private String version;

	public AuditableDto ( ) {
		this.createdDate = Instant.now ( );
		this.lastModifiedDate = Instant.now ( );
		this.status = "NEW";
		this.version = "1.0.0";
	}

}
