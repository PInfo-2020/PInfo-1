package api.rest;

import java.util.List;

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
import javax.ws.rs.FormParam;



import domain.model.Ingredient;
import domain.service.IngredientService;
import lombok.var;   


@ApplicationScoped
@Path("/ingredients")
public class IngredientRestService {


	@Inject
	private IngredientService ingredientService;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Ingredient> getIngredients() {
		List<Ingredient> infos = ingredientService.getAllIngredients();

		return infos;
	}
	
	@Path("/minInfos")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Object[]> getMinInfos() {
		List<Object[]> baseInfos = ingredientService.getAllMinInfos();
		
		return baseInfos;
	}


}