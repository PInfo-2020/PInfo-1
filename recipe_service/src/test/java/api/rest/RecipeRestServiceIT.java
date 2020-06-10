package api.rest;


import static io.restassured.RestAssured.when;
import static io.restassured.RestAssured.with;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.hamcrest.Matchers.containsString;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.h2.util.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
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
		RestAssured.defaultParser = Parser.JSON;
	}
	
	
	//@InjectMocks
	//KeycloakService KeycloakService;

	private static long idOfPostRecipe;
	
	private static long idOfPostComment;
	

	
	@Nested
	@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
	class TestPost {


		@Test
		@Order(1)
		public void testPostRecipe() {
			
			List<Ingredient> listIng = new ArrayList<Ingredient>();
			Ingredient ingredient1 = createIngredient(50l, (short)10);
			Ingredient ingredient2 = createIngredient(55l, (short)15);
			listIng.add(ingredient1);
			listIng.add(ingredient2);
			Comment comment1 = createComment("bonjour je suis pas content", "asdfakasy", (short)1);
			Comment comment2 = createComment("bonjour je suis content", "lasdfasdf", (short)5);
			List<Comment> listComment = new ArrayList<Comment>();
			listComment.add(comment1);
			listComment.add(comment2);
			Recipe recipe = createRecipe("maRecette", listIng, (short)5, (short)5, (short)4, "maPhoto", "fais ceci cela",
					"aprfg", Date.valueOf("2019-01-26"), 4.5f, listComment);
					
					
					
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
	
			// long id = recipe.getId();
			// given()
			// .contentType(ContentType.JSON)
			// .body(recipejson)
			// .when()
			// .post("/")
			// .then()
			// .statusCode(200);
			String id_string = with().contentType(ContentType.JSON).body(recipe).when().request("POST","/").then().statusCode(200).extract().asString();
			//assertThat(id_string).isNotEmpty();
			idOfPostRecipe = Long.parseLong(id_string);
			
		}
		
        @Test
        @Order(2)
        public void testGetRecipeAfterPost() {
        	long id = idOfPostRecipe;
    		when().get("/" + id).then().assertThat()
    		.body("author", equalTo("aprfg"))
    		.body("name", equalTo("maRecette"))
    		.body("picture", equalTo("maPhoto"))
    		.body("nbPersons", equalTo(4))
    		.body("preparationTime", equalTo(5))
    		.body("difficulty", equalTo(5))
    		.body("preparation", equalTo("fais ceci cela"))
    		.body("grade", equalTo(-1.0f))
    		.body("ingredients.quantity", hasItems(10,15))
    		.body("ingredients.detailsID", hasItems(50,55))
    		.body("comments", hasSize(0));
        }
        
        
        @Test
        @Order(3)
    	public void testPostComment() {
        	long id = idOfPostRecipe;
    		Comment comment =  createComment("dégueu", "méchant",(short) 5);

    		when().get("/"+id).then().body("comments", hasSize(0));
    		String idComment = with().contentType(ContentType.JSON).body(comment).when().request("POST","/"+id+"/comment").then().statusCode(200).extract().asString();
    		idOfPostComment = Long.parseLong(idComment);
    		when().get("/"+id).then().body("comments", hasSize(1));
    		when().get("/"+id).then().assertThat()
    		.body("comments.userID", hasItems("méchant"))
    		.body("comments.text", hasItems("dégueu"))
    		.body("comments.grade", hasItems(5));
    		
    	}
        
        @Test
        @Order(4)
        public void testGetCommentAfterPost() {
        	long id = idOfPostRecipe;
        	long idComment = idOfPostComment;
        	when().get("/"+id+"/comment/"+idComment).then().assertThat()
        	.body("userID", equalTo("méchant"))
        	.body("text", equalTo("dégueu"))
        	.body("grade", equalTo(5));
        }
        
        @Test
        @Order(5)
        public void testGetCommentsAfterPost() {
        	long id = idOfPostRecipe;
        	List<Comment> response = when().get("/"+id+"/comments").then().extract().body().jsonPath().getList(".", Comment.class);
        	assertEquals(1,response.size());
        	Comment myComment = response.get(0);
        	assertEquals("méchant",myComment.getUserID());
        	assertEquals("dégueu",myComment.getText());
        	assertEquals(5,myComment.getGrade());
        }
        
        
        @Test
        @Order(6)
    	public void testDeleteComment() {
        	long id = idOfPostRecipe;
    		long idComment = idOfPostComment;
    		when().get("/"+id).then().body("comments", hasSize(1));
    		with().delete("/"+id+"/comment/"+idComment).then().statusCode(204);
    		with().get("/"+id).then().body("comments", hasSize(0));
    		
    	}
        
        @Test
        @Order(7)
        public void testDeleteRecipeAfterPost() {
        	long id = idOfPostRecipe;
        	when().get("/" + id).then().assertThat().statusCode(200); //Content a response
        	when().delete("/" + id).then().assertThat().statusCode(204);
        	when().get("/" + id).then().assertThat().statusCode(204); //Doesn't content anything
        }
        
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
		
		assertEquals(2,response.size());
		
		Recipe Recipe1 = response.get(0);
		Recipe Recipe2 = response.get(1);
		
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
	
	@Test
	public void testSearch() {
		
		List<Ingredient> listIng = new ArrayList<Ingredient>();
		Ingredient ingredient1 = createIngredient(50l, (short)10);
		Ingredient ingredient2 = createIngredient(55l, (short)15);
		listIng.add(ingredient1);
		listIng.add(ingredient2);
		List<Comment> listComment = new ArrayList<Comment>();
		
		Recipe newRecipe = createRecipe("tarte aux fraises bernoise", listIng, (short)5, (short)5, (short)4, "maPhoto", "fais ceci cela",
				"aprfg", Date.valueOf("2019-01-26"), 4.5f, listComment);
		
		List<Ingredient> listIng2 = new ArrayList<Ingredient>();
		Ingredient ingredient3 = createIngredient(512l, (short)4);
		Ingredient ingredient4 = createIngredient(12l, (short)152);
		listIng2.add(ingredient3);
		listIng2.add(ingredient4);
		
		Recipe newRecipe2 = createRecipe("tarte aux fraises suisse", listIng2, (short)6, (short)4, (short)2, "maPhoto", "Prepare bien",
				"moi", Date.valueOf("2020-03-28"), 4.7f, listComment);
		
		Recipe newRecipe3 = createRecipe("Chocolat noir", listIng2, (short)6, (short)4, (short)2, "maPhoto", "Prepare",
				"moi", Date.valueOf("2016-03-28"), 2f, listComment);
		
		String id_string1 = with().contentType(ContentType.JSON).body(newRecipe).when().request("POST","/").then().statusCode(200).extract().asString();
		idOfPostRecipe = Long.parseLong(id_string1);
		
		String id_string2 = with().contentType(ContentType.JSON).body(newRecipe2).when().request("POST","/").then().statusCode(200).extract().asString();
		idOfPostRecipe = Long.parseLong(id_string2);
		
		String id_string3 = with().contentType(ContentType.JSON).body(newRecipe3).when().request("POST","/").then().statusCode(200).extract().asString();
		idOfPostRecipe = Long.parseLong(id_string3);
		
		String mySearch1 = "Tartes à la fraises";
		String mySearch2 = "poires aux truffes";
		String mySearch3 = "chocolat";
		List<Recipe> response1 = when().get("/search/" + mySearch1).then().extract().body().jsonPath().getList(".", Recipe.class);
		when().get("/search/" + mySearch2).then().assertThat().statusCode(204);
		List<Recipe> response3 = when().get("/search/" + mySearch3).then().extract().body().jsonPath().getList(".", Recipe.class);
		
		assertEquals(2,response1.size());
		assertEquals(1,response3.size());
		assertEquals(newRecipe3.getName(), response3.get(0).getName());
		
		
	}



	
	
	
	
	
	
	
	
	



	private Comment createComment(String text, String userID,short grade) {
		Comment comment = new Comment();
		comment.setText(text);
		comment.setUserID(userID);
		comment.setGrade(grade);
		return comment;
	}
	
	private Ingredient createIngredient(long detailsID, short quantity) {
		Ingredient ingredient = new Ingredient();
		ingredient.setDetailsID(detailsID);
		ingredient.setQuantity(quantity);
		
		return ingredient;
	}
	
	private Recipe createRecipe(String name, List<Ingredient> ingredients, short prepTime, short difficulty, short nbPersonnes,
			String photo, String preparation, String auteur, Date date, float note, List<Comment> comments) {

		Recipe i = new Recipe();
		i.setName(name);
		i.setIngredients(ingredients);
		i.setPreparationTime(prepTime);
		i.setDifficulty(difficulty);
		i.setNbPersons(nbPersonnes);
		i.setPicture(photo);
		i.setPreparation(preparation);
		i.setAuthor(auteur);
		i.setPublicationDate(date);
		i.setGrade(note);
		i.setComments(comments);

		return i;

	}
	
	
}