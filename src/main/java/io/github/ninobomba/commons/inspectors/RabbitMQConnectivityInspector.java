package io.github.ninobomba.commons.inspectors;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.Builder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Objects;

@Slf4j
@Builder
public class RabbitMQConnectivityInspector implements IResourceInspector
{

    private final String user;
    private final String password;
    private final String host;
    private final Integer port;

    @SneakyThrows
    @Override
    public boolean isAvailable()
    {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername( user );
        factory.setPassword( password );
        factory.setHost( host );
        factory.setPort( port );

        boolean isValid = false;
        try ( Connection connection = factory.newConnection() ) {
            isValid = Objects.nonNull(connection) && connection.isOpen();
        } catch ( IOException e ) {
            log.error( "RabbitMQConnectivityInspector::isAvailable() !: Unable to connect", e );
        }

        log.info( "RabbitMQConnectivityInspector::isAvailable() _: is connection available: {}", isValid );

        return isValid;
    }
}
