package io.github.ninobomba.commons.notifications.channels.email;

import io.github.ninobomba.commons.exceptions.core.messages.LocalExceptionMessageBuilder;
import io.github.ninobomba.commons.exceptions.types.notification.NotificationProcessException;
import io.github.ninobomba.commons.notifications.channels.INotificationChannel;
import io.github.ninobomba.commons.notifications.commons.AppNotificationProperties;
import io.github.ninobomba.commons.notifications.commons.NotificationMessage;
import io.github.ninobomba.commons.properties.LocalPropertiesLoader;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;

@Slf4j
public final class EmailNotificationChannel implements INotificationChannel {
	
	private static EmailNotificationChannel instance;
	
	private static boolean isServiceAvailable;
	
	private List < String > notificationLevelList;
	
	private Session session;
	
	private String issueUrl;
	private String to;
	private String from;
	
	@SneakyThrows
	private EmailNotificationChannel ( ) {
		boolean isEnabled = Boolean.parseBoolean ( LocalPropertiesLoader.getInstance ( ).getProperty ( "notifications.email.service.enabled", "false" ) );
		if ( !isEnabled ) {
			log.warn ( "EmailNotificationChannel() _: email channel is not enabled, check local properties - notifications.email.service.enabled" );
			return;
		}
		
		load ( );
		
		setServiceAvailable ( true );
	}
	
	public static void setServiceAvailable ( boolean isAvailable ) {
		isServiceAvailable = isAvailable;
	}
	
	public static EmailNotificationChannel getInstance ( ) {
		if ( Objects.isNull ( instance ) ) instance = new EmailNotificationChannel ( );
		return instance;
	}
	
	@Override
	public void publish ( NotificationMessage notificationMessage ) {
		log.trace ( "EmailNotificationChannel::publish() >: start" );
		
		if ( !isServiceAvailable ) {
			log.warn ( "EmailNotificationChannel::publish() !: email channel is not available, returning" );
			return;
		}
		
		if ( !notificationLevelList.contains ( String.valueOf ( notificationMessage.getLevel ( ) ) ) ) {
			log.warn ( "EmailNotificationChannel::publish() !: email notification level is not accepted. Actual: {} - Configured: {}, Message: {}",
					notificationMessage.getLevel ( ),
					notificationLevelList,
					notificationMessage.toJsonString ( )
			);
			return;
		}
		
		log.info ( "EmailNotificationChannel::publish() _: email notification info: {}",
				notificationMessage.toJsonString ( )
		);
		
		CompletableFuture
				.runAsync ( ( ) -> sendMessage ( notificationMessage ) )
				.join ( );
		
		log.trace ( "EmailNotificationChannel::publish() <: complete" );
	}
	
	@SneakyThrows
	private void sendMessage ( NotificationMessage notificationMessage ) {
		String subject = buildEmailSubject ( notificationMessage );
		String body = buildEmailBody ( notificationMessage );
		
		MimeMessage message = new MimeMessage ( session );
		message.setFrom ( new InternetAddress ( from ) );
		message.addRecipient ( Message.RecipientType.TO, new InternetAddress ( to ) );
		message.setSubject ( subject );
		message.setContent ( body, "text/html" );
		
		Transport.send ( message );
	}
	
	private static String buildEmailSubject ( NotificationMessage notificationMessage ) {
		return ""
				.concat ( "App-Id: " + AppNotificationProperties.getInstance ( ).getId ( ) + " / " )
				.concat ( "Name: " + AppNotificationProperties.getInstance ( ).getName ( ) + " / " )
				.concat ( "Version: " + AppNotificationProperties.getInstance ( ).getVersion ( ) + " / " )
				.concat ( "Module: " + AppNotificationProperties.getInstance ( ).getModule ( ) + " / " )
				.concat ( "<< " + notificationMessage.getMessage ( ) + ">>" );
	}
	
	private String buildEmailBody ( NotificationMessage notificationMessage ) {
		final String END_OF_ROW = "</td></tr>";
		return ""
				.concat ( "</br>" )
				.concat ( "<h4>Application</h4>" )
				.concat ( "<table width=\"100%\">" )
				.concat ( "<tr><td>Id:</td><td>".concat ( AppNotificationProperties.getInstance ( ).getId ( ) ) ).concat ( END_OF_ROW )
				.concat ( "<tr><td>Name:</td><td>".concat ( AppNotificationProperties.getInstance ( ).getName ( ) ) ).concat ( END_OF_ROW )
				.concat ( "<tr><td>Module:</td><td>".concat ( AppNotificationProperties.getInstance ( ).getModule ( ) ) ).concat ( END_OF_ROW )
				.concat ( "<tr><td>Version:</td><td>".concat ( AppNotificationProperties.getInstance ( ).getVersion ( ) ) ).concat ( END_OF_ROW )
				.concat ( "<tr><td>Host:</td><td>".concat ( AppNotificationProperties.getInstance ( ).getHost ( ) ) ).concat ( END_OF_ROW )
				.concat ( "<tr><td>Timestamp:</td><td>".concat ( notificationMessage.getTimestamp ( ) ) ).concat ( END_OF_ROW )
				.concat ( "</table>" )
				
				.concat ( "</br>" )
				
				.concat ( "<h4>Notification</h4>" )
				.concat ( "<table width=\"100%\">" )
				.concat ( "<tr VALIGN=\"TOP\"><td>Id:</td><td>".concat ( String.valueOf ( notificationMessage.getId ( ) ) ) ).concat ( END_OF_ROW )
				.concat ( "<tr VALIGN=\"TOP\"><td>Request:</td><td>".concat ( String.valueOf ( notificationMessage.getRequestId ( ) ) ) ).concat ( END_OF_ROW )
				.concat ( "<tr VALIGN=\"TOP\"><td>Message:</td><td>".concat ( notificationMessage.getMessage ( ) ) ).concat ( END_OF_ROW )
				.concat ( "<tr VALIGN=\"TOP\"><td>Url:</td><td>".concat ( issueUrl.concat ( "&nid=" + notificationMessage.getId ( ) ) ) ).concat ( END_OF_ROW )
				.concat ( "<tr VALIGN=\"TOP\"><td>Payload:</td><td>".concat ( notificationMessage.getPayload ( ) ) ).concat ( END_OF_ROW )
				.concat ( "</table>" );
	}
	
	
	@Override
	public void load ( ) throws NotificationProcessException {
		log.trace ( "EmailNotificationChannel::load() >: start" );
		
		notificationLevelList = List.of ( LocalPropertiesLoader.getInstance ( )
				.getProperty ( "notifications.email.trace", "CRITICAL,ERROR" )
				.replaceAll ( "\\s+", "" )
				.split ( "," ) );
		
		issueUrl = LocalPropertiesLoader.getInstance ( ).getProperty ( "notifications.email.issue.url" );
		if ( StringUtils.isBlank ( issueUrl ) )
			throw LocalExceptionMessageBuilder.builder ( )
					.message ( "EmailNotificationChannel::load() !: email issue url is empty: " + issueUrl )
					.level ( LocalExceptionMessageBuilder.ExceptionLevel.ERROR )
					.build ( )
					.create ( NotificationProcessException.class );
		
		// Required not null values
		var host = LocalPropertiesLoader.getInstance ( ).getProperty ( "notifications.email.host" );
		if ( StringUtils.isBlank ( host ) )
			throw LocalExceptionMessageBuilder.builder ( )
					.message ( "EmailNotificationChannel::load() !: email host server is empty: " + host )
					.level ( LocalExceptionMessageBuilder.ExceptionLevel.ERROR )
					.build ( )
					.create ( NotificationProcessException.class );
		
		String username = LocalPropertiesLoader.getInstance ( ).getProperty ( "notifications.email.username" );
		if ( StringUtils.isBlank ( username ) )
			throw LocalExceptionMessageBuilder.builder ( )
					.message ( "EmailNotificationChannel::load() !: email username is empty: " + username )
					.level ( LocalExceptionMessageBuilder.ExceptionLevel.ERROR )
					.build ( )
					.create ( NotificationProcessException.class );
		
		String password = LocalPropertiesLoader.getInstance ( ).getProperty ( "notifications.email.password" );
		if ( StringUtils.isBlank ( password ) )
			throw LocalExceptionMessageBuilder.builder ( )
					.message ( "EmailNotificationChannel::load() !: email password is empty: " + username )
					.level ( LocalExceptionMessageBuilder.ExceptionLevel.ERROR )
					.build ( )
					.create ( NotificationProcessException.class );
		
		int port = Integer.parseInt ( LocalPropertiesLoader.getInstance ( ).getProperty ( "notifications.email.port", "587" ) );
		
		from = username;
		to = LocalPropertiesLoader.getInstance ( ).getProperty ( "notifications.email.to" );
		
		var properties = new Properties ( );
		
		properties.put ( "mail.smtp.host", host );
		properties.put ( "mail.smtp.port", port );
		
		properties.put ( "mail.transport.protocol", LocalPropertiesLoader.getInstance ( ).getProperty ( "notifications.email.properties.mail.transport.protocol", "smtp" ) );
		properties.put ( "mail.smtp.auth", LocalPropertiesLoader.getInstance ( ).getProperty ( "notifications.email.properties.mail.smtp.auth", "true" ) );
		properties.put ( "mail.smtp.starttls.enable", LocalPropertiesLoader.getInstance ( ).getProperty ( "notifications.email.properties.mail.smtp.starttls.enable", "true" ) );
		properties.put ( "mail.smtp.ssl.protocols", LocalPropertiesLoader.getInstance ( ).getProperty ( "notifications.email.smtp.ssl.protocols", "TLSv1.2" ) );
		
		session = Session.getDefaultInstance ( properties, new Authenticator ( ) {
			@Override
			protected PasswordAuthentication getPasswordAuthentication ( ) {
				return new PasswordAuthentication ( username, password );
			}
		} );
		
		session.setDebug ( Boolean.parseBoolean ( LocalPropertiesLoader.getInstance ( ).getProperty ( "notifications.email.debug", "false" ) ) );
		
		log.debug ( "EmailNotificationChannel::load() _: \nhost: {} \nport: {} \nuser: {} \nto: {}",
				host,
				port,
				username,
				to
		);
		
		log.trace ( "EmailNotificationChannel::load() <: complete" );
	}
	
}
