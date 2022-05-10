package io.github.ninobomba.commons.inspectors;

import lombok.Builder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

@Slf4j
@Builder
public class H2ConnectivityInspector implements IResourceInspector
{

    private static final String DRIVER = "org.h2.Driver";

    private String uri;
    private String user;
    private String password;

    @SneakyThrows
    @Override
    public boolean isAvailable()
    {
        boolean isValid = false;

        Class.forName ( DRIVER );

        try ( Connection connection = DriverManager.getConnection ( uri, user, password ) ) {
            isValid = Objects.nonNull( connection ) && connection.isValid( 1000 );
        } catch ( SQLException e ) {
            log.error( "H2ConnectivityInspector::isAvailable() !: Unable to connect to db", e );
        }

        log.info( "H2ConnectivityInspector::isAvailable() _: is connection available: {}", isValid );

        return isValid;
    }

    @Override
    public String toString()
    {
        return  ""
                .concat( "Connection params: " )
                .concat( "user: [" + user + "], ")
                .concat( "uri: [" + uri + "]" )
                ;
    }
}
