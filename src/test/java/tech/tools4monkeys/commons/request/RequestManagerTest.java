package tech.tools4monkeys.commons.request;

import tech.tools4monkeys.commons.checkpoints.CheckPointFactory;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class RequestManagerTest
{

    static Request request = Request
            .builder()
            .name( "RequestManagerTest" )
            .payload( "{}" )
            .build();

    @BeforeAll
    public static void setUp()
    {
        var paths = new ArrayList<String>();
        paths.add( "src/main/resources/checkpoint/signup.json" );
        CheckPointFactory.getInstance().build( paths );
        request.createCheckPointMapWithKey( "signup" );
    }

    @SneakyThrows
    @Test
    public void addRequestTest()
    {
        // events
        System.out.println( "Working events" );
        int a = 0;
        while( a++ < 5 ) {
            request.pushEvent( String.valueOf( a ) );
        }

        //checkpoints
        System.out.println( "Working checkpoints" );
        var checkpointMap = request.getCheckPointMap();
        checkpointMap.forEach( (k,v) -> v.update() );

        RequestManager.getInstance().add( request );
        Thread.sleep( 1000 );

        RequestManager.checkOnQueue();
    }

}
