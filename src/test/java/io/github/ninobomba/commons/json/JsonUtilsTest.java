package io.github.ninobomba.commons.json;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class JsonUtilsTest {

    @Test
    public  void isValidJsonTest() {
        var isValidJson = JsonUtils.isValidJson( "{}" );
        assert( isValidJson );
    }

    @Test
    public void prettyTest(){
        var prettyJson = JsonUtils.pretty( "{\"phone\":\"4158149716\",\"mobile\":\"4425626535\"}" );
        assertNotNull( prettyJson );
        System.out.println( prettyJson );
    }

}
