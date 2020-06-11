package api.rest;


import static io.restassured.RestAssured.when;
import static io.restassured.RestAssured.with;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;

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
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import api.rest.FridgeRestService;


import domain.model.Fridge;
import domain.model.Ingredient;
import domain.service.FridgeService;

public class FridgeRestServiceIT {

	@BeforeAll
	public static void setup() {
		RestAssured.baseURI = "http://localhost:28080/fridge";
		RestAssured.port = 8080;
	}
	
	
	//@InjectMocks
	//KeycloakService KeycloakService;

	
	
	@Nested
	@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
	class TestPostAndPut {
		@Test
		@Order(1)
		public void testPostFridge() {
			
			Fridge fridge = new Fridge();

			fridge = createFridge();

			with().contentType(ContentType.JSON).body(fridge).when().request("POST","/").then().statusCode(204);
		}
		
        @Test
        @Order(2)
        public void testGetFridgeAfterPost() {
    		Date date1 = Date.valueOf("2019-10-29");
    		Date date2 = Date.valueOf("2020-08-26");
    		when().get("/myId").then().assertThat()
    		.body("userId", equalTo("myId"))
    		.body("ingredients.quantity", hasItems(12,2))
    		.body("ingredients.detailsID", hasItems(123,124))
    		.body("ingredients.expiration", hasItems(date1.getTime(),date2.getTime()));
        }
        
        @Test
        @Order(3)
        public void testPutFridge() {
        	Fridge fridge = new Fridge();

    		fridge = createOtherFridge();

    		with().contentType(ContentType.JSON).body(fridge).when().request("PUT","/").then().statusCode(204);
        }
        
        @Test
        @Order(4)
        public void testGetFridgeAfterPut() {
    		Date date1 = Date.valueOf("2021-03-02");
    		Date date2 = Date.valueOf("2020-10-12");
    		when().get("/myId").then().assertThat()
    		.body("userId", equalTo("myId"))
    		.body("ingredients.quantity", hasItems(43,123))
    		.body("ingredients.detailsID", hasItems(2,12))
    		.body("ingredients.expiration", hasItems(date1.getTime(),date2.getTime()));
        }
        
		
	}
	
	
	
	

	@Test
	public void testGetByUserId() {

		when().get("/testId").then().body(containsString("10"));
		when().get("/testId").then().assertThat().body("userId", equalTo("testId"));
		when().get("/testId").then().assertThat().body("ingredients.quantity", hasItems(5,10));
		when().get("/testId").then().assertThat().body("ingredients.detailsID", hasItems(47,48));
		Date date1 = Date.valueOf("2020-07-12");
		Date date2 = Date.valueOf("2020-08-26");
		when().get("/testId").then().assertThat().body("ingredients.expiration", hasItems(date1.getTime(),date2.getTime()));

	}
	
	
	private List<Ingredient> createListIngredients(){
		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		Ingredient ing1 = new Ingredient();
		ing1.setDetailsID(123);
		ing1.setQuantity((short)12);
		ing1.setExpiration(Date.valueOf("2019-10-29"));
		Ingredient ing2 = new Ingredient();
		ing2.setDetailsID(124);
		ing2.setQuantity((short)2);
		ing2.setExpiration(Date.valueOf("2020-08-26"));
		ingredients.add(ing1);
		ingredients.add(ing2);
		return ingredients;
	}
	
	private List<Ingredient> createOtherListIngredients(){
		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		Ingredient ing1 = new Ingredient();
		ing1.setDetailsID(2);
		ing1.setQuantity((short)43);
		ing1.setExpiration(Date.valueOf("2021-03-02"));
		Ingredient ing2 = new Ingredient();
		ing2.setDetailsID(12);
		ing2.setQuantity((short)123);
		ing2.setExpiration(Date.valueOf("2020-10-12"));
		ingredients.add(ing1);
		ingredients.add(ing2);
		return ingredients;
	}
	
	private Fridge createFridge() {
		Fridge fridge = new Fridge();
		fridge.setIngredients(createListIngredients());
		fridge.setUserId("myId");
		return fridge;
		
	}
	
	private Fridge createOtherFridge() {
		Fridge fridge = new Fridge();
		fridge.setIngredients(createOtherListIngredients());
		fridge.setUserId("myId");
		return fridge;
		
	}
	
}