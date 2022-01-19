package tech.tools4monkeys.commons.request;

import tech.tools4monkeys.commons.json.JsonUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class RequestTest
{

    @Test
    void pushEventTest()
    {

        Request request = Request.builder()
                .name(getClass().getName() )
                .payload( "{}" )
                .build();

        request.pushEvent( "start" );
        request.pushEvent( "processing" );
        request.pushEvent( "complete" );

        var json = request.toJsonString( true );
        System.out.println( json );

        assertTrue( JsonUtils.isValidJson( json ) );
    }

}
