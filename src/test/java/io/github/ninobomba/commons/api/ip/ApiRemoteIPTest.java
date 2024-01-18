package io.github.ninobomba.commons.api.ip;

import org.junit.jupiter.api.Test;

class ApiRemoteIPTest {
	
	@Test
	void getRemoteIpUsingApifyService ( ) {
		var ip = ApiRemoteIP.getRemoteIpUsingApifyService ();
		assert ip != null;
		System.out.println (  ip );
	}
	
	@Test
	void getRemoteIpUsingAwsService ( ) {
		var ip = ApiRemoteIP.getRemoteIpUsingAwsService ();
		assert ip != null;
		System.out.println (  ip );
	}
	
}
