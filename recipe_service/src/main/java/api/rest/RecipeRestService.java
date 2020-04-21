package api.rest;

import javax.enterprise.context.ApplicationScoped;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@ApplicationScoped
@Path("/recipe")
public class RecipeRestService {

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public void postRecipe() {
		
	
	}

    @GET
	@Produces(MediaType.APPLICATION_JSON)
    public String getRecipeTXT() {
        return "recipe";
    }

}