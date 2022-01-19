package tech.tools4monkeys.commons.request;

import lombok.extern.slf4j.Slf4j;
import tech.tools4monkeys.commons.checkpoints.CheckPoint;
import tech.tools4monkeys.commons.checkpoints.CheckPointFactory;
import tech.tools4monkeys.commons.events.Event;
import tech.tools4monkeys.commons.id.IdGenerator;
import tech.tools4monkeys.commons.json.JsonUtils;
import lombok.Builder;
import lombok.Data;

import java.time.Duration;
import java.util.*;

@Slf4j
@Data
@Builder
public class Request
{

    @Builder.Default
    private final long id = IdGenerator.getInstance().getNextId();

    private String name;
    private String payload;

    @Builder.Default
    private final Queue<Event> eventQueue = new LinkedList<>();

    private Map<String, CheckPoint> checkPointMap;

    /**
     * Create an instance of checkpoint map
     * @param key, the key name based on the filename
     */
    public void createCheckPointMapWithKey( String key ) {
        checkPointMap = CheckPointFactory.getInstance().getCheckPointMap( key );
    }

    public Map<String, CheckPoint> getCheckPointMap() {
        return checkPointMap;
    }

    public void pushEvent( String name )
    {
        log.trace( "Request::pushEvent() _: Adding new event name: {}", name );
        if( eventQueue.isEmpty() ) eventQueue.add( new Event( "__INIT__" ) );
        eventQueue.add( new Event( name ) );
    }

    public void calculateElapsedTime()
    {
        log.trace( "Request: calculateElapsedTime() >: start" );

        if ( eventQueue.isEmpty() ) return;

        var events = eventQueue.toArray();
        var length     = events.length;

        for( int index = 1; index < length; index++ )
        {
            var to   = (Event) events[ index ];
            var from = (Event) events[ index - 1 ];

            Duration duration = Duration.between( from.getTimestamp(), to.getTimestamp() );
            to.setElapsedTimeNanoSeconds( duration.toNanos() );
            to.setElapsedTimeSeconds( duration.toSeconds() );

            log.trace( "Request: calculateElapsedTime() _: Event => id: {}, name: {}, nano: {}, seconds: {}",
                    to.getId(),
                    to.getName(),
                    to.getElapsedTimeNanoSeconds(),
                    to.getElapsedTimeSeconds()
            );
        }

        log.trace( "Request: calculateElapsedTime() <: complete" );
    }

    public void clear()
    {
        eventQueue.clear();
        checkPointMap.clear();
    }

    public String toJsonString( boolean pretty )
    {
        calculateElapsedTime();
        var events = new StringJoiner( "," );
        eventQueue.forEach( e -> events.add( e.toJsonString() ) );

        var checkpoints = new StringJoiner( "," );
        Optional.ofNullable( checkPointMap )
                .orElse( new HashMap<>() )
                .forEach( ( k, v ) -> checkpoints.add( v.toJsonString() ) );

        var response = "{"
                .concat( "\"id\":\"" + id + "\"," )
                .concat( "\"name\":\"" + name + "\"," )
                .concat( "\"payload\":\"" + payload + "\"," )
                .concat( "\"events\":[" + events + "]" + "," )
                .concat( "\"checkpoints\":[" + checkpoints + "]" )
                .concat( "}" );

        return pretty ? JsonUtils.pretty( response ) : response;
    }

}
