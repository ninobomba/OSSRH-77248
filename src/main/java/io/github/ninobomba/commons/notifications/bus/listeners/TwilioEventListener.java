package io.github.ninobomba.commons.notifications.bus.listeners;

import com.google.common.eventbus.Subscribe;
import io.github.ninobomba.commons.notifications.bus.events.TwilioSmsDeliveryMessageEvent;
import io.github.ninobomba.commons.notifications.bus.events.TwilioWhatsAppDeliveryMessageEvent;
import io.github.ninobomba.commons.notifications.channels.media.TwilioSmsNotificationChannel;
import io.github.ninobomba.commons.notifications.channels.media.TwilioWhatsAppNotificationChannel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TwilioEventListener {

    @Subscribe
    public void onWhatsUpEvent(TwilioWhatsAppDeliveryMessageEvent event) {
        log.debug("TwilioEventListener::onWhatsUpEvent() -> processing event: {}", event);
        TwilioWhatsAppNotificationChannel.getInstance().publish( event.getMessage() );
    }

    @Subscribe
    public void onSmsEvent(TwilioSmsDeliveryMessageEvent event) {
        log.debug("TwilioEventListener::onSmsEvent() -> processing event: {}", event);
        TwilioSmsNotificationChannel.getInstance().publish( event.getMessage() );
    }

}
