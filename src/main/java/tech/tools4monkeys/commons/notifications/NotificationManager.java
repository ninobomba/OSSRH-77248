package tech.tools4monkeys.commons.notifications;

import lombok.extern.slf4j.Slf4j;
import tech.tools4monkeys.commons.notifications.channels.INotificationChannel;
import tech.tools4monkeys.commons.notifications.channels.email.EmailNotificationChannel;
import tech.tools4monkeys.commons.notifications.channels.media.TwilioSmsNotificationChannel;
import tech.tools4monkeys.commons.notifications.commons.NotificationMessage;
import lombok.SneakyThrows;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Slf4j
public final class NotificationManager
{
    private static final int MAX_ITEMS_TAKEN_FROM_QUEUE = 1000;

    private static final BlockingQueue<NotificationMessage> notificationQueue = new LinkedBlockingQueue<>();

    private static NotificationManager instance;

    private static final List<INotificationChannel> channels = List.of(
            EmailNotificationChannel.getInstance(),
            TwilioSmsNotificationChannel.getInstance()
    );

    private NotificationManager(){}

    public static NotificationManager getInstance() {
        if( Objects.isNull( instance ) ) instance = new NotificationManager();
        return instance;
    }

    @SneakyThrows
    public static void push(NotificationMessage notification)
    {
        if( Objects.nonNull( notification ) ) notificationQueue.put( notification );
    }

    @SneakyThrows
    public static void flush()
    {
        log.trace( "NotificationManager: flush() >: start" );

        if( notificationQueue.isEmpty() )
        {
            log.info( "NotificationManager: flush() _: queue is empty, returning" );
            return;
        }

        log.info( "NotificationManager: flush() _: processing {} notifications", notificationQueue.size() );

        int index = 0;
        boolean consume = true;

        while( consume )
        {
            NotificationMessage notification = notificationQueue.take();
            log.info( "NotificationManager: flush() _: publish [{}] -> {}", index, notification.toJsonString() );
            channels.forEach( e -> e.publish( notification ) );
            consume = index++ >= MAX_ITEMS_TAKEN_FROM_QUEUE || notificationQueue.isEmpty();
        }

        log.trace( "NotificationManager - flush() <: complete" );
    }

}
