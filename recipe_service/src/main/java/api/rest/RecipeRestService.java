package api.rest;

import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import javax.enterprise.context.ApplicationScoped;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.HttpHeaders;

import domain.model.Comment;
import domain.model.Recipe;
import domain.service.RecipeService;
import domain.service.KeycloakService;


@ApplicationScoped
@Path("/recipe")
public class RecipeRestService {
	
	@Inject
	private RecipeService RecipeService;
	
	@Inject
	private KeycloakService KeycloakService;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void postRecipe( @Context HttpHeaders headers) {
		Recipe recipe = new Recipe();
		if (KeycloakService.verifyAuthentification(headers,  new Date())) {
			String token = KeycloakService.getToken(headers);
			String UserID = KeycloakService.getIdUser(token);
			String idAuthor = recipe.getAuthor();
			if (UserID == idAuthor) {
				RecipeService.create(recipe);
			}
		}
	}
	
	@Path("/{idrecipe}")
    @GET
	@Produces(MediaType.APPLICATION_JSON)
    public Recipe getRecipe(@PathParam("idrecipe") Long idRecipe) {
    	Recipe recipe = RecipeService.get(idRecipe);
        return recipe;
    }
	
	@Path("/{idrecipe}/comment")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void postComment(@PathParam("idrecipe") Long idRecipe, Comment comment, @Context HttpHeaders headers) {
		
		if (KeycloakService.verifyAuthentification(headers,  new Date())) {
			String token = KeycloakService.getToken(headers);
			String UserID = KeycloakService.getIdUser(token);
			String idAuthor = comment.getUserID();
			if (UserID == idAuthor) {
				RecipeService.addComment(idRecipe,comment);
			}
		}
		
	}
	
	@Path("/{idrecipe}/comment/{idcomment}")
	@DELETE
	public void deleteComment(@PathParam("idrecipe") Long idRecipe,@PathParam("idcomment") Long idComment, @Context HttpHeaders headers) {
		
		if (KeycloakService.verifyAuthentification(headers,  new Date())) {
			String token = KeycloakService.getToken(headers);
			String UserID = KeycloakService.getIdUser(token);
			Comment comment = RecipeService.getComment(idRecipe, idComment);
			String idAuthor = comment.getUserID();
			if (UserID == idAuthor) {
				RecipeService.addComment(idRecipe,comment);
			}
		}
		RecipeService.deleteComment(idRecipe,idComment);
	}
	
	
	@Path("/user")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getUserID(@Context HttpHeaders headers) {
		
		String UserID = "Something has gone wrong. Wrong token.";
		
		if (KeycloakService.verifyAuthentification(headers,  new Date())) {
			String token = KeycloakService.getToken(headers);
			UserID = KeycloakService.getIdUser(token);
		}
		
		return UserID;
	}

	
	@Path("/test")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Recipe getTest() {
		
		
		return RecipeService.getRandomRecipe();
	}
	
	
}