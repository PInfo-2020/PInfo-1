package api.rest;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;


import javax.enterprise.context.ApplicationScoped;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


import javax.ws.rs.core.HttpHeaders;

import domain.model.Comment;
import domain.model.Recipe;
import domain.service.RecipeService;
import domain.service.KeycloakService;


@ApplicationScoped
@Path("/recipe")
public class RecipeRestService {
	
	@Inject
	private RecipeService recipeService;
	
	@Inject
	private KeycloakService keycloakService;
	
	private static String unauthorizedError = "There is no header or the token is not valid.";

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postRecipe(Recipe recipe, @Context HttpHeaders headers) {
		if (keycloakService.verifyAuthentification(headers)) {
			String token = keycloakService.getToken(headers);
			String userID = keycloakService.getIdUser(token);
			recipe.setAuthorId(userID);
			Date date = new Date(System.currentTimeMillis());
			recipe.setPublicationDate(date);
			recipe.setGrade(-1);
			List<Comment> comments = new ArrayList<>();
			recipe.setComments(comments);
			long id = recipeService.create(recipe);
			return Response.status(Response.Status.OK).entity(id).build();
		
		}
		return Response.status(Response.Status.UNAUTHORIZED).entity(unauthorizedError).build();
	}
	
	@Path("/{idrecipe}")
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteRecipe(@PathParam("idrecipe") Long idRecipe, @Context HttpHeaders headers) {
		if (keycloakService.verifyAuthentification(headers)) {
			String token = keycloakService.getToken(headers);
			String userID = keycloakService.getIdUser(token);
			Recipe recipe = recipeService.get(idRecipe);
			if (recipe.getAuthorId().equals(userID)) {
				recipeService.delete(idRecipe);
				return Response.status(Response.Status.OK).build();
			}
			return Response.status(Response.Status.FORBIDDEN).entity("You don't have the rights to call this request.").build();
			
		}
		return Response.status(Response.Status.UNAUTHORIZED).entity(unauthorizedError).build();
	}
	
	@Path("/{idrecipe}")
    @GET
	@Produces(MediaType.APPLICATION_JSON)
    public Recipe getRecipe(@PathParam("idrecipe") Long idRecipe) {
        return recipeService.get(idRecipe);
    }
	
	@Path("/{idrecipe}/comment")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postComment(@PathParam("idrecipe") Long idRecipe,Comment comment, @Context HttpHeaders headers) {
		
		if (keycloakService.verifyAuthentification(headers)) {
			String token = keycloakService.getToken(headers);
			String userID = keycloakService.getIdUser(token);
			comment.setUserID(userID);
			long idComment = recipeService.addComment(idRecipe,comment);
			
			return Response.status(Response.Status.OK).entity(idComment).build();
		}
		return Response.status(Response.Status.UNAUTHORIZED).entity(unauthorizedError).build();
	}
	
	@Path("/{idrecipe}/comment/{idcomment}")
	@DELETE
	public Response deleteComment(@PathParam("idrecipe") Long idRecipe,@PathParam("idcomment") Long idComment, @Context HttpHeaders headers) {
		
		if (keycloakService.verifyAuthentification(headers)) {
			String token = keycloakService.getToken(headers);
			String userID = keycloakService.getIdUser(token);
			Comment comment = recipeService.getComment(idRecipe, idComment);
			
			if (userID.equals(comment.getUserID())) {
				recipeService.deleteComment(idRecipe,idComment);
				return  Response.status(Response.Status.OK).build();
			}
			return Response.status(Response.Status.FORBIDDEN).entity("You don't have the rights to call this request.").build();
		}
		return Response.status(Response.Status.UNAUTHORIZED).entity(unauthorizedError).build();
	}
	
	@Path("/{idrecipe}/comment/{idcomment}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Comment getComment(@PathParam("idrecipe") Long idRecipe,@PathParam("idcomment") Long idComment) {
		
		return recipeService.getComment(idRecipe,idComment);
	}

	@Path("/{idrecipe}/comments")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Comment> getComments(@PathParam("idrecipe") Long idRecipe,@PathParam("idcomment") Long idComment) {
		
	return recipeService.get(idRecipe).getComments();
	}
	
	
	
	
	@Path("/user")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMyRecipes(@Context HttpHeaders headers) {
	
		if (keycloakService.verifyAuthentification(headers)) {
			String token = keycloakService.getToken(headers);
			String userID = keycloakService.getIdUser(token);
			List<Recipe> recipes = recipeService.getListRecipesFromUserId(userID);
			return Response.status(Response.Status.OK).entity(recipes).build();
		}
		return Response.status(Response.Status.UNAUTHORIZED).entity(unauthorizedError).build();
	}

	@Path("/user/{iduser}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Recipe> getRecipesOfUserId(@PathParam("iduser") String idUser) {
		
		return recipeService.getListRecipesFromUserId(idUser);
	}
	
	@Path("/recipes")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Recipe> getAllRecipes() {

		return recipeService.getAllRecipes();
	}
	
	@Path("/search/{mySearch}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSearchedRecipes(@PathParam("mySearch") String mySearch,@QueryParam("id") List<Long> idIngredientFromFridge, @QueryParam("quantity") List<Long> idQuantityFromFridge) {

		if(idIngredientFromFridge.size()!=idQuantityFromFridge.size()) {
			return Response.status(Response.Status.BAD_REQUEST).entity("ids and quantities have not same size").build();
		}
		List<Recipe> recipes = recipeService.searchRecipes(mySearch, idIngredientFromFridge, idQuantityFromFridge);
		return Response.status(Response.Status.OK).entity(recipes).build();
        //return recipeService.searchRecipes(mySearch, idIngredientFromFridge, idQuantityFromFridge);
    }
	

	
}