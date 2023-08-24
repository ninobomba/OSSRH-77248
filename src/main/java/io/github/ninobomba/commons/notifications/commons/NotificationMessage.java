package io.github.ninobomba.commons.notifications.commons;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.ninobomba.commons.id.IdGeneratorSnowFlakeSupport;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationMessage
{

    @Builder.Default
    private long id = IdGeneratorSnowFlakeSupport.getInstance().getNextId();

    @Builder.Default
    private ENotificationLevel level = ENotificationLevel.INFO;

    @Builder.Default
    private String timestamp = LocalDateTime.now().format( DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm:ss.SSS" ) );

    private long requestId;

    private String message;

    private String payload;

    @SneakyThrows
    public String toJsonString() {
        return new ObjectMapper().writeValueAsString( this );
    }

}
