package api.rest;


import static io.restassured.RestAssured.when;
import static io.restassured.RestAssured.with;
import static io.restassured.RestAssured.given;
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
import api.rest.FridgeRestService;
import domain.model.Ingredient;
import domain.model.Fridge;
import domain.service.FridgeService;

public class FridgeRestServiceIT {

	@BeforeAll
	public static void setup() {
		RestAssured.baseURI = "http://localhost:28080/fridge";
		RestAssured.port = 8080;
	}
	
	
	//@InjectMocks
	//KeycloakService KeycloakService;

	@Test
	public void testPostRecipe() {
		
		Fridge fridge = new Fridge();

		fridge = createFridge();

		with().contentType(ContentType.JSON).body(fridge).when().request("POST","/").then().statusCode(204);
	}
	
	@Test
	public void testGetByUserId() {

		when().get("/testId").then().body(containsString("testId"));
	}

	
	private List<Ingredient> createListIngredients(){
		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		Ingredient ing1 = new Ingredient();
		ing1.setDetailsID(123);
		ing1.setQuantity((short)12);
		ing1.setExpiration(new java.util.Date());
		Ingredient ing2 = new Ingredient();
		ing2.setDetailsID(124);
		ing2.setQuantity((short)2);
		ing2.setExpiration(new java.util.Date());
		ingredients.add(ing1);
		ingredients.add(ing2);
		return ingredients;
	}
	private Fridge createFridge() {
		Fridge fridge = new Fridge();
		fridge.setIngredients(createListIngredients());
		fridge.setUserId("testId");
		return fridge;
		
	}
	
}