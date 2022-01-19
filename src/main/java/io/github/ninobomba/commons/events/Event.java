package io.github.ninobomba.commons.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.*;
import io.github.ninobomba.commons.id.IdGenerator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@EqualsAndHashCode
@ToString
//@Builder( builderClassName = "EventBuilder", buildMethodName = "build" )
public class Event
{
    private long id;
    private String name;

    private long elapsedTimeSeconds;
    private long elapsedTimeNanoSeconds;

    private LocalDateTime timestamp;
    private String formattedTimestamp;

    public Event( String name ) {
        assignDefaults();
        this.name = name;
    }

    private void assignDefaults() {
        id = IdGenerator.getInstance().getNextId();
        timestamp = LocalDateTime.now();
        formattedTimestamp = timestamp.format( DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm:ss.SSS" ) );
    }

    @SneakyThrows
    public String toJsonString() {
        var mapper = new ObjectMapper();
        mapper.registerModule( new JavaTimeModule() );
        mapper.disable( SerializationFeature.WRITE_DATES_AS_TIMESTAMPS );
        return mapper.writeValueAsString( this );
    }

}
