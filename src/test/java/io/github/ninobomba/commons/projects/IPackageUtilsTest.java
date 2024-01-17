package io.github.ninobomba.commons.projects;

import io.github.ninobomba.commons.exceptions.commons.ExceptionsConstants;
import io.github.ninobomba.commons.project.IPackageUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class IPackageUtilsTest {
	
	
	@Test
	void testClassesUsingReflections() {
		var classes = IPackageUtils.findAllClassesUsingReflections( ExceptionsConstants.CUSTOM_PACKAGE_NAME.getValue ()  );
		assert classes != null;
		classes.forEach ( System.out::println );
	}
	
	@Test
	void testClassesUsingGoogleGuice() throws IOException {
		var classes = IPackageUtils.findAllClassesUsingGoogleGuice( ExceptionsConstants.CUSTOM_PACKAGE_NAME.getValue () + ".api"  );
		assert classes != null;
		classes.forEach ( System.out::println );
	}
	
	@Test
	void testClassesUsingClassLoader()  {
		var classes = IPackageUtils.findAllClassesUsingClassLoader ( ExceptionsConstants.CUSTOM_PACKAGE_NAME.getValue () +  ".commons"  );
		assert classes != null;
		classes.forEach ( System.out::println );
	}
	
}
