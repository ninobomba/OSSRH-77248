package io.github.ninobomba.commons.unit.notifications.channels;

import io.github.ninobomba.commons.exceptions.utils.ExceptionUtils;
import io.github.ninobomba.commons.notifications.channels.slack.SlackNotificationChannel;
import io.github.ninobomba.commons.notifications.commons.ENotificationLevel;
import io.github.ninobomba.commons.notifications.commons.NotificationMessage;
import org.junit.jupiter.api.BeforeAll;

public class SlackNotificationChannelTest {
	final NotificationMessage notification = new NotificationMessage ( );
	
	{
		Exception exception = new RuntimeException ( "A slack test exception" );
		notification.setMessage ( exception.getMessage ( ) );
		notification.setPayload ( ExceptionUtils.convertToString ( exception ) );
		notification.setLevel ( ENotificationLevel.CRITICAL );
	}
	
	@BeforeAll
	static void setUp ( ) {
		System.setProperty ( "skipDelivery", "true" );
	}
	
	//@Test
	void slackNotificationCommandTest ( ) {
		SlackNotificationChannel.getInstance ( ).publish ( notification );
	}
	
}
