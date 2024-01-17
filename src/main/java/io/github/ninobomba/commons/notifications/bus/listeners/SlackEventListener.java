package io.github.ninobomba.commons.notifications.bus.listeners;

import com.google.common.eventbus.Subscribe;
import io.github.ninobomba.commons.notifications.bus.events.SlackDeliveryMessageEvent;
import io.github.ninobomba.commons.notifications.channels.slack.SlackNotificationChannel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SlackEventListener {
	
	@Subscribe
	public void onEvent ( SlackDeliveryMessageEvent event ) {
		log.debug ( "SlackEventListener::onSlackMessageReceived() -> processing event: {}", event );
		SlackNotificationChannel.getInstance ( ).publish ( event.getMessage ( ) );
	}
	
}
