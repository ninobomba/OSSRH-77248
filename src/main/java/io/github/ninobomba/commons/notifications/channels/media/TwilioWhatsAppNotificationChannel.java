package io.github.ninobomba.commons.notifications.channels.media;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import io.github.ninobomba.commons.exceptions.core.messages.LocalExceptionMessage;
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
public final class TwilioWhatsAppNotificationChannel implements INotificationChannel {
	
	private static TwilioWhatsAppNotificationChannel instance;
	
	private static boolean isServiceAvailable;
	private List < String > notificationLevelList;
	
	private String issueUrl;
	private PhoneNumber twilioPhoneNumber;
	private static final List < PhoneNumber > phoneList = new ArrayList <> ( );
	
	@SneakyThrows
	private TwilioWhatsAppNotificationChannel ( ) {
		boolean isEnabled = Boolean.parseBoolean ( LocalPropertiesLoader.getInstance ( ).getProperty ( "notifications.twilio.whatsapp.service.enabled", "false" ) );
		if ( !isEnabled ) {
			log.warn ( "TwilioWhatsAppNotificationChannel() _: twilio Whats-up channel is not enabled, check local properties - notifications.twilio.whatsapp.service.enabled" );
			return;
		}
		
		load ( );
		
		setServiceAvailable ( true );
	}
	
	public static TwilioWhatsAppNotificationChannel getInstance ( ) {
		if ( Objects.isNull ( instance ) ) instance = new TwilioWhatsAppNotificationChannel ( );
		return instance;
	}
	
	public static void setServiceAvailable ( boolean isAvailable ) {
		isServiceAvailable = isAvailable;
	}
	
	@Override
	public void publish ( NotificationMessage notificationMessage ) {
		log.trace ( "TwilioWhatsUpNotificationChannel::publish() >: start" );
		
		if ( !isServiceAvailable ) {
			log.warn ( "TwilioWhatsUpNotificationChannel::publish() !: twilio whats-app channel is not available, returning" );
			return;
		}
		
		if ( !notificationLevelList.contains ( notificationMessage.getLevel ( ).toString ( ) ) ) {
			log.warn ( "TwilioWhatsUpNotificationChannel::publish() !: twilio notification level is not accepted: Actual: {}, Configured: {}, Message: {}",
					notificationMessage.getLevel ( ),
					notificationLevelList,
					notificationMessage.toJsonString ( )
			);
			return;
		}
		
		log.trace ( "TwilioWhatsUpNotificationChannel::publish() _: whats-app notification info: {}", notificationMessage.toJsonString ( ) );
		
		CompletableFuture
				.runAsync ( ( ) -> sendMessage ( notificationMessage ) )
				.join ( );
		
		log.trace ( "TwilioWhatsUpNotificationChannel::publish() <: complete" );
	}
	
	@SneakyThrows
	private void sendMessage ( NotificationMessage notificationMessage ) {
		log.trace ( "TwilioWhatsUpNotificationChannel::sendMessage() >: start" );
		
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
			
			log.debug ( "TwilioWhatsUpNotificationChannel::sendMessage() _: sending whats-app message to: {} from: {}", to, twilioPhoneNumber );
			
			var twilioMessage = Message
					.creator ( to, twilioPhoneNumber, message )
					.create ( );
			
			log.debug ( "TwilioWhatsUpNotificationChannel::sendMessage() _: response \nAccountSID: {}\nStatus: {} \nError: {}",
					twilioMessage.getAccountSid ( ),
					twilioMessage.getStatus ( ),
					"[".concat ( String.valueOf ( twilioMessage.getErrorCode ( ) ) ).concat ( "] - " ).concat ( String.valueOf ( twilioMessage.getErrorMessage ( ) ) )
			);
		} );
		
		log.trace ( "TwilioWhatsUpNotificationChannel::sendMessage() <: complete" );
	}
	
	@Override
	public void load ( ) throws NotificationProcessException {
		log.trace ( "TwilioWhatsUpNotificationChannel::load() >: start" );
		
		notificationLevelList = List.of ( LocalPropertiesLoader.getInstance ( ).getProperty ( "notifications.twilio.whatsapp.trace", "CRITICAL,ERROR" )
				.replaceAll ( "\\s+", "" )
				.split ( "," ) );
		
		issueUrl = LocalPropertiesLoader.getInstance ( ).getProperty ( "notifications.twilio.whatsapp.issue.url" );
		if ( StringUtils.isBlank ( issueUrl ) )
			throw LocalExceptionMessage.builder ( )
					.message ( "TwilioWhatsAppNotificationChannel::load() !: twilio issue url is empty: " + issueUrl )
					.level ( LocalExceptionMessage.ExceptionLevel.ERROR )
					.build ( )
					.create ( NotificationProcessException.class );
		
		var token = LocalPropertiesLoader.getInstance ( ).getProperty ( "notifications.twilio.whatsapp.token" );
		if ( StringUtils.isBlank ( token ) )
			throw LocalExceptionMessage.builder ( )
					.message ( "TwilioWhatsAppNotificationChannel::load() !: twilio token is empty: " + token )
					.level ( LocalExceptionMessage.ExceptionLevel.ERROR )
					.build ( )
					.create ( NotificationProcessException.class );
		
		var sid = LocalPropertiesLoader.getInstance ( ).getProperty ( "notifications.twilio.whatsapp.sid" );
		if ( StringUtils.isBlank ( sid ) )
			throw LocalExceptionMessage.builder ( )
					.message ( "TwilioWhatsAppNotificationChannel::load() !: twilio sid is empty: " + sid )
					.level ( LocalExceptionMessage.ExceptionLevel.ERROR )
					.build ( )
					.create ( NotificationProcessException.class );
		
		var phones = LocalPropertiesLoader.getInstance ( ).getProperty ( "notifications.twilio.whatsapp.to" );
		if ( StringUtils.isBlank ( phones ) )
			throw LocalExceptionMessage.builder ( )
					.message ( "TwilioWhatsAppNotificationChannel::load() !: no twilio whatsapp phones configured: " + phones )
					.level ( LocalExceptionMessage.ExceptionLevel.ERROR )
					.build ( )
					.create ( NotificationProcessException.class );
		
		var list = phones.replaceAll ( "\\s+", "" ).split ( "," );
		
		if ( list.length == 0 )
			throw LocalExceptionMessage.builder ( )
					.message ( "TwilioWhatsAppNotificationChannel::load() !: invalid phone list: " + phones )
					.level ( LocalExceptionMessage.ExceptionLevel.ERROR )
					.build ( )
					.create ( NotificationProcessException.class );
		
		Arrays.stream ( list ).forEach ( e -> phoneList.add ( new PhoneNumber ( e ) ) );
		
		var twilioPhoneFrom = LocalPropertiesLoader.getInstance ( ).getProperty ( "notifications.twilio.whatsapp.from" );
		
		if ( StringUtils.isBlank ( twilioPhoneFrom ) )
			throw LocalExceptionMessage.builder ( )
					.message ( "TwilioWhatsAppNotificationChannel::load() !:  no twilio phone configured to send sms messages: " + twilioPhoneFrom )
					.level ( LocalExceptionMessage.ExceptionLevel.ERROR )
					.build ( )
					.create ( NotificationProcessException.class );
		
		twilioPhoneNumber = new PhoneNumber ( twilioPhoneFrom );
		
		Twilio.init ( sid, token );
		
		log.trace ( "TwilioWhatsUpNotificationChannel::load() <: complete" );
	}
	
}
