package io.github.ninobomba.commons.unit.web.agent;

import io.github.ninobomba.commons.web.agent.LoginDevice;
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
