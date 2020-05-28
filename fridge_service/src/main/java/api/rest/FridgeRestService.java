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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.FormParam;



import domain.model.Fridge;
import domain.service.FridgeService;
import lombok.var;   


@ApplicationScoped
@Path("/fridge")
public class FridgeRestService {


	@Inject
	private FridgeService fridgeService;
	
	@Path("/{iduser}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Fridge getByUserId(@PathParam("iduser") String idUser) {

		return fridgeService.getByUserId(idUser);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void postFridge(Fridge fridge, @Context HttpHeaders headers) {
		//Keaycloack + récupérer l'id user à mettre dans fridge
		fridgeService.create(fridge);
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateFridge(Fridge fridge, @Context HttpHeaders headers) {

		//Keycloack+récupérer l'id user et mettre dans fridge
		fridge.setUserId("monId");
		fridgeService.updateFridge(fridge);
	}


}