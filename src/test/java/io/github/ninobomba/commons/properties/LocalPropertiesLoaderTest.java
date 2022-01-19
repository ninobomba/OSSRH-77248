package io.github.ninobomba.commons.properties;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LocalPropertiesLoaderTest
{

    @Test
    void instanceTest()
    {
        var instance = LocalPropertiesLoader.getInstance();
        assertThat ( instance ).isNotNull();
    }

    @Test
    void getEmptyPropertyTest()
    {
        var property = LocalPropertiesLoader.getInstance().getProperty( "noProperty" );
        assertThat( property ).isNull();
    }

    @Test
    void getValidPropertyTest()
    {
        var property = LocalPropertiesLoader.getInstance().getProperty( "notifications.application.id" );
        System.out.println( property );
        assertThat( property ).isNotNull();
    }

}
