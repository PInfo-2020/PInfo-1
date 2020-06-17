package domain.service;

import javax.ws.rs.core.HttpHeaders;



public interface KeycloakService {
	public boolean verifyAuthentification(HttpHeaders headers);
	public String getToken(HttpHeaders headers);
	public String getIdUser(String token);
	public String getNameUser(String token);

}
