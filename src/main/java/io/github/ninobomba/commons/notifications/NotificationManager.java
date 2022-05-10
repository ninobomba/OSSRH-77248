package io.github.ninobomba.commons.notifications;

import io.github.ninobomba.commons.notifications.bus.EventBusManager;
import io.github.ninobomba.commons.notifications.channels.INotificationChannel;
import io.github.ninobomba.commons.notifications.channels.email.EmailNotificationChannel;
import io.github.ninobomba.commons.notifications.channels.media.TwilioSmsNotificationChannel;
import io.github.ninobomba.commons.notifications.channels.media.TwilioWhatsAppNotificationChannel;
import io.github.ninobomba.commons.notifications.channels.slack.SlackNotificationChannel;
import io.github.ninobomba.commons.notifications.commons.NotificationMessage;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Slf4j
public final class NotificationManager
{
    private static final int MAX_ITEMS_TAKEN_FROM_QUEUE = 1000;

    private static final BlockingQueue<NotificationMessage> notificationQueue = new LinkedBlockingQueue<>();

    private static NotificationManager notificationManager;

    private static List<INotificationChannel> channels;

    private NotificationManager() {
        channels = List.of(
                EmailNotificationChannel.getInstance(),
                SlackNotificationChannel.getInstance(),
                TwilioSmsNotificationChannel.getInstance(),
                TwilioWhatsAppNotificationChannel.getInstance()
        );
    }

    public static NotificationManager getInstance() {
        if( Objects.isNull( notificationManager ) ) notificationManager = new NotificationManager();
        return notificationManager;
    }

    @SneakyThrows
    public static void push(NotificationMessage notification) {
        if( Objects.nonNull( notification ) ) notificationQueue.put( notification );
    }

    @SneakyThrows
    public static void flush()
    {
        log.trace( "NotificationManager::flush() >: start" );

        if( notificationQueue.isEmpty() ) {
            log.info( "NotificationManager::flush() _: queue is empty, returning" );
            return;
        }

        log.info( "NotificationManager::flush() _: processing {} notifications", notificationQueue.size() );

        int index = 0;
        boolean consume = true;

        while( consume ) {
            NotificationMessage notification = notificationQueue.take();
            log.info( "NotificationManager::flush() _: publish [{}] -> {}", index, notification.toJsonString() );
            EventBusManager.getInstance().publish( notification );
            //channels.forEach( e -> e.publish( notification ));
            consume = notificationQueue.isEmpty() || index++ <= MAX_ITEMS_TAKEN_FROM_QUEUE;
        }

        log.debug( "NotificationManager::flush() - notifications has been processed - queue size: {}", notificationQueue.size() );

        log.trace( "NotificationManager::flush() <: complete" );
    }

}
