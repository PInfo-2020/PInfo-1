package domain.service;



import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.HttpHeaders;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

@ApplicationScoped

public class KeycloakServiceImpl implements KeycloakService {

	@Override
	public String getToken(HttpHeaders headers) {
		try {
			String header = headers.getRequestHeader("Authorization").get(0);
			return header.replaceFirst("Bearer" + " ", "");
		
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public String getIdUser(String token) {
		try {
			DecodedJWT jwt = JWT.decode(token);
			return jwt.getSubject();
		} catch (JWTDecodeException e) {
			return null;
		}
	}
	
	@Override
	public String getNameUser(String token) {
		try {
			DecodedJWT jwt = JWT.decode(token);
			return jwt.getClaim("preferred_username").asString();
		} catch (JWTDecodeException e) {
			return null;
		}
	}

	@Override
	public boolean verifyAuthentification(HttpHeaders headers) {
		String token = getToken(headers);
		return (token != null && getIdUser(token) != null);
				
	}

}
