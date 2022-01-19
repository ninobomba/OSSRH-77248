package io.github.ninobomba.commons.notifications.channels.email;

import io.github.ninobomba.commons.exceptions.commons.EmptyOrNullParameterException;
import io.github.ninobomba.commons.notifications.channels.INotificationChannel;
import lombok.extern.slf4j.Slf4j;
import io.github.ninobomba.commons.notifications.commons.AppData;
import io.github.ninobomba.commons.notifications.commons.NotificationMessage;
import io.github.ninobomba.commons.properties.LocalPropertiesLoader;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;

@Slf4j
public final class EmailNotificationChannel implements INotificationChannel
{

    private static final EmailNotificationChannel instance = new EmailNotificationChannel();

    private static final boolean skipDelivery = ! StringUtils.isBlank( System.getProperty( "skipDelivery" ) );

    private boolean isServiceAvailable;
    private List<String> notificationLevel;

    private Session session;

    private String issueUrl;
    private String to;
    private String from;

    private EmailNotificationChannel()
    {
        boolean isServiceEnabled = Boolean.parseBoolean( LocalPropertiesLoader.getInstance().getProperty( "notifications.email.service.enabled", "false" ));
        if( isServiceEnabled ) load();
    }

    public static EmailNotificationChannel getInstance(){ return instance; }

    @Override
    public void publish(NotificationMessage notificationMessage)
    {
        log.trace("EmailNotificationChannel: publish() >: start");

        if( ! isServiceAvailable ) {
            log.warn("EmailNotificationChannel:  publish() !: email channel is not available, returning");
            return;
        }

        if( ! notificationLevel.contains( String.valueOf( notificationMessage.getLevel() ) ) ) {
            log.warn("EmailNotificationChannel: publish() _: email notification level is not accepted: {} - configured levels: {}",
                    notificationMessage.toJsonString(),
                    notificationLevel
            );
            return;
        }

        log.info("EmailNotificationChannel: publish() _: email notification info: {}",
                notificationMessage.toJsonString()
        );

        CompletableFuture
                .runAsync(() -> sendMessage( notificationMessage ))
                .join();

        log.trace("EmailNotificationChannel: publish() <: complete");
    }

    @SneakyThrows
    public void sendMessage(NotificationMessage notificationMessage)
    {
        String subject = buildEmailSubject( notificationMessage );
        String body    = buildEmailBody( notificationMessage );

        MimeMessage message = new MimeMessage( session );
        message.setFrom( new InternetAddress(from) );
        message.addRecipient( Message.RecipientType.TO, new InternetAddress(to) );
        message.setSubject( subject );
        message.setContent( body, "text/html" );

        if( skipDelivery ) return;

        Transport.send( message );
    }

    private static String buildEmailSubject(NotificationMessage notificationMessage)
    {
        return ""
                .concat( "App-Id: "    + AppData.getInstance().getId()      + " / " )
                .concat( "Name: "      + AppData.getInstance().getName()    + " / " )
                .concat( "Version: "   + AppData.getInstance().getVersion() + " / " )
                .concat( "Module: "    + AppData.getInstance().getModule()  + " / " )
                .concat( "<< " + notificationMessage.getMessage() + ">>"   );
    }

    private String buildEmailBody(NotificationMessage notificationMessage)
    {
        return ""
                .concat( "</br>" )
                .concat( "<h4>Application</h4>" )
                .concat( "<table width=\"100%\">" )
                .concat( "<tr><td>Id:</td><td>".concat( AppData.getInstance().getId() ) ).concat( "</td></tr>" )
                .concat( "<tr><td>Name:</td><td>".concat( AppData.getInstance().getName() ) ).concat( "</td></tr>" )
                .concat( "<tr><td>Module:</td><td>".concat( AppData.getInstance().getModule() ) ).concat( "</td></tr>" )
                .concat( "<tr><td>Version:</td><td>".concat( AppData.getInstance().getVersion() ) ).concat( "</td></tr>" )
                .concat( "<tr><td>Host:</td><td>".concat( AppData.getInstance().getHost() ) ).concat( "</td></tr>" )
                .concat( "<tr><td>Timestamp:</td><td>".concat( notificationMessage.getTimestamp() ) ).concat( "</td></tr>" )
                .concat( "</table>" )

                .concat( "</br>" )

                .concat( "<h4>Notification</h4>" )
                .concat( "<table width=\"100%\">" )
                .concat( "<tr VALIGN=\"TOP\"><td>Id:</td><td>".concat( String.valueOf( notificationMessage.getId() )) ).concat( "</td></tr>" )
                .concat( "<tr VALIGN=\"TOP\"><td>Request:</td><td>".concat( String.valueOf( notificationMessage.getRequestId() )) ).concat( "</td></tr>" )
                .concat( "<tr VALIGN=\"TOP\"><td>Message:</td><td>".concat( notificationMessage.getMessage() ) ).concat( "</td></tr>" )
                .concat( "<tr VALIGN=\"TOP\"><td>Url:</td><td>".concat(issueUrl.concat( "&nid="+ notificationMessage.getId())) ).concat( "</td></tr>" )
                .concat( "<tr VALIGN=\"TOP\"><td>Payload:</td><td>".concat( notificationMessage.getPayload() ) ).concat( "</td></tr>" )
                .concat( "</table>" );
    }


    @Override
    public void load()
    {
        log.trace("EmailNotificationChannel: load() >: start");

        notificationLevel = List.of( LocalPropertiesLoader.getInstance()
                .getProperty( "notifications.email.trace", "CRITICAL,ERROR" )
                .replaceAll( "\\s+", "" )
                .split(",") );

        issueUrl = LocalPropertiesLoader.getInstance().getProperty( "notifications.email.issue.url" );
        if( StringUtils.isBlank( issueUrl ) )
            throw EmptyOrNullParameterException.create( "EmailNotificationChannel::load() !: email issue url is empty", issueUrl );

        // Required not null values
        String host = LocalPropertiesLoader.getInstance().getProperty( "notifications.email.host" );
        if( StringUtils.isBlank( host ) )
            throw EmptyOrNullParameterException.create( "EmailNotificationChannel::load() !: email host server is empty", host );

        String username = LocalPropertiesLoader.getInstance().getProperty( "notifications.email.username" );
        if( StringUtils.isBlank( username ) )
            throw EmptyOrNullParameterException.create( "EmailNotificationChannel::load() !: email username is empty", username );

        String password = LocalPropertiesLoader.getInstance().getProperty( "notifications.email.password" );
        if( StringUtils.isBlank( password ) )
            throw EmptyOrNullParameterException.create( "EmailNotificationChannel: load() !: email password is empty", password );

        int port = Integer.parseInt( LocalPropertiesLoader.getInstance().getProperty( "notifications.email.port", "587" ) );

        from = username;
        to = LocalPropertiesLoader.getInstance().getProperty( "notifications.email.to" );

        var properties = new Properties();

        properties.put( "mail.smtp.host", host );
        properties.put( "mail.smtp.port", port );

        properties.put( "mail.transport.protocol", LocalPropertiesLoader.getInstance().getProperty("notifications.email.properties.mail.transport.protocol", "smtp" ));
        properties.put( "mail.smtp.auth", LocalPropertiesLoader.getInstance().getProperty("notifications.email.properties.mail.smtp.auth", "true") );
        properties.put( "mail.smtp.starttls.enable", LocalPropertiesLoader.getInstance().getProperty( "notifications.email.properties.mail.smtp.starttls.enable", "true") );
        properties.put( "mail.smtp.ssl.protocols", LocalPropertiesLoader.getInstance().getProperty("notifications.email.smtp.ssl.protocols", "TLSv1.2" ));

        session = Session.getDefaultInstance(properties, new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication( username, password );
                    }
                });

        session.setDebug( Boolean.parseBoolean( LocalPropertiesLoader.getInstance().getProperty("notifications.email.debug", "false") ));

        log.debug( "EmailNotificationChannel: load() _: \nhost: {} \nport: {} \nuser: {} \nto: {}",
                host,
                port,
                username,
                to
        );

        isServiceAvailable = true;

        log.trace( "EmailNotificationChannel: load() <: complete" );
    }

}
