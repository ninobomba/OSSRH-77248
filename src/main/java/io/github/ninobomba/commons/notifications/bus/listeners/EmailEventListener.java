package io.github.ninobomba.commons.notifications.bus.listeners;

import com.google.common.eventbus.Subscribe;
import io.github.ninobomba.commons.notifications.bus.events.EmailDeliveryMessageEvent;
import io.github.ninobomba.commons.notifications.channels.email.EmailNotificationChannel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EmailEventListener {
	
	@Subscribe
	public void onEvent ( EmailDeliveryMessageEvent event ) {
		log.debug ( "EmailEventListener::onMessageReceived() -> processing event: {}", event );
		EmailNotificationChannel.getInstance ( ).publish ( event.getMessage ( ) );
	}
	
}
