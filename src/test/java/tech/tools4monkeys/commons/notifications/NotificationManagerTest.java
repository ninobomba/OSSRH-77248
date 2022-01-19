package tech.tools4monkeys.commons.notifications;

import tech.tools4monkeys.commons.notifications.commons.NotificationMessage;
import org.junit.jupiter.api.Test;

public class NotificationManagerTest
{

    @Test
    void pushAndFlushTest()
    {
        for( int index =0; index < 10; index++ )
            NotificationManager.push(
                    NotificationMessage
                            .builder()
                            .message(String.valueOf( index ))
                            .payload( "{}" )
                            .build()
            );

        NotificationManager.flush();
    }

}
