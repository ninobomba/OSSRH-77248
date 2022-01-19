package io.github.ninobomba.commons.notifications.channels.media;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import io.github.ninobomba.commons.exceptions.commons.EmptyOrNullParameterException;
import lombok.extern.slf4j.Slf4j;
import io.github.ninobomba.commons.notifications.channels.INotificationChannel;
import io.github.ninobomba.commons.notifications.commons.AppData;
import io.github.ninobomba.commons.notifications.commons.NotificationMessage;
import io.github.ninobomba.commons.properties.LocalPropertiesLoader;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
public final class TwilioSmsNotificationChannel implements INotificationChannel
{

    private static final TwilioSmsNotificationChannel instance = new TwilioSmsNotificationChannel();

    private boolean isServiceAvailable;
    private List<String> notificationLevel;

    private String issueUrl;
    private PhoneNumber twilioPhoneNumber;

    private List<PhoneNumber> phoneList;

    private static final boolean skipDelivery = ! StringUtils.isBlank( System.getProperty( "skipDelivery" ) );

    private TwilioSmsNotificationChannel()
    {
        boolean isServiceEnabled = Boolean.parseBoolean( LocalPropertiesLoader.getInstance().getProperty( "notifications.twilio.sms.service.enabled", "false" ));
        if( ! isServiceEnabled )
            return;
        phoneList = new ArrayList<>();
        load();
    }

    public static TwilioSmsNotificationChannel getInstance(){ return instance; }

    @Override
    public void publish(NotificationMessage notificationMessage)
    {
        log.trace("TwilioSmsNotificationChannel: publish() >: start");

        if( ! isServiceAvailable ) {
            log.warn("TwilioSmsNotificationChannel: publish() !: twilio channel is not available, returning");
            return;
        }

        if( ! notificationLevel.contains( notificationMessage.getLevel().toString() ) ) {
            log.warn("TwilioSmsNotificationChannel: publish() _: twilio notification level is not accepted: {} - configured levels: {}",
                    notificationMessage.toJsonString(),
                    notificationLevel
            );
            return;
        }

        log.trace("TwilioSmsNotificationChannel: publish() _: twilio sms notification info: {}", notificationMessage.toJsonString() );

        CompletableFuture
                .runAsync(() -> sendMessage( notificationMessage ))
                .join();

        log.trace("TwilioSmsNotificationChannel: publish() <: complete");
    }

    @SneakyThrows
    public void sendMessage(NotificationMessage notificationMessage)
    {
        log.trace("TwilioSmsNotificationChannel: sendMessage() >: start");

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

        phoneList.forEach(to -> {

            log.debug("TwilioSmsNotificationChannel: sendMessage() _: sending sms message to: {} from: {}", to, twilioPhoneNumber);

            if( skipDelivery ) return;

            var twilioMessage = Message
                    .creator( to, twilioPhoneNumber, message )
                    .create();

            log.debug( "TwilioSmsNotificationChannel: sendMessage() _: response \nAccountSID: {}\nStatus: {} \nError: {}",
                    twilioMessage.getAccountSid(),
                    twilioMessage.getStatus(),
                    "[".concat( String.valueOf( twilioMessage.getErrorCode() ) ).concat( "] - " ).concat(  String.valueOf( twilioMessage.getErrorMessage() ) )
                    );
        });

        log.trace("TwilioSmsNotificationChannel: sendMessage() <: complete");
    }

    @Override
    public void load()
    {
        notificationLevel = List.of( LocalPropertiesLoader.getInstance().getProperty( "notifications.twilio.sms.trace", "CRITICAL,ERROR" )
                .replaceAll( "\\s+", "" )
                .split(",") );

        issueUrl = LocalPropertiesLoader.getInstance().getProperty( "notifications.twilio.sms.issue.url" );
        if( StringUtils.isBlank(issueUrl) )
            throw EmptyOrNullParameterException.create( "TwilioSmsNotificationChannel::load() !: twilio issue url is empty", issueUrl );

        String token = LocalPropertiesLoader.getInstance().getProperty( "notifications.twilio.sms.token" );
        if( StringUtils.isBlank( token ) )
            throw EmptyOrNullParameterException.create( "TwilioSmsNotificationChannel::load() !: twilio token is empty", token );

        String sid = LocalPropertiesLoader.getInstance().getProperty( "notifications.twilio.sms.sid" );
        if( StringUtils.isBlank( sid ) )
            throw EmptyOrNullParameterException.create( "TwilioSmsNotificationChannel::load() !: twilio sid is empty", sid );

        var phones = LocalPropertiesLoader.getInstance().getProperty( "notifications.twilio.sms.to" );
        if( StringUtils.isBlank( phones ) )
            throw EmptyOrNullParameterException.create( "TwilioSmsNotificationChannel::load() !: no twilio phones configured", phones );

        var list = phones
                .replaceAll("\\s+","")
                .split(",");

        if( list.length == 0 )
            throw EmptyOrNullParameterException.create( "TwilioSmsNotificationChannel::load() !: invalid phone list", phones );

        Arrays
                .stream( list )
                .sequential()
                .forEach( e -> phoneList.add( new PhoneNumber( e ) ));

        var twilioPhoneFrom = LocalPropertiesLoader.getInstance().getProperty( "notifications.twilio.sms.from" );

        if( StringUtils.isBlank( twilioPhoneFrom ) )
            throw EmptyOrNullParameterException.create( "TwilioSmsNotificationChannel::load() !: no twilio phone configured to send sms messages", twilioPhoneFrom );

        twilioPhoneNumber = new PhoneNumber( twilioPhoneFrom );

        Twilio.init(sid, token);

        isServiceAvailable = true;
    }

}
