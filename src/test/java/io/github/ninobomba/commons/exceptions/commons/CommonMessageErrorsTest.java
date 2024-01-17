package io.github.ninobomba.commons.exceptions.commons;

import org.junit.jupiter.api.Test;

class CommonMessageErrorsTest {
	
	@Test
	void values ( ) {
		CommonMessageErrors[] values = CommonMessageErrors.values( );
		assert values.length > 0;
		for (CommonMessageErrors value : values)
			System.out.println(value);
		
		System.out.println (  );
		
		CommonMessageErrors.class.getEnumConstants( );
		for (CommonMessageErrors value : CommonMessageErrors.class.getEnumConstants( )) {
			assert value != null && ! value.name().isBlank () && ! value.description.isBlank ();
			System.out.println(value);
		}
	}
	
}
