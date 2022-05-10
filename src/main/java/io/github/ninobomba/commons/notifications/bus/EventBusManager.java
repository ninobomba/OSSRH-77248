package io.github.ninobomba.commons.notifications.bus;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import io.github.ninobomba.commons.notifications.bus.events.EmailDeliveryMessageEvent;
import io.github.ninobomba.commons.notifications.bus.events.SlackDeliveryMessageEvent;
import io.github.ninobomba.commons.notifications.bus.events.TwilioSmsDeliveryMessageEvent;
import io.github.ninobomba.commons.notifications.bus.events.TwilioWhatsAppDeliveryMessageEvent;
import io.github.ninobomba.commons.notifications.bus.listeners.EmailEventListener;
import io.github.ninobomba.commons.notifications.bus.listeners.SlackEventListener;
import io.github.ninobomba.commons.notifications.bus.listeners.TwilioEventListener;
import io.github.ninobomba.commons.notifications.commons.NotificationMessage;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;

@Slf4j
public class EventBusManager {

    private static EventBusManager instance;
    private static EventBus eventBus;
    private static final List<?> listeners = List.of(
            new EmailEventListener(),
            new SlackEventListener(),
            new TwilioEventListener()
    );

    private EventBusManager() {
        eventBus = new AsyncEventBus( Executors.newCachedThreadPool() );
        listeners.forEach( listener -> eventBus.register( listener ) );
    }

    public static EventBusManager getInstance() {
        if( Objects.isNull( eventBus ) ) instance = new EventBusManager();
        return instance;
    }

    public void publish( NotificationMessage message ) {
        log.debug( "EventBusManager::publish() -> message: {}", message );

        var emailDeliveryMessageEvent = new EmailDeliveryMessageEvent();
        emailDeliveryMessageEvent.setMessage( message );
        eventBus.post( emailDeliveryMessageEvent );

        var slackDeliveryMessageEvent = new SlackDeliveryMessageEvent();
        slackDeliveryMessageEvent.setMessage( message );
        eventBus.post( slackDeliveryMessageEvent );

        var twilioSmsDeliveryMessageEvent = new TwilioSmsDeliveryMessageEvent();
        twilioSmsDeliveryMessageEvent.setMessage( message );
        eventBus.post( twilioSmsDeliveryMessageEvent );

        var twilioWhatsAppDeliveryMessageEvent = new TwilioWhatsAppDeliveryMessageEvent();
        twilioWhatsAppDeliveryMessageEvent.setMessage( message );
        eventBus.post( twilioWhatsAppDeliveryMessageEvent );
    }

}
