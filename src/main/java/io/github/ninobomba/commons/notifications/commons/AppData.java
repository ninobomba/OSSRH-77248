package io.github.ninobomba.commons.notifications.commons;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.ninobomba.commons.id.IdGenerator;
import io.github.ninobomba.commons.properties.LocalPropertiesLoader;
import lombok.Data;
import lombok.SneakyThrows;

import java.net.InetAddress;

@Data
public final class AppData
{

    private String id;
    private String name;
    private String module;
    private String version;
    private String host;
    private String env;

    private static final AppData instance = new AppData();

    private AppData(){
        load();
    }

    public static AppData getInstance(){ return instance; }

    @SneakyThrows
    private void load()
    {
        id = LocalPropertiesLoader.getInstance().getProperty( "notifications.application.id", String.valueOf( IdGenerator.getInstance().getNextId() ) );
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
