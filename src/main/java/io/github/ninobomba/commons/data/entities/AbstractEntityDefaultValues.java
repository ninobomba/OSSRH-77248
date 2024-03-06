package io.github.ninobomba.commons.data.entities;

import io.github.ninobomba.commons.data.records.ERecordStatus;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

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
	
	public AbstractEntityDefaultValues ( ) {
		final LocalDateTime currentDateTime = LocalDateTime.now ( );
		this.createdAt = currentDateTime;
		this.lastModifiedAt = currentDateTime;
		this.status = ERecordStatus.NEW.toString ();
		this.version = "1.0.0";
	}
	
}
