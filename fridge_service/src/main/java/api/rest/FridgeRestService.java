package api.rest;

import java.sql.Date;
import java.util.ArrayList;
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
import javax.ws.rs.core.Response;
import javax.ws.rs.FormParam;

import domain.model.Fridge;
import domain.service.FridgeService;
import domain.service.KeycloakService;
import lombok.var;   


@ApplicationScoped
@Path("/fridge")
public class FridgeRestService {


	@Inject
	private FridgeService fridgeService;
	
	@Inject
	private KeycloakService KeycloakService;
	
	String UnauthorizedError = "There is no header or the token is not valid.";
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getByUserId(@Context HttpHeaders headers) {
		if (KeycloakService.verifyAuthentification(headers)) {
			String token = KeycloakService.getToken(headers);
			String UserID = KeycloakService.getIdUser(token);
			Fridge fridge = fridgeService.getByUserId(UserID);
			if(fridge != null) {
				if(fridge.getUserId().equals(UserID)) {
					return Response.status(Response.Status.OK).entity(fridge).build();
				}
				return Response.status(Response.Status.FORBIDDEN).entity("You don't have right to access this Fridge").build();
			}
			Fridge myFridge = new Fridge();
			myFridge.setUserId(UserID);
			fridgeService.create(myFridge);
			return Response.status(Response.Status.OK).entity(myFridge).build();
		}
		return Response.status(Response.Status.UNAUTHORIZED).entity(UnauthorizedError).build();
	}
	
	@Path("/ingredientIds")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getIngredientsIds(@Context HttpHeaders headers) {
		if (KeycloakService.verifyAuthentification(headers)) {
			String token = KeycloakService.getToken(headers);
			String UserID = KeycloakService.getIdUser(token);
			Fridge fridge = fridgeService.getByUserId(UserID);
			if(fridge != null) {
				List<Long> listIds = fridgeService.getIngredientsId(fridge);
				return Response.status(Response.Status.OK).entity(listIds).build();
			}
			return Response.status(Response.Status.NOT_FOUND).entity("You don't have any fridge").build();
		}
		return Response.status(Response.Status.UNAUTHORIZED).entity(UnauthorizedError).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postFridge(Fridge fridge, @Context HttpHeaders headers) {
		if (KeycloakService.verifyAuthentification(headers)) {
			String token = KeycloakService.getToken(headers);
			String UserID = KeycloakService.getIdUser(token);
			fridge.setUserId(UserID);
			fridgeService.create(fridge);
			return Response.status(Response.Status.OK).entity("Fridge created.").build();
		}
		return Response.status(Response.Status.UNAUTHORIZED).entity(UnauthorizedError).build();
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateFridge(Fridge fridge, @Context HttpHeaders headers) {
		if (KeycloakService.verifyAuthentification(headers)) {
			String token = KeycloakService.getToken(headers);
			String UserID = KeycloakService.getIdUser(token);
			fridge.setUserId(UserID);
			if(fridgeService.updateFridge(fridge)!=false) {
				Fridge userFridge = fridgeService.getByUserId(UserID);
				if(userFridge.getUserId().equals(UserID)) {
					return Response.status(Response.Status.OK).entity("Fridge updated.").build();
				}
				return Response.status(Response.Status.FORBIDDEN).entity("You don't have right to update this Fridge").build();
			}
			return Response.status(Response.Status.NOT_FOUND).entity("You don't have any fridge").build();
		}
	
		return Response.status(Response.Status.UNAUTHORIZED).entity(UnauthorizedError).build();
		
	}


}