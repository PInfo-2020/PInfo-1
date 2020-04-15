package api.rest;

import javax.enterprise.context.ApplicationScoped;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


import domain.model.Ingredient;
import domain.service.IngredientService;

@ApplicationScoped
@Path("/ingredient")
public class IngredientRestService {


	@Inject
	private IngredientService ingredientService;

    @GET
	@Produces(MediaType.APPLICATION_JSON)
    public String getIngredientTXT() {
        return ingredientService.helloWorld();
    }

}