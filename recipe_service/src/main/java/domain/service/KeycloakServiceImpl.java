package domain.service;
import java.util.Date;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.HttpHeaders;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

@ApplicationScoped

public class KeycloakServiceImpl implements KeycloakService {

	@Override
	public String getToken(HttpHeaders headers) {
		try {
			String header = headers.getRequestHeader("Authorization").get(0);
			String token = header.replaceFirst("Bearer" + " ", "");
			return token;
		
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public String getIdUser(String token) {
		try {
			DecodedJWT jwt = JWT.decode(token);
			String userId = jwt.getSubject();
			return userId;
		} catch (JWTDecodeException e) {
			return null;
		}
	}

	@Override
	public boolean verifyAuthentification(HttpHeaders headers) {
		String token = getToken(headers);
		if(token != null && getIdUser(token) != null) {
			return true;
					}
		return false;
	}

}
