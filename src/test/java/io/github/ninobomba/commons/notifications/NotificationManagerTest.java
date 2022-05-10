package io.github.ninobomba.commons.notifications;

import io.github.ninobomba.commons.notifications.commons.ENotificationLevel;
import io.github.ninobomba.commons.notifications.commons.NotificationMessage;
import org.junit.jupiter.api.Test;

class NotificationManagerTest
{

    @Test
    void pushAndFlushTest()
    {
        for( int index =0; index < 2; index++ )
            NotificationManager.push(
                    NotificationMessage
                            .builder()
                            .message(String.valueOf( index ))
                            .payload( "{}" )
                            .level( ENotificationLevel.ERROR )
                            .build()
            );

        NotificationManager.flush();
    }

}
