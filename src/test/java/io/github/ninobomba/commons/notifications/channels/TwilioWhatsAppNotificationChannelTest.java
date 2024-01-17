package io.github.ninobomba.commons.notifications.channels;

import io.github.ninobomba.commons.exceptions.utils.ExceptionUtils;
import io.github.ninobomba.commons.notifications.channels.media.TwilioWhatsAppNotificationChannel;
import io.github.ninobomba.commons.notifications.commons.ENotificationLevel;
import io.github.ninobomba.commons.notifications.commons.NotificationMessage;
import org.junit.jupiter.api.BeforeAll;

class TwilioWhatsAppNotificationChannelTest {
	final NotificationMessage notification = new NotificationMessage ( );
	
	{
		Exception exception = new RuntimeException ( "An whatsapp test exception" );
		notification.setMessage ( exception.getMessage ( ) );
		notification.setPayload ( ExceptionUtils.convertToString ( exception ) );
		notification.setLevel ( ENotificationLevel.CRITICAL );
	}
	
	@BeforeAll
	static void setUp ( ) {
		System.setProperty ( "skipDelivery", "true" );
	}
	
	//@Test
	void whatsUpNotificationCommandTest ( ) {
		TwilioWhatsAppNotificationChannel.getInstance ( ).publish ( notification );
	}
	
}
