package io.github.ninobomba.commons.data.dtos;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

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
@EqualsAndHashCode
public abstract class AbstractDtoDefaultValues {
	
	private String        createdBy;
	private String        lastModifiedBy;
	
	private LocalDateTime createdAt;
	private LocalDateTime lastModifiedAt;
	
	/**
	 * An abstract class that provides default values for properties commonly used in DTOs (Data Transfer Objects).
	 *
	 * This class provides default values for the following properties:
	 * - createdBy: the user who created the entity represented by the DTO
	 * - lastModifiedBy: the last user who modified the entity represented by the DTO
	 * - createdAt: the date and time when the entity represented by the DTO was created
	 * - lastModifiedAt: the date and time when the entity represented by the DTO was last modified
	 *
	 * It also includes a single constructor that initializes the createdAt and lastModifiedAt properties with the
	 * current date and time.
	 */
	public AbstractDtoDefaultValues ( ) {
		final LocalDateTime currentDateTime = LocalDateTime.now ( );
		this.createdAt = currentDateTime;
		this.lastModifiedAt = currentDateTime;
	}
	
}
