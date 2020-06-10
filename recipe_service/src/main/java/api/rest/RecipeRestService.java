package api.rest;

import java.util.ArrayList;
import java.sql.Date;
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
	
	//@Inject
	//private KeycloakService KeycloakService;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public long postRecipe(Recipe recipe) {
		//if (KeycloakService.verifyAuthentification(headers,  new Date())) {
		//	String token = KeycloakService.getToken(headers);
		//	String UserID = KeycloakService.getIdUser(token);
		//	String idAuthor = recipe.getAuthor();
		//	if (UserID == idAuthor) {
			Date date = new java.sql.Date(System.currentTimeMillis());
			recipe.setPublicationDate(date);
			recipe.setGrade(-1);
			List<Comment> comments = new ArrayList<Comment>();
			recipe.setComments(comments);
			
			long id = RecipeService.create(recipe);
			return id;
		//	}
		//}
		//return 0;
	}
	
	@Path("/{idrecipe}")
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	public void deleteRecipe(@PathParam("idrecipe") Long idRecipe) {
		RecipeService.delete(idRecipe);
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
	public long postComment(@PathParam("idrecipe") Long idRecipe, Comment comment, @Context HttpHeaders headers) {
		/*
		if (KeycloakService.verifyAuthentification(headers,  new Date())) {
			String token = KeycloakService.getToken(headers);
			String UserID = KeycloakService.getIdUser(token);
			String idAuthor = comment.getUserID();
			if (UserID == idAuthor) {
				
			}
		}
		*/
		return RecipeService.addComment(idRecipe,comment);
	}
	
	@Path("/{idrecipe}/comment/{idcomment}")
	@DELETE
	public void deleteComment(@PathParam("idrecipe") Long idRecipe,@PathParam("idcomment") Long idComment, @Context HttpHeaders headers) {
		/*
		if (KeycloakService.verifyAuthentification(headers,  new Date())) {
			String token = KeycloakService.getToken(headers);
			String UserID = KeycloakService.getIdUser(token);
			Comment comment = RecipeService.getComment(idRecipe, idComment);
			String idAuthor = comment.getUserID();
			if (UserID == idAuthor) {
				RecipeService.addComment(idRecipe,comment);
			}
		}*/
		RecipeService.deleteComment(idRecipe,idComment);
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
	public String getUserID(@Context HttpHeaders headers) {
		
		String UserID = "Something has gone wrong. Wrong token.";
		/*
		if (KeycloakService.verifyAuthentification(headers,  new Date())) {
			String token = KeycloakService.getToken(headers);
			UserID = KeycloakService.getIdUser(token);
		}
		*/
		return UserID;
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
	public List<Recipe> getSearchedRecipes(@PathParam("mySearch") String mySearch) {

		return RecipeService.searchRecipes(mySearch);
	}

	
}