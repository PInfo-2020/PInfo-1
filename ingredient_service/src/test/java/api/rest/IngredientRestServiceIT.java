package api.rest;


import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.containsString;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;

public class IngredientRestServiceIT {

	@BeforeAll
	public static void setup() {
		RestAssured.baseURI = "http://localhost:28080/ingredients";
		RestAssured.port = 8080;
	}

	@Test
	public void testGetMinInfos() {
		when().get("/minInfos").then().body(containsString("Abricot, sucré, conserve"));
	}
	
	@Test
	public void testGetIngedients() {
		when().get("/").then().body(containsString("Abricot, sucré, conserve"));
	}

}