package io.github.ninobomba.commons.web.agent;

import org.junit.jupiter.api.Test;

class LoginDeviceTest {
	
	@Test
	void builder ( ) {
		LoginDevice loginDevice = LoginDevice.builder(  )
				.deviceVersion ( "1.0" )
				.id (  "2" )
				.email (  "fernando.farfan@xa.com" )
				.build();
		assert loginDevice != null;
	}
}
