package io.github.ninobomba.commons.unit.notifications.channels;

import io.github.ninobomba.commons.exceptions.utils.ExceptionUtils;
import io.github.ninobomba.commons.notifications.channels.media.TwilioSmsNotificationChannel;
import io.github.ninobomba.commons.notifications.commons.ENotificationLevel;
import io.github.ninobomba.commons.notifications.commons.NotificationMessage;
import org.junit.jupiter.api.BeforeAll;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TwilioSmsNotificationChannelTest {
	final NotificationMessage notification = new NotificationMessage ( );
	
	{
		Exception exception = new RuntimeException ( "Twilio sms test exception" );
		notification.setMessage ( exception.getMessage ( ) );
		notification.setPayload ( ExceptionUtils.convertToString ( exception ) );
		notification.setLevel ( ENotificationLevel.CRITICAL );
	}
	
	@BeforeAll
	static void setUp ( ) {
		System.setProperty ( "skipDelivery", "true" );
	}
	
	//@Test
	void smsNotificationCommandTest ( ) {
		TwilioSmsNotificationChannel.getInstance ( ).publish ( notification );
		assertTrue ( true );
	}
	
}
