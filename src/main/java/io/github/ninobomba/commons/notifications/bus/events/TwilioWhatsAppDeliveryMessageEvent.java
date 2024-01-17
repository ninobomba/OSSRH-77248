package io.github.ninobomba.commons.notifications.bus.events;

import io.github.ninobomba.commons.notifications.commons.NotificationMessage;
import lombok.Data;

@Data
public class TwilioWhatsAppDeliveryMessageEvent {
	
	private NotificationMessage message;
	
}
