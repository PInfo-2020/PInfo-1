package api.rest;


import static io.restassured.RestAssured.when;
import static io.restassured.RestAssured.with;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.hamcrest.Matchers.containsString;
import io.restassured.http.ContentType;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;

import org.h2.util.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import api.rest.RecipeRestService;
import domain.model.Comment;
import domain.model.Ingredient;
import domain.model.Recipe;
import domain.service.RecipeService;

public class RecipeRestServiceIT {

	@BeforeAll
	public static void setup() {
		RestAssured.baseURI = "http://localhost:28080/recipe";
		RestAssured.port = 8080;
	}
	
	
	//@InjectMocks
	//KeycloakService KeycloakService;

	@Test
	public void testPostRecipe() {
		
		Recipe recipe = new Recipe();
		// JsonObject recipejson = Json.createObjectBuilder()
		// 		.add("name", "name")
		// 		.add("picture", "picture")
		// 		.add("nbPersons", 5)
		// 		.add("preparationTime", 523)
		// 		.add("difficulty", 642)
		// 		.add("ingredients", Json.createArrayBuilder()
		// 				.add(Json.createObjectBuilder()
		// 						.add("quantity",296)
		// 						.add("detailsID", 6))
		// 				.add(Json.createObjectBuilder()
		// 						.add("quantity",269)
		// 						.add("detailsID", 834)))
		// 		.add("preparation", "preparation")
		// 		.add("author", "author")
		// 		.add("publicationData",15400)
		// 		.add("grade", 3.5)
		// 		.add("comments", Json.createArrayBuilder()
		// 				.add("comment"))
		// 		.build();
		recipe = getRandomRecipe();
		// long id = recipe.getId();
		// given()
		// .contentType(ContentType.JSON)
		// .body(recipejson)
		// .when()
		// .post("/")
		// .then()
		// .statusCode(200);
		with().contentType(ContentType.JSON).body(recipe).when().request("POST","/").then().statusCode(200);
	}

	
	@Test
	public void testGetById() {

		when().get("/2").then().body(containsString("Choux à la crème"));
		Date date = Date.valueOf("2020-07-12");
		when().get("/1").then().assertThat()
		.body("author", equalTo("testId"))
		.body("name", equalTo("Tarte aux citrons"))
		.body("picture", equalTo("monImage"))
		.body("nbPersons", equalTo(5))
		.body("preparationTime", equalTo(10))
		.body("difficulty", equalTo(5))
		.body("preparation", equalTo("Prépare la tarte"))
		.body("publicationDate", equalTo(date.getTime()))
		.body("grade", equalTo(4.5f))
		.body("ingredients.quantity", hasItems(4,12))
		.body("ingredients.detailsID", hasItems(0,1))
		.body("comments.text", hasItems("Pas mal","Très bon", "Dégeulasse"))
		.body("comments.userID", hasItems("commentateur","AutreCommentateur", "randomGuy"))
		.body("comments.grade", hasItems(3,4,1));
	}
	
	
	@Test
	public void testGetByUserId() {
		
		List<Recipe> response = when().get("/user/autreId").then().extract().body().jsonPath().getList(".", Recipe.class);
		
		assertEquals(1,response.size());
		
		Recipe myRecipe = response.get(0);
		
		assertEquals("Choux à la crème", myRecipe.getName());
		assertEquals("monAutreImage", myRecipe.getPicture());
		assertEquals(2, myRecipe.getNbPersons());
		assertEquals(15, myRecipe.getPreparationTime());
		assertEquals(3, myRecipe.getDifficulty());
		assertEquals("Prépare le choux", myRecipe.getPreparation());
		assertEquals("autreId", myRecipe.getAuthor());
		assertEquals(3, myRecipe.getGrade());
		assertEquals(Date.valueOf("2020-04-24"), myRecipe.getPublicationDate());
		assertEquals(2, myRecipe.getIngredients().size());
		assertEquals(3, myRecipe.getIngredients().get(0).getQuantity());
		assertEquals(2, myRecipe.getIngredients().get(0).getDetailsID());
		assertEquals(42, myRecipe.getIngredients().get(1).getQuantity());
		assertEquals(3, myRecipe.getIngredients().get(1).getDetailsID());
		assertEquals(1, myRecipe.getComments().size());
		assertEquals("passable", myRecipe.getComments().get(0).getText());
		assertEquals(3, myRecipe.getComments().get(0).getGrade());
		assertEquals("moi", myRecipe.getComments().get(0).getUserID());
		
	}
	
	
	@Test
	public void testGetAll() {

		List<Recipe> response = when().get("/recipes").then().extract().body().jsonPath().getList(".", Recipe.class);
		
		assertEquals(3,response.size()); // Le POST du premier test a déposé une recette, il y en a donc 3.
		
		Recipe Recipe1 = response.get(1);
		Recipe Recipe2 = response.get(2);
		
		assertEquals("Tarte aux citrons", Recipe1.getName());
		assertEquals("monImage", Recipe1.getPicture());
		assertEquals(5, Recipe1.getNbPersons());
		assertEquals(10, Recipe1.getPreparationTime());
		assertEquals(5, Recipe1.getDifficulty());
		assertEquals("Prépare la tarte", Recipe1.getPreparation());
		assertEquals("testId", Recipe1.getAuthor());
		assertEquals(4.5, Recipe1.getGrade());
		assertEquals(Date.valueOf("2020-07-12"), Recipe1.getPublicationDate());
		assertEquals(2, Recipe1.getIngredients().size());
		assertEquals(4, Recipe1.getIngredients().get(0).getQuantity());
		assertEquals(0, Recipe1.getIngredients().get(0).getDetailsID());
		assertEquals(12, Recipe1.getIngredients().get(1).getQuantity());
		assertEquals(1, Recipe1.getIngredients().get(1).getDetailsID());
		assertEquals(3, Recipe1.getComments().size());
		assertEquals("Pas mal", Recipe1.getComments().get(0).getText());
		assertEquals(3, Recipe1.getComments().get(0).getGrade());
		assertEquals("commentateur", Recipe1.getComments().get(0).getUserID());
		assertEquals("Très bon", Recipe1.getComments().get(1).getText());
		assertEquals(4, Recipe1.getComments().get(1).getGrade());
		assertEquals("AutreCommentateur", Recipe1.getComments().get(1).getUserID());
		assertEquals("Dégeulasse", Recipe1.getComments().get(2).getText());
		assertEquals(1, Recipe1.getComments().get(2).getGrade());
		assertEquals("randomGuy", Recipe1.getComments().get(2).getUserID());
		
		assertEquals("Choux à la crème", Recipe2.getName());
		assertEquals("monAutreImage", Recipe2.getPicture());
		assertEquals(2, Recipe2.getNbPersons());
		assertEquals(15, Recipe2.getPreparationTime());
		assertEquals(3, Recipe2.getDifficulty());
		assertEquals("Prépare le choux", Recipe2.getPreparation());
		assertEquals("autreId", Recipe2.getAuthor());
		assertEquals(3, Recipe2.getGrade());
		assertEquals(Date.valueOf("2020-04-24"), Recipe2.getPublicationDate());
		assertEquals(2, Recipe2.getIngredients().size());
		assertEquals(3, Recipe2.getIngredients().get(0).getQuantity());
		assertEquals(2, Recipe2.getIngredients().get(0).getDetailsID());
		assertEquals(42, Recipe2.getIngredients().get(1).getQuantity());
		assertEquals(3, Recipe2.getIngredients().get(1).getDetailsID());
		assertEquals(1, Recipe2.getComments().size());
		assertEquals("passable", Recipe2.getComments().get(0).getText());
		assertEquals(3, Recipe2.getComments().get(0).getGrade());
		assertEquals("moi", Recipe2.getComments().get(0).getUserID());
	}
	
	
	/*
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

	private Ingredient getRandomIngredient() {
		Ingredient ingredient = new Ingredient();
		ingredient.setDetailsID((long) (Math.random()*1000));
		ingredient.setQuantity((short) (Math.random()*1000));
		
		return ingredient;
	}
	
	private Comment getRandomComment() {
		Comment comment = new Comment();
		comment.setText(UUID.randomUUID().toString());
		comment.setUserID(UUID.randomUUID().toString());
		comment.setGrade((short)(Math.random() * ((5 - 0) + 1)));
		
		return comment;
	}
	
	private Recipe getRandomRecipe() {
		
		List<Ingredient> listIng = new ArrayList<Ingredient>();
		Ingredient ingredient1 = getRandomIngredient();
		Ingredient ingredient2 = getRandomIngredient();
		listIng.add(ingredient1);
		listIng.add(ingredient2);
		
		Comment comment1 = getRandomComment();
		Comment comment2 = getRandomComment();
		List<Comment> listComment = new ArrayList<Comment>();
		listComment.add(comment1);
		listComment.add(comment2);
		
		Recipe i = new Recipe();
		i.setName(UUID.randomUUID().toString());
		i.setPreparationTime((short) (Math.random()*1000));
		i.setDifficulty((short) (Math.random()*1000));
		i.setNbPersons((short) (Math.random()*1000));
		i.setPicture(UUID.randomUUID().toString());
		i.setPreparation(UUID.randomUUID().toString());
		i.setAuthor(UUID.randomUUID().toString());
		i.setPublicationDate(Date.valueOf("2019-01-26"));
		i.setGrade((float) (Math.random()*1000));
		i.setComments(listComment);
		i.setIngredients(listIng);

		
		return i;
	}
	
	
}