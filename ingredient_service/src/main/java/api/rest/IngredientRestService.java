package api.rest;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import javax.inject.Inject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import domain.model.Ingredient;
import domain.service.IngredientService;  


@ApplicationScoped
@Path("/ingredients")
public class IngredientRestService {


	@Inject
	private IngredientService ingredientService;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Ingredient> getIngredients() {

		return ingredientService.getAllIngredients();
	}
	
	@Path("/minInfos")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Object[]> getMinInfos() {		
		return ingredientService.getAllMinInfos();
	}
	
	@Path("/idName")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Object[]> getIdName() {
		return ingredientService.getIdName();	
	}


}