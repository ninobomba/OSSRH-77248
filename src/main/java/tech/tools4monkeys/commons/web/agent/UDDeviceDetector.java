package tech.tools4monkeys.commons.web.agent;

import tech.tools4monkeys.commons.web.http.HttpRemoteIpUtils;
import tech.tools4monkeys.commons.id.IdGenerator;
import net.sf.uadetector.ReadableUserAgent;
import net.sf.uadetector.service.UADetectorServiceFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

public class UDDeviceDetector
{

    public LoginDevice getLoginDeviceDetails(HttpServletRequest request)
    {
        String deviceId   = UUID.randomUUID().toString();
        String token      = String.valueOf( IdGenerator.getInstance().getNextId() );
        String email      = request.getParameter( "username" );
        String timezone   = request.getParameter( "ua_timezone" );
        String remoteHost = HttpRemoteIpUtils.getRemoteIpByHttpRequestHeaders( request );

        String userAgent = request.getHeader("User-Agent");
        ReadableUserAgent agent = UADetectorServiceFactory.getResourceModuleParser().parse( userAgent );

        String name       = agent.getName();
        String type       = agent.getType().getName();
        String version    = agent.getVersionNumber().getMajor();
        String category   = agent.getDeviceCategory().getName();
        String osProducer = agent.getOperatingSystem().getProducer();
        String osName     = agent.getOperatingSystem().getName();

        return LoginDevice.builder()
                .id(deviceId)
                .email(email)
                .token(token)
                .timezone(timezone)
                .remoteHost(remoteHost)
                .deviceVersion(version)
                .userAgent(userAgent)
                .name(name).type(type).category(category).osProducer(osProducer).osName(osName)
                .build();

    }
}
