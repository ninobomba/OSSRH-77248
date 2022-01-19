package tech.tools4monkeys.commons.notifications.channels;

import tech.tools4monkeys.commons.exceptions.utils.ExceptionUtils;
import tech.tools4monkeys.commons.notifications.channels.media.TwilioWhatsAppNotificationChannel;
import tech.tools4monkeys.commons.notifications.commons.ENotificationLevel;
import tech.tools4monkeys.commons.notifications.commons.NotificationMessage;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TwilioWhatsAppNotificationChannelTest
{
    final NotificationMessage notification = new NotificationMessage();
    {
        Exception exception = new RuntimeException( "An whatsapp test exception" );
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
        TwilioWhatsAppNotificationChannel.getInstance().publish( notification );
    }

}
