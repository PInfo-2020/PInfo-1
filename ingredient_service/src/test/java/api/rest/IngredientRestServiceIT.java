package api.rest;


import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.containsString;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import domain.model.Ingredient;
import io.restassured.RestAssured;

public class IngredientRestServiceIT {

	@BeforeAll
	public static void setup() {
		RestAssured.baseURI = "http://localhost:28080/ingredients";
		RestAssured.port = 8080;
	}

	@Test
	void testGetMinInfos() {
		when().get("/minInfos").then().body(containsString("Abricot, sucré, conserve"));
		List<List<String>> response = new ArrayList<>();
		response = when().get("/minInfos").then().extract().body().as(response.getClass());
		
		assertEquals(3,response.size());
		
		assertEquals("Abricot", response.get(0).get(1));
		assertEquals("unité/g", response.get(0).get(2));
	}
	
	@Test
	void testGetIdName() {
		when().get("/idName").then().body(containsString("Abricot, sucré, conserve"));
		List<List<String>> response = new ArrayList<>();
		response = when().get("/idName").then().extract().body().as(response.getClass());
		
		assertEquals(3,response.size());
		
		assertEquals("Abricot", response.get(0).get(1));
	}
	
	@Test
	void testGetIngedients() {
		when().get("/").then().body(containsString("Abricot, sucré, conserve"));
		
		List<Ingredient> response = when().get("/").then().extract().body().jsonPath().getList(".", Ingredient.class);
		
		assertEquals(3,response.size());
		
		Ingredient ing = response.get(0);
		
		assertEquals("Abricot", ing.getName());
		assertEquals(60, ing.getAverageWeight());
		assertEquals("unité/g", ing.getUnity());
		assertEquals("Fruits/Fruits frais", ing.getCategory());
		
		
	}
	
}