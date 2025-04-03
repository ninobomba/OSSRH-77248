package io.github.ninobomba.commons.events.stack;

public interface IEvent {

	String getEventId ( );

	String getEventType ( );

	String getEntityId ( );

	String getEntityName ( );

}
