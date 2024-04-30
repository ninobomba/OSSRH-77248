package io.github.ninobomba.commons.web.agent;

import io.github.ninobomba.commons.id.IdGeneratorSnowFlakeSupport;
import io.github.ninobomba.commons.web.http.HttpRemoteIpUtils;
import net.sf.uadetector.ReadableUserAgent;
import net.sf.uadetector.service.UADetectorServiceFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * The UDDeviceDetector interface represents a device detector used to collect login device details from a HttpServletRequest.
 */
interface UDDeviceDetector {
	
	/**
	 * Returns the details of the login device based on the given HttpServletRequest.
	 *
	 * @param request the HttpServletRequest containing the login device information
	 * @return the LoginDevice object with the login device details
	 */
	default LoginDevice getLoginDeviceDetails ( HttpServletRequest request ) {
		String deviceId = UUID.randomUUID ( ).toString ( );
		String token = String.valueOf ( IdGeneratorSnowFlakeSupport.getInstance ( ).getNextId ( ) );
		String email = request.getParameter ( "username" );
		String timezone = request.getParameter ( "ua_timezone" );
		String remoteHost = HttpRemoteIpUtils.getRemoteIpByHttpRequestHeaders ( request );
		
		String userAgent = request.getHeader ( "User-Agent" );
		ReadableUserAgent agent = UADetectorServiceFactory.getResourceModuleParser ( ).parse ( userAgent );
		
		String name = agent.getName ( );
		String type = agent.getType ( ).getName ( );
		String version = agent.getVersionNumber ( ).getMajor ( );
		String category = agent.getDeviceCategory ( ).getName ( );
		String osProducer = agent.getOperatingSystem ( ).getProducer ( );
		String osName = agent.getOperatingSystem ( ).getName ( );
		
		return LoginDevice.builder ( )
				.id ( deviceId )
				.email ( email )
				.token ( token )
				.timezone ( timezone )
				.remoteHost ( remoteHost )
				.deviceVersion ( version )
				.userAgent ( userAgent )
				.name ( name ).type ( type ).category ( category ).osProducer ( osProducer ).osName ( osName )
				.build ( );
		
	}
	
}
