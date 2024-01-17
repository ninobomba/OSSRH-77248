package io.github.ninobomba.commons.notifications.channels.media;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import io.github.ninobomba.commons.exceptions.core.messages.LocalExceptionMessageBuilder;
import io.github.ninobomba.commons.exceptions.types.notification.NotificationProcessException;
import io.github.ninobomba.commons.notifications.channels.INotificationChannel;
import io.github.ninobomba.commons.notifications.commons.AppNotificationProperties;
import io.github.ninobomba.commons.notifications.commons.NotificationMessage;
import io.github.ninobomba.commons.properties.LocalPropertiesLoader;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Slf4j
public final class TwilioSmsNotificationChannel implements INotificationChannel {
	
	private static TwilioSmsNotificationChannel instance;
	
	private static boolean isServiceAvailable;
	private List < String > notificationLevelList;
	
	private String issueUrl;
	private PhoneNumber twilioPhoneNumber;
	
	private List < PhoneNumber > phoneList;
	
	@SneakyThrows
	private TwilioSmsNotificationChannel ( ) {
		boolean isEnabled = Boolean.parseBoolean ( LocalPropertiesLoader.getInstance ( ).getProperty ( "notifications.twilio.sms.service.enabled", "false" ) );
		if ( !isEnabled ) {
			log.warn ( "TwilioSmsNotificationChannel() _: twilio SMS channel is not enabled, check local properties - notifications.twilio.sms.service.enabled" );
			return;
		}
		
		phoneList = new ArrayList <> ( );
		
		load ( );
		
		setServiceAvailable ( true );
	}
	
	public static void setServiceAvailable ( boolean isAvailable ) {
		isServiceAvailable = isAvailable;
	}
	
	public static TwilioSmsNotificationChannel getInstance ( ) {
		if ( Objects.isNull ( instance ) ) instance = new TwilioSmsNotificationChannel ( );
		return instance;
	}
	
	@Override
	public void publish ( NotificationMessage notificationMessage ) {
		log.trace ( "TwilioSmsNotificationChannel::publish() >: start" );
		
		if ( !isServiceAvailable ) {
			log.warn ( "TwilioSmsNotificationChannel::publish() !: twilio channel is not available, returning" );
			return;
		}
		
		if ( !notificationLevelList.contains ( notificationMessage.getLevel ( ).toString ( ) ) ) {
			log.warn ( "TwilioSmsNotificationChannel::publish() !: twilio notification level is not accepted: Actual: {}, Configured: {}, Message: {}",
					notificationMessage.getLevel ( ),
					notificationLevelList,
					notificationMessage.toJsonString ( )
			);
			return;
		}
		
		log.trace ( "TwilioSmsNotificationChannel::publish() _: twilio sms notification info: {}", notificationMessage.toJsonString ( ) );
		
		CompletableFuture
				.runAsync ( ( ) -> sendMessage ( notificationMessage ) )
				.join ( );
		
		log.trace ( "TwilioSmsNotificationChannel::publish() <: complete" );
	}
	
	@SneakyThrows
	private void sendMessage ( NotificationMessage notificationMessage ) {
		log.trace ( "TwilioSmsNotificationChannel::sendMessage() >: start" );
		
		final String message = "\n"
				.concat ( AppNotificationProperties.getInstance ( ).getName ( ) + " / " )
				.concat ( AppNotificationProperties.getInstance ( ).getModule ( ) + " / " )
				.concat ( AppNotificationProperties.getInstance ( ).getVersion ( ) + " /n/n " )
				.concat ( "An " + notificationMessage.getLevel ( ) + " message " )
				.concat ( "on " + AppNotificationProperties.getInstance ( ).getEnv ( ) + " environment has occurred." )
				.concat ( "\n\n" )
				.concat ( "Notification Id: " + notificationMessage.getId ( ) )
				.concat ( "\n\n" )
				.concat ( "Error: " + notificationMessage.getMessage ( ) )
				.concat ( "\n\n" )
				.concat ( "Url: " + issueUrl.concat ( "&nid=" + notificationMessage.getId ( ) ) );
		
		phoneList.forEach ( to -> {
			
			log.debug ( "TwilioSmsNotificationChannel::sendMessage() _: sending sms message to: {} from: {}", to, twilioPhoneNumber );
			
			var twilioMessage = Message
					.creator ( to, twilioPhoneNumber, message )
					.create ( );
			
			log.debug ( "TwilioSmsNotificationChannel: sendMessage() _: response \nAccountSID: {}\nStatus: {} \nError: {}",
					twilioMessage.getAccountSid ( ),
					twilioMessage.getStatus ( ),
					"[".concat ( String.valueOf ( twilioMessage.getErrorCode ( ) ) ).concat ( "] - " ).concat ( String.valueOf ( twilioMessage.getErrorMessage ( ) ) )
			);
		} );
		
		log.trace ( "TwilioSmsNotificationChannel::sendMessage() <: complete" );
	}
	
	@Override
	public void load ( ) throws NotificationProcessException {
		log.trace ( "TwilioSmsNotificationChannel::load() >: start" );
		
		notificationLevelList = List.of ( LocalPropertiesLoader.getInstance ( ).getProperty ( "notifications.twilio.sms.trace", "CRITICAL,ERROR" )
				.replaceAll ( "\\s+", "" )
				.split ( "," ) );
		
		issueUrl = LocalPropertiesLoader.getInstance ( ).getProperty ( "notifications.twilio.sms.issue.url" );
		if ( StringUtils.isBlank ( issueUrl ) )
			throw LocalExceptionMessageBuilder.builder ( )
					.message ( "TwilioSmsNotificationChannel::load() !: twilio issue url is empty: " + issueUrl )
					.level ( LocalExceptionMessageBuilder.ExceptionLevel.ERROR )
					.build ( )
					.create ( NotificationProcessException.class );
		
		String token = LocalPropertiesLoader.getInstance ( ).getProperty ( "notifications.twilio.sms.token" );
		if ( StringUtils.isBlank ( token ) )
			throw LocalExceptionMessageBuilder.builder ( )
					.message ( "TwilioSmsNotificationChannel::load() !: twilio token is empty: " + token )
					.level ( LocalExceptionMessageBuilder.ExceptionLevel.ERROR )
					.build ( )
					.create ( NotificationProcessException.class );
		
		var sid = LocalPropertiesLoader.getInstance ( ).getProperty ( "notifications.twilio.sms.sid" );
		if ( StringUtils.isBlank ( sid ) )
			throw LocalExceptionMessageBuilder.builder ( )
					.message ( "TwilioSmsNotificationChannel::load() !: twilio sid is empty: " + sid )
					.level ( LocalExceptionMessageBuilder.ExceptionLevel.ERROR )
					.build ( )
					.create ( NotificationProcessException.class );
		
		var phones = LocalPropertiesLoader.getInstance ( ).getProperty ( "notifications.twilio.sms.to" );
		if ( StringUtils.isBlank ( phones ) )
			throw LocalExceptionMessageBuilder.builder ( )
					.message ( "TwilioSmsNotificationChannel::load() !: no twilio phones configured: " + phones )
					.level ( LocalExceptionMessageBuilder.ExceptionLevel.ERROR )
					.build ( )
					.create ( NotificationProcessException.class );
		
		var list = phones
				.replaceAll ( "\\s+", "" )
				.split ( "," );
		
		if ( list.length == 0 )
			throw LocalExceptionMessageBuilder.builder ( )
					.message ( "TwilioSmsNotificationChannel::load() !: invalid phone list: " + phones )
					.level ( LocalExceptionMessageBuilder.ExceptionLevel.ERROR )
					.build ( )
					.create ( NotificationProcessException.class );
		
		Arrays.stream ( list ).forEach ( e -> phoneList.add ( new PhoneNumber ( e ) ) );
		
		var twilioPhoneFrom = LocalPropertiesLoader.getInstance ( ).getProperty ( "notifications.twilio.sms.from" );
		
		if ( StringUtils.isBlank ( twilioPhoneFrom ) )
			throw LocalExceptionMessageBuilder.builder ( )
					.message ( "TwilioSmsNotificationChannel::load() !: no twilio phone configured to send sms messages: " + twilioPhoneFrom )
					.level ( LocalExceptionMessageBuilder.ExceptionLevel.ERROR )
					.build ( )
					.create ( NotificationProcessException.class );
		
		twilioPhoneNumber = new PhoneNumber ( twilioPhoneFrom );
		
		Twilio.init ( sid, token );
		
		log.trace ( "TwilioSmsNotificationChannel::load() <: complete" );
	}
	
}
