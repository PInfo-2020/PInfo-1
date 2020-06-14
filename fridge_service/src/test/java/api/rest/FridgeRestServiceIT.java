package api.rest;


import static io.restassured.RestAssured.when;
import static io.restassured.RestAssured.with;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;

import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.parsing.Parser;

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
	
	String Bearer ="Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICI3WHZWQ2t2anBGYTlCMW1RVUVKLVAyRXYwa1RTMTh1US1zdm5vWEdRSzNnIn0.eyJqdGkiOiJiMjNkNGQ3ZC05ZTYxLTQ4MzgtOTEwZi01YTIzOGFiNGM0NDIiLCJleHAiOjE1OTA3NTc5OTEsIm5iZiI6MCwiaWF0IjoxNTkwNzU3OTMxLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODAvYXV0aC9yZWFsbXMvbWFzdGVyIiwiYXVkIjpbIm1hc3Rlci1yZWFsbSIsImFjY291bnQiXSwic3ViIjoiOWRjMjY2MTgtZGUwNi00ODYyLWI5NDctNzk5ZmI1NTFkMmE0IiwidHlwIjoiQmVhcmVyIiwiYXpwIjoid2ViLXNzbyIsIm5vbmNlIjoiZGJiZmM3MjItNGU4MC00M2VlLTkwMWItOTY2YjU0NGM0NmNmIiwiYXV0aF90aW1lIjoxNTkwNzU3OTMwLCJzZXNzaW9uX3N0YXRlIjoiMjk0MDBmNWYtMTUzMy00NTUxLWJlNjItYTMxOGMzMzczNTNiIiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyIqIl0sInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJjcmVhdGUtcmVhbG0iLCJVc2VyIiwib2ZmbGluZV9hY2Nlc3MiLCJhZG1pbiIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsibWFzdGVyLXJlYWxtIjp7InJvbGVzIjpbInZpZXctcmVhbG0iLCJ2aWV3LWlkZW50aXR5LXByb3ZpZGVycyIsIm1hbmFnZS1pZGVudGl0eS1wcm92aWRlcnMiLCJpbXBlcnNvbmF0aW9uIiwiY3JlYXRlLWNsaWVudCIsIm1hbmFnZS11c2VycyIsInF1ZXJ5LXJlYWxtcyIsInZpZXctYXV0aG9yaXphdGlvbiIsInF1ZXJ5LWNsaWVudHMiLCJxdWVyeS11c2VycyIsIm1hbmFnZS1ldmVudHMiLCJtYW5hZ2UtcmVhbG0iLCJ2aWV3LWV2ZW50cyIsInZpZXctdXNlcnMiLCJ2aWV3LWNsaWVudHMiLCJtYW5hZ2UtYXV0aG9yaXphdGlvbiIsIm1hbmFnZS1jbGllbnRzIiwicXVlcnktZ3JvdXBzIl19LCJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJzY29wZSI6Im9wZW5pZCBwcm9maWxlIGVtYWlsIiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJhZG1pbiJ9.YukZ4nb3kZBRmiwG7lYAI4LJY-ruBA2XXtg2uFWvybi6FIX2HsqMGo79vru2u0Rn8ko46VtsstV056Llop66w7r0hDql1TQm8mmcmktxdDf-JFFYj7e1exbIYXRwuV6ttVAUdahzCAnVM-4JUBTBMrAnSQC1UAB0XveN6l-6IBri-YKSgmn0lofPETiPPzpWDXpYuHDYI9QtoaNKOYhWQGfE1sl5PRsHCsm-qWTr3eoy2OtBkX1yclao2JY27RduS1gl8klSAPOAQDc0wJztRmdK1G79W2pPt0UHx6D34_8zk_mf0PKK5ybZ50bFYOCvIvaNuf3RgQLBJrNovvqplA";	
	String badBearer ="Bearer eyJhbGciOiJwSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICI3WHZWQ2t2anBGYTlCMW1RVUVKLVAyRXYwa1RTMTh1US1zdm5vWEdRSzNnIn0.eyJqdGkiOiJiMjNkNGQ3ZC05ZTYxLTQ4MzgtOTEwZi01YTIzOGFiNGM0NDIiLCJleHAiOjE1OTA3NTc5OTEsIm5iZiI6MCwiaWF0IjoxNTkwNzU3OTMxLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODAvYXV0aC9yZWFsbXMvbWFzdGVyIiwiYXVkIjpbIm1hc3Rlci1yZWFsbSIsImFjY291bnQiXSwic3ViIjoiOWRjMjY2MTgtZGUwNi00ODYyLWI5NDctNzk5ZmI1NTFkMmE0IiwidHlwIjoiQmVhcmVyIiwiYXpwIjoid2ViLXNzbyIsIm5vbmNlIjoiZGJiZmM3MjItNGU4MC00M2VlLTkwMWItOTY2YjU0NGM0NmNmIiwiYXV0aF90aW1lIjoxNTkwNzU3OTMwLCJzZXNzaW9uX3N0YXRlIjoiMjk0MDBmNWYtMTUzMy00NTUxLWJlNjItYTMxOGMzMzczNTNiIiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyIqIl0sInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJjcmVhdGUtcmVhbG0iLCJVc2VyIiwib2ZmbGluZV9hY2Nlc3MiLCJhZG1pbiIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsibWFzdGVyLXJlYWxtIjp7InJvbGVzIjpbInZpZXctcmVhbG0iLCJ2aWV3LWlkZW50aXR5LXByb3ZpZGVycyIsIm1hbmFnZS1pZGVudGl0eS1wcm92aWRlcnMiLCJpbXBlcnNvbmF0aW9uIiwiY3JlYXRlLWNsaWVudCIsIm1hbmFnZS11c2VycyIsInF1ZXJ5LXJlYWxtcyIsInZpZXctYXV0aG9yaXphdGlvbiIsInF1ZXJ5LWNsaWVudHMiLCJxdWVyeS11c2VycyIsIm1hbmFnZS1ldmVudHMiLCJtYW5hZ2UtcmVhbG0iLCJ2aWV3LWV2ZW50cyIsInZpZXctdXNlcnMiLCJ2aWV3LWNsaWVudHMiLCJtYW5hZ2UtYXV0aG9yaXphdGlvbiIsIm1hbmFnZS1jbGllbnRzIiwicXVlcnktZ3JvdXBzIl19LCJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJzY29wZSI6Im9wZW5pZCBwcm9maWxlIGVtYWlsIiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJhZG1pbiJ9.YukZ4nb3kZBRmiwG7lYAI4LJY-ruBA2XXtg2uFWvybi6FIX2HsqMGo79vru2u0Rn8ko46VtsstV056Llop66w7r0hDql1TQm8mmcmktxdDf-JFFYj7e1exbIYXRwuV6ttVAUdahzCAnVM-4JUBTBMrAnSQC1UAB0XveN6l-6IBri-YKSgmn0lofPETiPPzpWDXpYuHDYI9QtoaNKOYhWQGfE1sl5PRsHCsm-qWTr3eoy2OtBkX1yclao2JY27RduS1gl8klSAPOAQDc0wJztRmdK1G79W2pPt0UHx6D34_8zk_mf0PKK5ybZ50bFYOCvIvaNuf3RgQLBJrNovvqplA";	
	String secondBearer = "Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICIzNXA2SEJGU3RfUkx3TjhaamZWa3lsTUoydEhEV0xpclUwLTVzcTBFUHBvIn0.eyJqdGkiOiJkZjVkYTgyMi0xNjU4LTQ5NGItOGUwYy0yYjcxMjJmYWEwNDQiLCJleHAiOjE1NjA0MTg5MDQsIm5iZiI6MCwiaWF0IjoxNTYwNDE4NjA0LCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODAvYXV0aC9yZWFsbXMvYXBpZ3ciLCJzdWIiOiJkOWIzMzM1NS04ZTJjLTRmYzEtOTA2NS04ZTcwODZkMDY5NmEiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJhZG1pbi1jbGkiLCJhdXRoX3RpbWUiOjAsInNlc3Npb25fc3RhdGUiOiI3MjM2MGJmZC05ZTg5LTQ4NzgtOTk5OC00ODE1OWFiNjJkNjUiLCJhY3IiOiIxIiwic2NvcGUiOiJlbWFpbCBwcm9maWxlIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsIm5hbWUiOiJTdMOodmUgSG9zdGV0dGxlciIsInByZWZlcnJlZF91c2VybmFtZSI6InVzZXIxIiwiZ2l2ZW5fbmFtZSI6IlN0w6h2ZSIsImZhbWlseV9uYW1lIjoiSG9zdGV0dGxlciIsImVtYWlsIjoic3RldmUuaG9zdGV0dGxlckBnbWFpbC5jb20ifQ.EegRsElq1xmwX7_etJLFNBkeYaHoGjcOkmz2P2saPVZfSIV29NWbnIMKJjEOzDZwVjASUQ_khhbA_5swdjbdo6jtEvcPRzqwFY3HgJg30uMzEUvU3W1soR9FPOtUVn9vMl3yoqgehxA6NCyJs8QgPjSNEhNBWUFb1chhIWp1qvQyw4TY6KaxeIGy29Pkx_WeXNnpbT4dBwa8kbm_Q89z60ulT26avwEzv0yWu7vS0NJTf6RVVWiBttkjlGxjU2JXh9O7bVtig8NUdYIOwHG4HSRuozjoevJoCxJBcg73omZ5xFQS-v1GqtFaUsKoP8oTeYhJo4Bg-EeZjQcuwfHVdg";
	
	String idOfUser = "9dc26618-de06-4862-b947-799fb551d2a4";
	String idOfSecondUser = "d9b33355-8e2c-4fc1-9065-8e7086d0696a";

	
	
	@Nested
	@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
	class TestPostGetAndPut {
		
	    
	    @Test
	    @Order(1)
	    public void testPutNotExists() {
	    	Fridge fridge = new Fridge();

			fridge = createOtherFridge();
			Header header= new Header("Authorization",Bearer);

			String response = with().contentType(ContentType.JSON).header(header).body(fridge).when().request("PUT","/").then().statusCode(404).extract().asString();
			assertEquals(response, "You don't have any fridge");
	    }
		
	    @Test
	    @Order(2)
	    public void testGetNotExists() { //The user doesn't have any fridge yet
			Header header= new Header("Authorization",secondBearer);
			
			with().header(header).when().request("GET","/").then().statusCode(200).assertThat()
			.body("userId", equalTo(idOfSecondUser))
			.body("ingredients", equalTo(null));
	    }
		
		@Test
		@Order(3)
		public void testPostFridge() {
			
			Header header= new Header("Authorization",Bearer);
			Fridge fridge = new Fridge();

			fridge = createFridge();

			String response = with().contentType(ContentType.JSON).header(header).body(fridge).when().request("POST","/").then().statusCode(200).extract().asString();
			assertEquals(response,"Fridge created.");
		}
		
        @Test
        @Order(4)
        public void testGetFridgeAfterPost() {
    		Date date1 = Date.valueOf("2019-10-29");
    		Date date2 = Date.valueOf("2020-08-26");
    		Header header= new Header("Authorization",Bearer);
    		with().header(header).when().request("GET","/").then().assertThat()
    		.body("userId", equalTo(idOfUser))
    		.body("ingredients.quantity", hasItems(12,2))
    		.body("ingredients.detailsID", hasItems(123,124))
    		.body("ingredients.expiration", hasItems(date1.getTime(),date2.getTime()));
        }
        
        @Test
        @Order(5)
        public void testPutFridge() {
        	Fridge fridge = new Fridge();

    		fridge = createOtherFridge();
    		Header header= new Header("Authorization",Bearer);

    		String response = with().contentType(ContentType.JSON).header(header).body(fridge).when().request("PUT","/").then().statusCode(200).extract().asString();
    		assertEquals(response,"Fridge updated.");
        }
        
        @Test
        @Order(6)
        public void testGetFridgeAfterPut() {
    		Date date1 = Date.valueOf("2021-03-02");
    		Date date2 = Date.valueOf("2020-10-12");
    		Header header= new Header("Authorization",Bearer);
    		with().header(header).when().request("GET","/").then().assertThat()
    		.body("userId", equalTo(idOfUser))
    		.body("ingredients.quantity", hasItems(43,123))
    		.body("ingredients.detailsID", hasItems(2,12))
    		.body("ingredients.expiration", hasItems(date1.getTime(),date2.getTime()));
        }
        
		
	}
	
	
	@Test
	public void testPostUnhautorized() {
		
		Header header= new Header("Authorization",badBearer);
		Fridge fridge = new Fridge();

		fridge = createFridge();

		String response = with().contentType(ContentType.JSON).header(header).body(fridge).when().request("POST","/").then().statusCode(401).extract().asString();
		assertEquals(response, "There is no header or the token is not valid.");
	}
	
    @Test
    public void testGetUnhautorized() {
		Header header= new Header("Authorization",badBearer);
		String response = with().header(header).when().request("GET","/").then().statusCode(401).extract().asString();
		assertEquals(response, "There is no header or the token is not valid.");
    }
    
    @Test
    public void testPutUnhautorized() {
    	Fridge fridge = new Fridge();

		fridge = createOtherFridge();
		Header header= new Header("Authorization",badBearer);

		String response = with().contentType(ContentType.JSON).header(header).body(fridge).when().request("PUT","/").then().statusCode(401).extract().asString();
		assertEquals(response, "There is no header or the token is not valid.");
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