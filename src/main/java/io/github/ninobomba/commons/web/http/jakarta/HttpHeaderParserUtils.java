package io.github.ninobomba.commons.web.http.jakarta;

public interface HttpHeaderParserUtils {

	static String getHeaderValue ( String headerName, jakarta.servlet.http.HttpServletRequest request ) {
		return request.getHeader ( headerName );
	}

	static String getHeaderValue ( String headerName, jakarta.servlet.http.HttpServletResponse response ) {
		return response.getHeader ( headerName );
	}

	static String getBearerToken ( jakarta.servlet.http.HttpServletRequest request ) {
		var authorization = getHeaderValue ( "Authorization", request );
		if ( authorization == null ) return null;
		if ( ! authorization.startsWith ( "Bearer " ) ) return null;
		String token = null;
		try {
			token = authorization.substring ( 7 );
		} catch ( Exception ignored ) {
		}
		return token == null || token.isBlank ( ) ? null : token.trim ( );
	}

}
