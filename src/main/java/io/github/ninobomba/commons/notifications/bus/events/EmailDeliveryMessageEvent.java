package io.github.ninobomba.commons.notifications.bus.events;

import io.github.ninobomba.commons.notifications.commons.NotificationMessage;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailDeliveryMessageEvent {

    private NotificationMessage message;

}
