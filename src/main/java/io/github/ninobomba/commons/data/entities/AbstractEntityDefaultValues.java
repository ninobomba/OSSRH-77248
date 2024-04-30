package io.github.ninobomba.commons.data.entities;

import io.github.ninobomba.commons.data.records.ERecordStatus;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * This abstract class represents an entity with default values for common fields such as created at,
 * last modified at, created by, last modified by, status, and version.
 *
 * The class provides getters and setters for all the fields and overrides the equals and hashCode methods
 * to consider all the fields for object comparison.
 */
@Getter
@Setter
@EqualsAndHashCode
public abstract class AbstractEntityDefaultValues {
	
	private LocalDateTime createdAt;
	private LocalDateTime lastModifiedAt;
	
	private String createdBy;
	private String lastModifiedBy;
	
	private String status;
	private String version;
	
	/**
	 * Initializes an instance of the {@code AbstractEntityDefaultValues} class.
	 * Sets the default values for common fields such as created at, last modified at,
	 * status, and version. The created at and last modified at fields are initialized
	 * with the current date and time. The status field is set to "NEW" and the version
	 * field is set to "1.0.0".
	 *
	 * @see AbstractEntityDefaultValues#createdAt
	 * @see AbstractEntityDefaultValues#lastModifiedAt
	 * @see AbstractEntityDefaultValues#status
	 * @see AbstractEntityDefaultValues#version
	 * @see ERecordStatus#NEW
	 */
	public AbstractEntityDefaultValues ( ) {
		final LocalDateTime currentDateTime = LocalDateTime.now ( );
		this.createdAt = currentDateTime;
		this.lastModifiedAt = currentDateTime;
		this.status = ERecordStatus.NEW.toString ();
		this.version = "1.0.0";
	}
	
}
