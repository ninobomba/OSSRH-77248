package tech.tools4monkeys.commons.inspectors;

import lombok.Builder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@Slf4j
@Builder
public class PostgresqlConnectivityInspector implements IResourceInspector
{

    private final String uri;
    private final String user;
    private final String password;
    private final String ssl;

    @SneakyThrows
    @Override
    public boolean isAvailable()
    {
        Properties properties = new Properties();
        properties.setProperty("user", user );
        properties.setProperty("password", password );
        properties.setProperty("ssl", ssl );

        boolean isValid = false;
        try ( Connection connection = DriverManager.getConnection ( uri, properties ) ) {
            isValid = connection.isValid( 1000 );
        } catch ( SQLException e ) {
            log.error( "PostgresqlConnectivityInspector::isAvailable() !: Unable to connect to db", e );
        }

        log.info( "PostgresqlConnectivityInspector::isAvailable() _: is connection available: {}", isValid );

        return isValid;
    }

    @Override
    public String toString()
    {
        return  ""
                .concat( "Connection params: " )
                .concat( "user: [" + user + "], ")
                .concat( "uri: [" + uri + "]" )
                .concat( "sslEnabled: [" + ssl + "]" )
                ;
    }
}
