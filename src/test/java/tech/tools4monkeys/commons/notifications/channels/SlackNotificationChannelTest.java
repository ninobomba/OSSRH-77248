package tech.tools4monkeys.commons.notifications.channels;

import tech.tools4monkeys.commons.exceptions.utils.ExceptionUtils;
import tech.tools4monkeys.commons.notifications.channels.slack.SlackNotificationChannel;
import tech.tools4monkeys.commons.notifications.commons.ENotificationLevel;

import tech.tools4monkeys.commons.notifications.commons.NotificationMessage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class SlackNotificationChannelTest
{
    final NotificationMessage notification = new NotificationMessage();
    {
        Exception exception = new RuntimeException( "A slack test exception" );
        notification.setMessage( exception.getMessage() );
        notification.setPayload( ExceptionUtils.convertToString( exception ) );
        notification.setLevel( ENotificationLevel.CRITICAL );
    }

    @BeforeAll
    static void setUp()
    {
        System.setProperty( "skipDelivery", "true" );
    }

    @Test
    void slackNotificationCommandTest()
    {
        SlackNotificationChannel.getInstance().publish( notification );
    }

}
