package io.github.ninobomba.commons.notifications;

import io.github.ninobomba.commons.notifications.bus.EventBusManager;
import io.github.ninobomba.commons.notifications.commons.NotificationMessage;
import io.github.ninobomba.commons.properties.LocalPropertiesLoader;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Slf4j
public final class NotificationManager
{
    private static int maxItemsTakenFromQueue = 1_000;

    private static final BlockingQueue<NotificationMessage> notificationQueue = new LinkedBlockingQueue<>();

    private static NotificationManager instance;

//    private static final List<INotificationChannel> channels =  List.of(
//            EmailNotificationChannel.getInstance(),
//            SlackNotificationChannel.getInstance(),
//            TwilioSmsNotificationChannel.getInstance(),
//            TwilioWhatsAppNotificationChannel.getInstance()
//    );

    private NotificationManager() {
    }

    public static NotificationManager getInstance() {
        if( Objects.isNull( instance ) ) {
            maxItemsTakenFromQueue = Integer.parseInt( LocalPropertiesLoader.getInstance().getProperty( "notifications.application.queue.MAX_ITEMS_TAKEN_FROM_QUEUE", "100" ) );
            instance = new NotificationManager();
        }
        return instance;
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
        boolean stopConsuming = false;

        while( ! stopConsuming ) {
            NotificationMessage notification = notificationQueue.take();
            log.info( "NotificationManager::flush() _: publish [{}] -> {}", index, notification.toJsonString() );
            EventBusManager.getInstance().publish( notification );
            //channels.forEach( e -> e.publish( notification ));
            stopConsuming = notificationQueue.isEmpty() || index++ >= maxItemsTakenFromQueue;
        }

        log.debug( "NotificationManager::flush() - notifications has been processed - queue size: {}", notificationQueue.size() );

        log.trace( "NotificationManager::flush() <: complete" );
    }

}
