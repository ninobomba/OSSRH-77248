package io.github.ninobomba.commons.spring.events.ao;

import org.springframework.context.ApplicationEvent;

import java.time.Clock;


public class ApplicationStartupEvent extends ApplicationEvent {

	private final String message;

	public ApplicationStartupEvent ( Object source, Clock clock, String message ) {
		super ( source, clock );
		this.message = message;
	}

	public ApplicationStartupEvent ( Object source, String message ) {
		super ( source );
		this.message = message;
	}

}
