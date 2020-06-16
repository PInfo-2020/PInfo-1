package api.rest;


import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;

import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


import domain.model.Fridge;
import domain.service.FridgeService;
import domain.service.KeycloakService;
  


@ApplicationScoped
@Path("/fridge")
public class FridgeRestService {


	@Inject
	private FridgeService fridgeService;
	
	@Inject
	private KeycloakService keycloakService;
	
	String unauthorizedError = "There is no header or the token is not valid.";
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getByuserID(@Context HttpHeaders headers) {
		if (keycloakService.verifyAuthentification(headers)) {
			String token = keycloakService.getToken(headers);
			String userID = keycloakService.getIdUser(token);
			Fridge fridge = fridgeService.getByUserId(userID);
			if(fridge != null) {
				if(fridge.getUserId().equals(userID)) {
					return Response.status(Response.Status.OK).entity(fridge).build();
				}
				return Response.status(Response.Status.FORBIDDEN).entity("You don't have right to access this Fridge").build();
			}
			Fridge myFridge = new Fridge();
			myFridge.setUserId(userID);
			fridgeService.create(myFridge);
			return Response.status(Response.Status.OK).entity(myFridge).build();
		}
		return Response.status(Response.Status.UNAUTHORIZED).entity(unauthorizedError).build();
	}
	
	@Path("/ingredientIds")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getIngredientsIds(@Context HttpHeaders headers) {
		if (keycloakService.verifyAuthentification(headers)) {
			String token = keycloakService.getToken(headers);
			String userID = keycloakService.getIdUser(token);
			Fridge fridge = fridgeService.getByUserId(userID);
			if(fridge != null) {
				List<Long> listIds = fridgeService.getIngredientsId(fridge);
				return Response.status(Response.Status.OK).entity(listIds).build();
			}
			return Response.status(Response.Status.NOT_FOUND).entity("You don't have any fridge").build();
		}
		return Response.status(Response.Status.UNAUTHORIZED).entity(unauthorizedError).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postFridge(Fridge fridge, @Context HttpHeaders headers) {
		if (keycloakService.verifyAuthentification(headers)) {
			String token = keycloakService.getToken(headers);
			String userID = keycloakService.getIdUser(token);
			fridge.setUserId(userID);
			fridgeService.create(fridge);
			return Response.status(Response.Status.OK).entity("Fridge created.").build();
		}
		return Response.status(Response.Status.UNAUTHORIZED).entity(unauthorizedError).build();
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateFridge(Fridge fridge, @Context HttpHeaders headers) {
		if (keycloakService.verifyAuthentification(headers)) {
			String token = keycloakService.getToken(headers);
			String userID = keycloakService.getIdUser(token);
			fridge.setUserId(userID);
			if(fridgeService.updateFridge(fridge)) {
				Fridge userFridge = fridgeService.getByUserId(userID);
				if(userFridge.getUserId().equals(userID)) {
					return Response.status(Response.Status.OK).entity("Fridge updated.").build();
				}
				return Response.status(Response.Status.FORBIDDEN).entity("You don't have right to update this Fridge").build();
			}
			return Response.status(Response.Status.NOT_FOUND).entity("You don't have any fridge").build();
		}
	
		return Response.status(Response.Status.UNAUTHORIZED).entity(unauthorizedError).build();
		
	}


}