package api.rest;


import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.containsString;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.h2.util.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import api.rest.RecipeRestService;
import domain.model.Comment;
import domain.model.Recipe;
import domain.service.RecipeService;

public class RecipeRestServiceIT {

	@BeforeAll
	public static void setup() {
		RestAssured.baseURI = "http://localhost:28080/recipe";
		RestAssured.port = 8080;
	}
	@InjectMocks
	RecipeRestService recipeRestService;
	
	@InjectMocks
	RecipeService recipeService;
	
	

	@Test
	public void testPostRecipe() {
		
	}
/*
	@Test
	public void testGetRecipe() {
		when().get("/1").then().body(containsString("Abricot"));
		when().get("/1").then().body(containsString("vegan"));
	}
	
	@Test
	public void testPostComment() {
		when().get("/1").then().body(containsString("Abricot"));
		when().get("/1").then().body(containsString("vegan"));
	}

	@Test
	public void testDeleteComment() {
		
		recipeService.create(getRandomRecipe());
		List<Recipe> recipes = recipeService.getAllRecipes();
		Recipe myRecipe = recipes.get(0);
		Comment comment1 = recipeService.createComment("bonjour c'est moyen", "asdfakasy", (short)3);
		Comment comment2 = recipeService.createComment("bonjour je suis content", "lasdfasdf", (short)5);
		List<Comment> listComment = new ArrayList<Comment>();
		listComment.add(comment1);
		listComment.add(comment2);
		myRecipe.setComments(listComment);
		RecipeService.createComment(text, userID, grade);
		RecipeRestService.deleteComment(idRecipe, idComment, headers);
		
		when().get("/1").then().body(containsString("Abricot"));
		when().get("/1").then().body(containsString("vegan"));
	}
	
*/

}