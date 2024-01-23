package io.github.ninobomba.commons.platform;

import lombok.SneakyThrows;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

public interface SystemMetaDataProvider {
	
	static Map < String, String > getProperties ( ) {
		var properties = new HashMap < String, String > ( );
		properties.putAll ( getSystemEnvironmentProperties ( ) );
		properties.putAll ( getNetworkProperties ( ) );
		return properties;
	}
	
	static Map < String, String > getSystemEnvironmentProperties ( ) {
		return System.getenv ( );
	}
	
	@SneakyThrows
	static Map < String, String > getNetworkProperties ( ) {
		var properties = new HashMap < String, String > ( );
		properties.put ( "inet-address", String.valueOf ( InetAddress.getLocalHost ( ) ) );
		properties.put ( "host-address", String.valueOf ( InetAddress.getLocalHost ( ).getHostAddress ( ) ) );
		return properties;
	}
	
}
