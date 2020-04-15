import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("/")
@Produces(MediaType.TEXT_PLAIN)
public class Hello {

    @GET
    @Path("helloworld")
    public String getHelloTXT() {
        return "Hello World";
    }

}