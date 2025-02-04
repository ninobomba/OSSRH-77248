package io.github.ninobomba.commons.web.agent;

import net.sf.uadetector.service.UADetectorServiceFactory;


/**
 * The UADeviceParser interface represents a device detector used to collect login device details from a HttpServletRequest.
 */
public interface UADeviceParser {

	/**
	 * Returns the details of the login device based on the given HttpServletRequest.
	 *
	 * @param request the HttpServletRequest containing the login device information
	 * @return the UADevice object with the login device details
	 */
	static UADevice getDeviceDetailsUsingJavax ( javax.servlet.http.HttpServletRequest request ) {

		var id = request.getParameter ( "vxp_uid" );
		var token = request.getParameter ( "vxp_token" );
		var username = request.getParameter ( "vxp_username" );
		var timezone = request.getParameter ( "vxp_ua_timezone" );

		var remoteHost = io.github.ninobomba.commons.web.http.javax.HttpRemoteIpUtils.getRemoteIpByHttpRequestHeaders ( request );

		var userAgent = request.getHeader ( "User-Agent" );
		var agent = UADetectorServiceFactory.getResourceModuleParser ( ).parse ( userAgent );

		return new UADevice (
				id,
				token,
				username,
				timezone,
				remoteHost,
				userAgent,
				agent.getType ( ).getName ( ),
				agent.getName ( ),
				agent.getDeviceCategory ( ).getName ( ),
				agent.getOperatingSystem ( ).getProducer ( ),
				agent.getOperatingSystem ( ).getName ( ),
				agent.getOperatingSystem ( ).getVersionNumber ( ).toVersionString ( ),
				agent.getOperatingSystem ( ).getVersionNumber ( ).getExtension ( )
		);

	}

	/**
	 * Returns the details of the login device based on the given HttpServletRequest.
	 *
	 * @param request the HttpServletRequest containing the login device information
	 * @return the UADevice object with the login device details
	 */
	static UADevice getDeviceDetailsUsingJakarta ( jakarta.servlet.http.HttpServletRequest request ) {

		var id = request.getParameter ( "vxp_uid" );
		var token = request.getParameter ( "vxp_token" );
		var username = request.getParameter ( "vxp_username" );
		var timezone = request.getParameter ( "vxp_ua_timezone" );

		var remoteHost = io.github.ninobomba.commons.web.http.jakarta.HttpRemoteIpUtils.getRemoteIpByHttpRequestHeaders ( request );

		var userAgent = request.getHeader ( "User-Agent" );
		var agent = UADetectorServiceFactory.getResourceModuleParser ( ).parse ( userAgent );

		return new UADevice (
				id,
				token,
				username,
				timezone,
				remoteHost,
				userAgent,
				agent.getType ( ).getName ( ),
				agent.getName ( ),
				agent.getDeviceCategory ( ).getName ( ),
				agent.getOperatingSystem ( ).getProducer ( ),
				agent.getOperatingSystem ( ).getName ( ),
				agent.getOperatingSystem ( ).getVersionNumber ( ).toVersionString ( ),
				agent.getOperatingSystem ( ).getVersionNumber ( ).getExtension ( )
		);
	}

}
