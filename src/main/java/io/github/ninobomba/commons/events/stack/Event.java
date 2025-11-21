package io.github.ninobomba.commons.events.stack;

import java.time.LocalDateTime;

public record Event(
		String id,
		String name,
		String type,
		String entityIdentifier,
		String entityName,
		LocalDateTime timestamp
) {
}
