package io.github.ninobomba.commons.api.ip;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ApiRemoteIpTest {
	@Test
	void getRemoteIpByAwsTest ( ) {
		var ip = ApiRemoteIP.getRemoteIpByAws ( );
		assertThat ( ip ).isNotBlank ( );
	}
}
