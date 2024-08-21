package io.github.ninobomba.commons.spring.events.security;

import org.springframework.context.ApplicationEvent;

import java.time.Clock;

public class ApplicationSqlInjectionAttackEvent extends ApplicationEvent {

	private final String message;

	public ApplicationSqlInjectionAttackEvent ( Object source, String message ) {
		super ( source );
		this.message = message;
	}

	public ApplicationSqlInjectionAttackEvent ( Object source, Clock clock, String message ) {
		super ( source, clock );
		this.message = message;
	}
}
