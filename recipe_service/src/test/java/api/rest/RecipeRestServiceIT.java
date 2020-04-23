package api.rest;


import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.containsString;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;

public class RecipeRestServiceIT {

	@BeforeAll
	public static void setup() {
		RestAssured.baseURI = "http://localhost:28080/recipe";
		RestAssured.port = 8080;
	}
/*
	@Test
	public void test() {
		when().post("/").then().body(containsString("Hello World2"));

	}*/

	@Test
	public void testGetRecipe() {
		when().get("/1").then().body(containsString("Abricot"));
	}


}