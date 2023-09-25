package io.github.ninobomba.commons.notifications.commons;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.ninobomba.commons.id.IdGeneratorSnowFlakeSupport;
import io.github.ninobomba.commons.properties.LocalPropertiesLoader;
import lombok.Getter;
import lombok.SneakyThrows;

import java.net.InetAddress;

public final class AppData
{

    @Getter private String id;
    @Getter private String name;
    @Getter private String module;
    @Getter private String version;
    @Getter private String host;
    @Getter private String env;

    @Getter
    private static final AppData instance = new AppData();

    private AppData(){
        load();
    }

    @SneakyThrows
    private void load()
    {
        id = LocalPropertiesLoader.getInstance().getProperty( "notifications.application.id", String.valueOf( IdGeneratorSnowFlakeSupport.getInstance().getNextId() ) );
        name = LocalPropertiesLoader.getInstance().getProperty( "notifications.application.name" );
        module = LocalPropertiesLoader.getInstance().getProperty( "notifications.application.module" );
        version = LocalPropertiesLoader.getInstance().getProperty( "notifications.application.version" );
        env = LocalPropertiesLoader.getInstance().getProperty( "notifications.application.env" );

        host = InetAddress.getLocalHost().toString();
    }

    @SneakyThrows
    public String toJsonString()
    {
        return new ObjectMapper().writeValueAsString( this );
    }

}
