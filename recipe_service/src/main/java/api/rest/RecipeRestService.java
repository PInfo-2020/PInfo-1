package api.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.sql.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

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
import javax.ws.rs.core.Response;

import org.postgresql.shaded.com.ongres.scram.common.message.ServerFinalMessage.Error;

import javax.ws.rs.core.HttpHeaders;

import domain.model.Comment;
import domain.model.Ingredient;
import domain.model.Recipe;
import domain.service.RecipeService;
//import io.restassured.http.ContentType;
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
	@Produces(MediaType.APPLICATION_JSON)
	public Response postRecipe(Recipe recipe, @Context HttpHeaders headers) {
		if (KeycloakService.verifyAuthentification(headers)) {
			String token = KeycloakService.getToken(headers);
			String UserID = KeycloakService.getIdUser(token);
			recipe.setAuthor(UserID);
			Date date = new Date(System.currentTimeMillis());
			recipe.setPublicationDate(date);
			recipe.setGrade(-1);
			List<Comment> comments = new ArrayList<Comment>();
			recipe.setComments(comments);
			long id = RecipeService.create(recipe);
			return Response.status(Response.Status.OK).entity(id).build();
		
		}
		return Response.status(Response.Status.UNAUTHORIZED).entity("There is no header or the token is not valid.").build();
	}
	
	@Path("/{idrecipe}")
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteRecipe(@PathParam("idrecipe") Long idRecipe, @Context HttpHeaders headers) {
		if (KeycloakService.verifyAuthentification(headers)) {
			String token = KeycloakService.getToken(headers);
			String UserID = KeycloakService.getIdUser(token);
			Recipe recipe = RecipeService.get(idRecipe);
			if (recipe.getAuthor().equals(UserID)) {
				RecipeService.delete(idRecipe);
				return Response.status(Response.Status.OK).build();
			}
			return Response.status(Response.Status.FORBIDDEN).entity("You don't have the rights to call this request.").build();
			
		}
		return Response.status(Response.Status.UNAUTHORIZED).entity("There is no header or the token is not valid.").build();
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
	public Response postComment(@PathParam("idrecipe") Long idRecipe,Comment comment, @Context HttpHeaders headers) {
		
		if (KeycloakService.verifyAuthentification(headers)) {
			String token = KeycloakService.getToken(headers);
			String UserID = KeycloakService.getIdUser(token);
			comment.setUserID(UserID);
			long idComment = RecipeService.addComment(idRecipe,comment);
			
			return Response.status(Response.Status.OK).entity(idComment).build();
		}
		return Response.status(Response.Status.UNAUTHORIZED).entity("There is no header or the token is not valid.").build();
	}
	
	@Path("/{idrecipe}/comment/{idcomment}")
	@DELETE
	public Response deleteComment(@PathParam("idrecipe") Long idRecipe,@PathParam("idcomment") Long idComment, @Context HttpHeaders headers) {
		
		if (KeycloakService.verifyAuthentification(headers)) {
			String token = KeycloakService.getToken(headers);
			String UserID = KeycloakService.getIdUser(token);
			Comment comment = RecipeService.getComment(idRecipe, idComment);
			
			if (UserID.equals(comment.getUserID())) {
				RecipeService.deleteComment(idRecipe,idComment);
				return  Response.status(Response.Status.OK).build();
			}
			return Response.status(Response.Status.FORBIDDEN).entity("You don't have the rights to call this request.").build();
		}
		return Response.status(Response.Status.UNAUTHORIZED).entity("There is no header or the token is not valid.").build();
	}
	
	@Path("/{idrecipe}/comment/{idcomment}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Comment getComment(@PathParam("idrecipe") Long idRecipe,@PathParam("idcomment") Long idComment) {
		
		return RecipeService.getComment(idRecipe,idComment);
	}

	@Path("/{idrecipe}/comments")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Comment> getComments(@PathParam("idrecipe") Long idRecipe,@PathParam("idcomment") Long idComment) {
		
		Recipe recipe = RecipeService.get(idRecipe);
		
	return recipe.getComments();
	}
	
	
	
	
	@Path("/user")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMyRecipes(@Context HttpHeaders headers) {
	
		if (KeycloakService.verifyAuthentification(headers)) {
			String token = KeycloakService.getToken(headers);
			String UserID = KeycloakService.getIdUser(token);
			List<Recipe> recipes = RecipeService.getListRecipesFromUserId(UserID);
			return Response.status(Response.Status.OK).entity(recipes).build();
		}
		return Response.status(Response.Status.UNAUTHORIZED).entity("There is no header or the token is not valid.").build();
	}

	@Path("/user/{iduser}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Recipe> getRecipesOfUserId(@PathParam("iduser") String idUser) {
		
		return RecipeService.getListRecipesFromUserId(idUser);
	}
	
	@Path("/recipes")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Recipe> getAllRecipes() {

		return RecipeService.getAllRecipes();
	}
	
	@Path("/search/{mySearch}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Object> getSearchedRecipes(@PathParam("mySearch") String mySearch, Map<Long, String> idNom, List<Long> idIngredientFromFridge ) {

		
		
		
		return RecipeService.searchRecipes(mySearch,idNom, idIngredientFromFridge);
	}
	

	
}