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


import domain.model.Ingredient;
import domain.service.IngredientService;
import io.undertow.server.handlers.form.FormData;
import lombok.var;   


@ApplicationScoped
@Path("/ingredient")
public class IngredientRestService {


	@Inject
	private IngredientService ingredientService;
	
	@Path("/BaseInfos")
	@GET
	@Produces(MediaType.MULTIPART_FORM_DATA)
	public FormData getBaseInfos() {
		
		
		List<Object[]> infos = ingredientService.getAllBaseInfo();
		
		var formData = new FormData();
		for (int i=0; i <= infos.size(); i++) {
			formData.;
		}
			
		
		return formData;
	}

    @GET
	@Produces(MediaType.APPLICATION_JSON)
    public String getIngredientTXT() {
        return ingredientService.helloWorld();
    }

}