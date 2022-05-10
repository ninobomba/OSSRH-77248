package io.github.ninobomba.commons.notifications.channels;

import io.github.ninobomba.commons.notifications.commons.NotificationMessage;

public interface INotificationChannel
{
    void publish(NotificationMessage notificationMessage);
    void load();
}
