package io.github.ninobomba.commons.notifications.channels.media;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import io.github.ninobomba.commons.exceptions.commons.EmptyOrNullParameterException;
import io.github.ninobomba.commons.notifications.channels.INotificationChannel;
import lombok.extern.slf4j.Slf4j;
import io.github.ninobomba.commons.notifications.commons.AppData;
import io.github.ninobomba.commons.notifications.commons.NotificationMessage;
import io.github.ninobomba.commons.properties.LocalPropertiesLoader;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Slf4j
public final class TwilioWhatsAppNotificationChannel implements INotificationChannel
{

    private static TwilioWhatsAppNotificationChannel instance;

    private boolean isServiceAvailable;
    private List<String> notificationLevel;

    private String issueUrl;
    private PhoneNumber twilioPhoneNumber;
    private static final List<PhoneNumber> phoneList = new ArrayList<>();

    private final boolean skipDelivery = ! StringUtils.isBlank( System.getProperty( "skipDelivery" ) );

    private TwilioWhatsAppNotificationChannel()
    {
        boolean isServiceEnabled = Boolean.parseBoolean( LocalPropertiesLoader.getInstance().getProperty( "notifications.twilio.whatsapp.service.enabled", "false" ));
        if( isServiceEnabled ) load();
    }

    public static TwilioWhatsAppNotificationChannel getInstance(){
        if(Objects.isNull( instance )) instance = new TwilioWhatsAppNotificationChannel();
        return instance;
    }

    @Override
    public void publish(NotificationMessage notificationMessage)
    {
        log.trace("TwilioWhatsUpNotificationChannel: publish() >: start");

        if( ! isServiceAvailable ) {
            log.warn("TwilioWhatsUpNotificationChannel: publish() !: twilio whats-app channel is not available, returning");
            return;
        }

        if( ! notificationLevel.contains( notificationMessage.getLevel().toString() ) ) {
            log.warn("TwilioWhatsUpNotificationChannel: publish() _: twilio whats-app notification level is not accepted: {} - configured levels: {}",
                    notificationMessage.toJsonString(),
                    notificationLevel
            );
            return;
        }

        log.trace("TwilioWhatsUpNotificationChannel: publish() _: whats-app notification info: {}", notificationMessage.toJsonString() );

        CompletableFuture
                .runAsync(() -> sendMessage( notificationMessage ))
                .join();

        log.trace("TwilioWhatsUpNotificationChannel: publish() <: complete");
    }

    @SneakyThrows
    public void sendMessage(NotificationMessage notificationMessage)
    {
        log.trace( "TwilioWhatsUpNotificationChannel: sendMessage() >: start" );

        String message = "\n"
                .concat( AppData.getInstance().getName()    + " / " )
                .concat( AppData.getInstance().getModule()  + " / " )
                .concat( AppData.getInstance().getVersion() + " /n/n " )
                .concat( "An " + notificationMessage.getLevel()  + " message " )
                .concat( "on " + AppData.getInstance().getEnv()  + " environment has occurred." )
                .concat( "\n\n" )
                .concat( "Notification Id: " + notificationMessage.getId() )
                .concat( "\n\n" )
                .concat( "Error: " + notificationMessage.getMessage() )
                .concat( "\n\n" )
                .concat( "Url: " + issueUrl.concat( "&nid="+ notificationMessage.getId() ) );


        phoneList.forEach( to -> {

            log.debug("TwilioWhatsUpNotificationChannel: sendMessage() _: sending whats-app message to: {} from: {}", to, twilioPhoneNumber);

            if( skipDelivery ) return;

            var twilioMessage = Message
                    .creator( to, twilioPhoneNumber, message )
                    .create();

            log.debug( "TwilioWhatsUpNotificationChannel: sendMessage() _: response \nAccountSID: {}\nStatus: {} \nError: {}",
                    twilioMessage.getAccountSid(),
                    twilioMessage.getStatus(),
                    "[".concat( String.valueOf( twilioMessage.getErrorCode() ) ).concat( "] - " ).concat(  String.valueOf( twilioMessage.getErrorMessage() ) )
                    );
        });

        log.trace("TwilioWhatsUpNotificationChannel: sendMessage() <: complete");
    }

    @Override
    public void load()
    {
        notificationLevel = List.of( LocalPropertiesLoader.getInstance().getProperty( "notifications.twilio.whatsapp.trace", "CRITICAL,ERROR" )
                .replaceAll( "\\s+", "" )
                .split(",") );

        issueUrl = LocalPropertiesLoader.getInstance().getProperty( "notifications.twilio.whatsapp.issue.url" );
        if( StringUtils.isBlank( issueUrl ) )
            throw EmptyOrNullParameterException.create( "TwilioWhatsUpNotificationChannel::load() !: twilio whats-up issue url is empty", issueUrl );

        String token = LocalPropertiesLoader.getInstance().getProperty( "notifications.twilio.whatsapp.token" );
        if( StringUtils.isBlank( token ) )
            throw EmptyOrNullParameterException.create( "TwilioWhatsUpNotificationChannel::load() !: twilio whatsapp token is empty", token );

        String sid = LocalPropertiesLoader.getInstance().getProperty( "notifications.twilio.whatsapp.sid" );
        if( StringUtils.isBlank( sid ) )
            throw EmptyOrNullParameterException.create( "TwilioWhatsUpNotificationChannel: load() !: twilio whatsapp sid is empty", sid );

        var phones = LocalPropertiesLoader.getInstance().getProperty( "notifications.twilio.whatsapp.to" );
        if( StringUtils.isBlank( phones ) )
            throw EmptyOrNullParameterException.create( "TwilioWhatsUpNotificationChannel: load() !: no twilio whatsapp phones configured", String.valueOf( phones ) );

        var list = phones
                .replaceAll("\\s+","")
                .split(",");

        if( list.length == 0 )
            throw EmptyOrNullParameterException.create( "TwilioWhatsUpNotificationChannel: load() !: invalid phone list", String.valueOf( phones ) );

        Arrays
                .stream( list )
                .sequential()
                .forEach( e -> phoneList.add( new PhoneNumber( e ) ));

        var twilioPhoneFrom = LocalPropertiesLoader.getInstance().getProperty( "notifications.twilio.whatsapp.from" );

        if( StringUtils.isBlank( twilioPhoneFrom ) )
            throw EmptyOrNullParameterException.create( "TwilioWhatsUpNotificationChannel::load() !: no twilio phone configured to send sms messages", twilioPhoneFrom );

        twilioPhoneNumber = new PhoneNumber( twilioPhoneFrom );

        Twilio.init(sid, token);

        isServiceAvailable = true;
    }

}
