package io.github.ninobomba.commons.platform;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.springframework.util.CollectionUtils;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@UtilityClass
public final class SystemMetaDataProvider
{
    private static Map<String, String> properties;

    public static Map<String, String> getProperties()
    {
        if( CollectionUtils.isEmpty( properties ) ) {
            properties = new HashMap<>();
            properties.putAll( getSystemEnvironmentProperties() );
            properties.putAll( getNetworkProperties() );
        }
        return properties;
    }

    public static Map<String, String> getSystemEnvironmentProperties() {
        return System.getenv();
    }

    @SneakyThrows
    public static Map<String, String> getNetworkProperties()
    {
        Map<String, String> properties = new HashMap<>();
        properties.put( "inet-address", String.valueOf( InetAddress.getLocalHost() ) );
        properties.put( "host-address", String.valueOf( InetAddress.getLocalHost().getHostAddress() ) );
        return properties;
    }

}
