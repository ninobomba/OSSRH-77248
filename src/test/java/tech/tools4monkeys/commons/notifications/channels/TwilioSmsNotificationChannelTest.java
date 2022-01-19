package tech.tools4monkeys.commons.notifications.channels;

import tech.tools4monkeys.commons.exceptions.utils.ExceptionUtils;
import tech.tools4monkeys.commons.notifications.channels.media.TwilioSmsNotificationChannel;
import tech.tools4monkeys.commons.notifications.commons.ENotificationLevel;
import tech.tools4monkeys.commons.notifications.commons.NotificationMessage;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TwilioSmsNotificationChannelTest
{
    final NotificationMessage notification = new NotificationMessage();
    {
        Exception exception = new RuntimeException( "Twilio sms test exception" );
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
    void smsNotificationCommandTest() {
        TwilioSmsNotificationChannel.getInstance().publish( notification );
        assertTrue( true );
    }

}
