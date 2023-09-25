package io.github.ninobomba.commons.notifications.channels.slack;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.model.Attachment;
import com.slack.api.model.block.DividerBlock;
import com.slack.api.model.block.LayoutBlock;
import com.slack.api.model.block.SectionBlock;
import com.slack.api.model.block.composition.MarkdownTextObject;
import io.github.ninobomba.commons.exceptions.commons.EmptyOrNullParameterException;
import io.github.ninobomba.commons.notifications.channels.INotificationChannel;
import io.github.ninobomba.commons.notifications.commons.AppData;
import io.github.ninobomba.commons.notifications.commons.NotificationMessage;
import io.github.ninobomba.commons.properties.LocalPropertiesLoader;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Slf4j
public final class SlackNotificationChannel implements INotificationChannel
{
    private static SlackNotificationChannel instance;

    private static boolean isServiceAvailable;
    private List<String> notificationLevelList;

    private String issueUrl;
    private String token;
    private String slackChannelId;

    private Slack slack;

    private SlackNotificationChannel()
    {
        boolean isEnabled = Boolean.parseBoolean( LocalPropertiesLoader.getInstance().getProperty( "notifications.slack.service.enabled", "false" ));
        if( ! isEnabled ) {
            log.warn("SlackNotificationChannel() _: Slack channel is not enabled, check local properties - notifications.slack.service.enabled");
            return;
        }

        load();

        setServiceAvailable( true );
    }

    public static void setServiceAvailable( boolean isAvailable ) {
        isServiceAvailable = isAvailable;
    }

    public static SlackNotificationChannel getInstance() {
        if( Objects.isNull( instance ) ) instance = new SlackNotificationChannel();
        return instance;
    }

    @Override
    public void publish(NotificationMessage notificationMessage)
    {
        log.trace( "SlackNotificationChannel::publish() >: start" );

        if( ! isServiceAvailable ) {
            log.warn("SlackNotificationChannel::publish() !: slack channel is not available, returning");
            return;
        }

        if( ! notificationLevelList.contains( notificationMessage.getLevel().toString() ) )
        {
            log.warn("SlackNotificationChannel::publish() !: Slack notification level is not accepted: Actual: {}, Configured: {}, Message: {}",
                    notificationMessage.getLevel(),
                    notificationLevelList,
                    notificationMessage.toJsonString()
            );
            return;
        }

        log.trace("SlackNotificationChannel::publish() _: slack notification info: {}", notificationMessage.toJsonString() );

        CompletableFuture
                .runAsync(() -> sendMessage( notificationMessage ))
                .join();

        log.trace("SlackNotificationChannel::publish() <: complete");
    }

    @SneakyThrows
    private void sendMessage(NotificationMessage notificationMessage)
    {
        log.trace("SlackNotificationChannel::sendMessage() >: start");

        String emoji;

        switch ( notificationMessage.getLevel() )
        {
            case CRITICAL : emoji = ":interrobang:"; break;
            case ERROR    : emoji = ":error:"; break;
            case WARNING  : emoji = ":warning:"; break;
            case DEBUG    : emoji = ":speech_balloon:"; break;
            default       : emoji = ":info_3:";
        }

        var request = ChatPostMessageRequest.builder()
                .channel(slackChannelId)
                .text(
                        ""
                        .concat( emoji )
                        .concat( " *" )
                        .concat( AppData.getInstance().getName()    + "/" )
                        .concat( AppData.getInstance().getModule()  + "/" )
                        .concat( AppData.getInstance().getVersion() + "* " )
                        .concat( "An *" + notificationMessage.getLevel()  + "* message " )
                        .concat( "on *" + AppData.getInstance().getEnv()  + "* environment has occurred" )
                )
                .attachments( createAttachments( notificationMessage ) )
                .build();

        var response = slack.methods(token).chatPostMessage( request );

        if( ! response.isOk() )
            log.error("SlackNotificationChannel::sendMessage() !: error while sending message to slack: \nerror: {}, \nmeta: {}",
                    response.getError(),
                    response.getResponseMetadata()
            );

        log.trace("SlackNotificationChannel::sendMessage() <: complete");
    }

    private List<Attachment> createAttachments(NotificationMessage notificationMessage)
    {
        var blocks = new ArrayList<LayoutBlock>();

        blocks.add( DividerBlock.builder().build() );
        blocks.add( SectionBlock.builder().text( MarkdownTextObject.builder().text( " << *Application* >>" ).build()).build());
        blocks.add( DividerBlock.builder().build() );

        blocks.add (
                SectionBlock.builder().text(
                        MarkdownTextObject.builder().text( "*Id*: " + AppData.getInstance().getId() ).build()
                ).build()
        );

        blocks.add (
                SectionBlock.builder().text(
                        MarkdownTextObject.builder().text( "*Name*: " + AppData.getInstance().getName() ).build()
                ).build()
        );

        blocks.add (
                SectionBlock.builder().text(
                        MarkdownTextObject.builder().text( "*Module*: " + AppData.getInstance().getModule() ).build()
                ).build()
        );

        blocks.add (
                SectionBlock.builder().text(
                        MarkdownTextObject.builder().text( "*Host*: " + AppData.getInstance().getHost() ).build()
                ).build()
        );

        blocks.add (
                SectionBlock.builder().text(
                        MarkdownTextObject.builder().text( "*Environment*: " + AppData.getInstance().getEnv() ).build()
                ).build()
        );

        blocks.add( DividerBlock.builder().build() );
        blocks.add( SectionBlock.builder().text( MarkdownTextObject.builder().text( "<< *Notification* >>" ).build()).build());
        blocks.add( DividerBlock.builder().build() );

        blocks.add (
                SectionBlock.builder().text(
                        MarkdownTextObject.builder().text( "*Id*: " + notificationMessage.getId() ).build()
                ).build()
        );
        blocks.add (
                SectionBlock.builder().text(
                        MarkdownTextObject.builder().text( "*Request Id*: " + notificationMessage.getRequestId() ).build()
                ).build()
        );
        blocks.add (
                SectionBlock.builder().text(
                        MarkdownTextObject.builder().text( "*Message*: " + notificationMessage.getMessage() ).build()
                ).build()
        );
        blocks.add (
                SectionBlock.builder().text(
                        MarkdownTextObject.builder().text( "*Url*: " + issueUrl.concat( "&nid="+ notificationMessage.getId() ) ).build()
                ).build()
        );

        var payload = StringUtils.normalizeSpace( StringUtils.left( notificationMessage.getPayload(), 2900 ) );

        blocks.add (
                SectionBlock.builder().text( MarkdownTextObject.builder().text( payload ).build()).build()
        );

        var list = new ArrayList<Attachment>();
        list.add( Attachment.builder().color( "#F2466F" ).blocks( blocks ).build() );

        return list;
    }

    @Override
    public void load()
    {
        log.trace("SlackNotificationChannel::load() >: start");

        notificationLevelList = List.of( LocalPropertiesLoader.getInstance().getProperty( "notifications.slack.trace", "CRITICAL,ERROR" )
                .replaceAll( "\\s+", "" )
                .split(","));

        issueUrl = LocalPropertiesLoader.getInstance().getProperty( "notifications.slack.issue.url" );
        if( StringUtils.isBlank( issueUrl ) )
            throw EmptyOrNullParameterException.create( "TwilioSmsNotificationChannel::load() !: slack issue url is empty", issueUrl );

        token = LocalPropertiesLoader.getInstance().getProperty( "notifications.slack.token" );
        if( StringUtils.isBlank( token ) )
            throw EmptyOrNullParameterException.create( "SlackNotificationChannel: load() !: slack token is empty", token );

        slackChannelId = LocalPropertiesLoader.getInstance().getProperty( "notifications.slack.channel" );
        if( StringUtils.isBlank( slackChannelId ) )
            throw EmptyOrNullParameterException.create( "SlackNotificationChannel: load() !: slack slack channel Id is empty", slackChannelId );

        var config = new SlackConfig();
        config.setPrettyResponseLoggingEnabled( true );
        slack = Slack.getInstance( config );

        log.trace("SlackNotificationChannel::load() <: complete");
    }

}
