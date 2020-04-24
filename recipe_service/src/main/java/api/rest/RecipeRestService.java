package api.rest;

import java.sql.Date;

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

import domain.model.Recipe;
import domain.service.RecipeService;


@ApplicationScoped
@Path("/recipe")
public class RecipeRestService {
	
	@Inject
	private RecipeService RecipeService;

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public void postRecipe(Recipe recipe) {
		RecipeService.create(recipe);

	}
	
	@Path("/{idrecipe}")
    @GET
	@Produces(MediaType.APPLICATION_JSON)
    public Recipe getRecipe(@PathParam("name") Long idRecipe) {
    	Recipe recipe = RecipeService.get(idRecipe);
        return recipe;
    }

}