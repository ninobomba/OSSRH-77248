package io.github.ninobomba.commons.notifications;

import io.github.ninobomba.commons.notifications.commons.ENotificationLevel;
import io.github.ninobomba.commons.notifications.commons.NotificationMessage;

class NotificationManagerTest {
	
	//@Test
	void pushAndFlushTest ( ) {
		for ( int index = 0 ; index < 2 ; index++ )
			NotificationManager.getInstance ( ).push (
					NotificationMessage
							.builder ( )
							.message ( String.valueOf ( index ) )
							.payload ( "{}" )
							.level ( ENotificationLevel.ERROR )
							.build ( )
			);
		
		NotificationManager.getInstance ( ).flush ( );
	}
	
}
