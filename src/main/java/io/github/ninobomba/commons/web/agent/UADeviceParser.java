package io.github.ninobomba.commons.web.agent;

import net.sf.uadetector.service.UADetectorServiceFactory;

import static io.github.ninobomba.commons.web.agent.HttpParameterSanitizer.sanitize;
import static org.springframework.http.HttpHeaders.USER_AGENT;


/**
 * The UADeviceParser interface represents a device detector used to collect login device details from a HttpServletRequest.
 */
public interface UADeviceParser {

    /**
     * Returns the details of the login device based on the given request object.
     * This method determines the type of the request object and processes it
     * accordingly to retrieve the device details.
     *
     * @param requestObject the request object, which can be either
     *                      javax.servlet.http.HttpServletRequest or
     *                      jakarta.servlet.http.HttpServletRequest
     * @return the UADevice object containing the details of the login device
     * @throws NullPointerException     if the request object is null
     * @throws IllegalArgumentException if the request object type is not supported
     */
    static UADevice getDeviceDetails ( Object requestObject ) {
        return switch ( requestObject ) {
            case null -> throw new NullPointerException( "Request object cannot be null" );
            case javax.servlet.http.HttpServletRequest request -> getDeviceDetailsUsingJavax( request );
            case jakarta.servlet.http.HttpServletRequest request -> getDeviceDetailsUsingJakarta( request );
            default ->
                    throw new IllegalArgumentException( "Unsupported request object type: " + requestObject.getClass( ).getName( ) );
        };
    }

    /**
     * Returns the details of the login device based on the given HttpServletRequest.
     *
     * @param request the HttpServletRequest containing the login device information
     * @return the UADevice object with the login device details
     */
    static UADevice getDeviceDetailsUsingJavax ( javax.servlet.http.HttpServletRequest request ) {

        var id = request.getParameter( "vxp_uid" );
        var token = request.getParameter( "vxp_token" );
        var username = request.getParameter( "vxp_username" );
        var timezone = request.getParameter( "vxp_ua_timezone" );

        var remoteHost = io.github.ninobomba.commons.web.http.javax.HttpRemoteIpUtils.getRemoteIpByHttpRequestHeaders( request );

        var userAgent = sanitize( request.getHeader( USER_AGENT ) );
        var agent = UADetectorServiceFactory.getResourceModuleParser( ).parse( userAgent );

        return new UADevice(
                id,
                token,
                username,
                timezone,
                remoteHost,
                userAgent,
                agent.getType( ).getName( ),
                agent.getName( ),
                agent.getDeviceCategory( ).getName( ),
                agent.getOperatingSystem( ).getProducer( ),
                agent.getOperatingSystem( ).getName( ),
                agent.getOperatingSystem( ).getVersionNumber( ).toVersionString( ),
                agent.getOperatingSystem( ).getVersionNumber( ).getExtension( )
        );

    }

    /**
     * Returns the details of the login device based on the given HttpServletRequest.
     *
     * @param request the HttpServletRequest containing the login device information
     * @return the UADevice object with the login device details
     */
    static UADevice getDeviceDetailsUsingJakarta ( jakarta.servlet.http.HttpServletRequest request ) {

        var id = request.getParameter( "vxp_uid" );
        var token = request.getParameter( "vxp_token" );
        var username = request.getParameter( "vxp_username" );
        var timezone = request.getParameter( "vxp_ua_timezone" );

        var remoteHost = io.github.ninobomba.commons.web.http.jakarta.HttpRemoteIpUtils.getRemoteIpByHttpRequestHeaders( request );

        var userAgent = sanitize( request.getHeader( "User-Agent" ) );
        var agent = UADetectorServiceFactory.getResourceModuleParser( ).parse( userAgent );

        return new UADevice(
                id,
                token,
                username,
                timezone,
                remoteHost,
                userAgent,
                agent.getType( ).getName( ),
                agent.getName( ),
                agent.getDeviceCategory( ).getName( ),
                agent.getOperatingSystem( ).getProducer( ),
                agent.getOperatingSystem( ).getName( ),
                agent.getOperatingSystem( ).getVersionNumber( ).toVersionString( ),
                agent.getOperatingSystem( ).getVersionNumber( ).getExtension( )
        );
    }

}
