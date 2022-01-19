package tech.tools4monkeys.commons.notifications.channels;

import tech.tools4monkeys.commons.notifications.commons.NotificationMessage;

public interface INotificationChannel
{
    void publish(NotificationMessage notificationMessage);

    void load();
}
