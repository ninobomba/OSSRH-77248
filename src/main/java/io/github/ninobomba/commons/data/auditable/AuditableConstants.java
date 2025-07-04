package io.github.ninobomba.commons.data.auditable;

import lombok.Getter;

import java.time.Instant;


@Getter
public final class AuditableConstants {

	private final String statusCode = "ENABLED";
	private final String statusCodeDisable = "DISABLED";

	private final String createdBy = "SYSTEM";
	private final String lastModifiedBy = "SYSTEM";

	private final Instant createdDate = Instant.now ( );
	private final Instant lastModifiedDate = Instant.now ( );

}
