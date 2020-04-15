package ch.unige.pickncook;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@ApplicationScoped
@Path("/ingredient")
public class Hello {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
    public String getHelloTXT() {
        return "Hello World";
    }

}