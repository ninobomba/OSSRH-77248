package io.github.ninobomba.commons.platform;

import lombok.SneakyThrows;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * The SystemMetaDataProvider interface provides methods to retrieve system metadata
 * related to the environment properties and network properties.
 *
 * <p>Usage example:</p>
 * <pre>{@code
 * var properties = SystemMetaDataProvider.getProperties();
 * assertThat(properties).isNotNull();
 * properties.forEach((k, v) -> System.out.println(k + ": " + v));
 * }</pre>
 *
 * <p>The SystemMetaDataProvider interface has the following methods:</p>
 *
 * <ul>
 *   <li>{@code static Map<String, String> getProperties()}</li>
 *   <li>{@code static Map<String, String> getSystemEnvironmentProperties()}</li>
 *   <li>{@code static Map<String, String> getNetworkProperties()}</li>
 * </ul>
 *
 * <p>Note: The getSystemEnvironmentProperties() method retrieves the environment properties by
 * using the {@link System#getenv()} method.</p>
 *
 * <p>Note: The getNetworkProperties() method retrieves the network properties, such as the IP address
 * and host address, using the {@link InetAddress#getLocalHost()} method from the java.net package.</p>
 */
public interface SystemMetaDataProvider {
	
	static Map < String, String > getProperties ( ) {
		var properties = new HashMap < String, String > ( );
		properties.putAll ( getSystemEnvironmentProperties ( ) );
		properties.putAll ( getNetworkProperties ( ) );
		return properties;
	}
	
	/**
	 * Retrieves the system environment properties.
	 *
	 * @return A map containing the system environment properties.
	 *         The key represents the property name,
	 *         and the value represents the property value.
	 */
	static Map < String, String > getSystemEnvironmentProperties ( ) {
		return System.getenv ( );
	}
	
	/**
	 * Retrieves the network properties, such as the IP address and host address.
	 *
	 * @return A map containing the network properties.
	 *         The key represents the property name,
	 *         and the value represents the property value.
	 * @throws UnknownHostException If an error occurs while retrieving the network properties.
	 */
	@SneakyThrows
	static Map < String, String > getNetworkProperties ( ) {
		var properties = new HashMap < String, String > ( );
		properties.put ( "inet-address", String.valueOf ( InetAddress.getLocalHost ( ) ) );
		properties.put ( "host-address", String.valueOf ( InetAddress.getLocalHost ( ).getHostAddress ( ) ) );
		return properties;
	}
	
}
