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
@Path("/ingredient")
public class IngredientRestService {


	@Inject
	private IngredientService ingredientService;
	
	@Path("/ingredients")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Ingredient> getIngredients() {
		List<Ingredient> infos = ingredientService.getAllIngredients();
		Ingredient i = new Ingredient();
		i.setCategorie("test");
		i.setPoid_moyen(10);
		i.setUnite("g");
		i.setNom("test");
		
		
		
		return infos;
	}
	
	@Path("/BaseInfos")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Object[]> getBaseInfos() {
		List<Object[]> baseInfos = ingredientService.getAllBaseInfo();
		Ingredient i = new Ingredient();
		i.setCategorie("test");
		i.setPoid_moyen(10);
		i.setUnite("g");
		i.setNom("test");
		
		
		
		return baseInfos;
	}

    @GET
	@Produces(MediaType.APPLICATION_JSON)
    public String getIngredientTXT() {
        return ingredientService.helloWorld();
    }

}