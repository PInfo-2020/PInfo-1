package domain.service;

import javax.ws.rs.core.HttpHeaders;

import com.auth0.jwt.interfaces.Claim;


public interface KeycloakService {
	public boolean verifyAuthentification(HttpHeaders headers);
	public String getToken(HttpHeaders headers);
	public String getIdUser(String token);
	public String getNameUser(String token);

}
