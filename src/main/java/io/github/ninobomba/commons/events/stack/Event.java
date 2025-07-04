package io.github.ninobomba.commons.events.stack;

import java.time.Instant;

public record Event(
		String id,
		String name,
		String type,
		String entityIdentifier,
		String entityName,
		Instant timestamp
) {
}
