import javax.ws.rs.*;
@Path("/")

public class Hello {

    @GET
    @Path("helloworld")
    public String getHelloTXT() {
        return "Hello World";
    }

}