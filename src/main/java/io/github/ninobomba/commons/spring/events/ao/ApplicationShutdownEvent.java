package io.github.ninobomba.commons.spring.events.ao;

import org.springframework.context.ApplicationEvent;

import java.time.Clock;


public class ApplicationShutdownEvent extends ApplicationEvent {

	private final String message;

	public ApplicationShutdownEvent ( Object source, Clock clock, String message ) {
		super ( source, clock );
		this.message = message;
	}

	public ApplicationShutdownEvent ( Object source, String message ) {
		super ( source );
		this.message = message;
	}

}
