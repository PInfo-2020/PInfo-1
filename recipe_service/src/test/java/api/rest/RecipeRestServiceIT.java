package api.rest;
 
 
import static io.restassured.RestAssured.when;
import static io.restassured.RestAssured.with;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.hamcrest.Matchers.containsString;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.parsing.Parser;
 
import java.sql.Date;
import java.util.ArrayList;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
 
import io.restassured.RestAssured;
import domain.model.Comment;
import domain.model.Ingredient;
import domain.model.Recipe;

 
 
class RecipeRestServiceIT {
 
 
 
    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost:28080/recipe";
        RestAssured.port = 8080;
        RestAssured.defaultParser = Parser.JSON;
    }
   
   
   
    private static long idOfPostRecipe;
   
    private static long idOfPostComment;
   
    private static String idOfUser = "9dc26618-de06-4862-b947-799fb551d2a4";
   
    private static String Bearer ="Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICI3WHZWQ2t2anBGYTlCMW1RVUVKLVAyRXYwa1RTMTh1US1zdm5vWEdRSzNnIn0.eyJqdGkiOiJiMjNkNGQ3ZC05ZTYxLTQ4MzgtOTEwZi01YTIzOGFiNGM0NDIiLCJleHAiOjE1OTA3NTc5OTEsIm5iZiI6MCwiaWF0IjoxNTkwNzU3OTMxLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODAvYXV0aC9yZWFsbXMvbWFzdGVyIiwiYXVkIjpbIm1hc3Rlci1yZWFsbSIsImFjY291bnQiXSwic3ViIjoiOWRjMjY2MTgtZGUwNi00ODYyLWI5NDctNzk5ZmI1NTFkMmE0IiwidHlwIjoiQmVhcmVyIiwiYXpwIjoid2ViLXNzbyIsIm5vbmNlIjoiZGJiZmM3MjItNGU4MC00M2VlLTkwMWItOTY2YjU0NGM0NmNmIiwiYXV0aF90aW1lIjoxNTkwNzU3OTMwLCJzZXNzaW9uX3N0YXRlIjoiMjk0MDBmNWYtMTUzMy00NTUxLWJlNjItYTMxOGMzMzczNTNiIiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyIqIl0sInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJjcmVhdGUtcmVhbG0iLCJVc2VyIiwib2ZmbGluZV9hY2Nlc3MiLCJhZG1pbiIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsibWFzdGVyLXJlYWxtIjp7InJvbGVzIjpbInZpZXctcmVhbG0iLCJ2aWV3LWlkZW50aXR5LXByb3ZpZGVycyIsIm1hbmFnZS1pZGVudGl0eS1wcm92aWRlcnMiLCJpbXBlcnNvbmF0aW9uIiwiY3JlYXRlLWNsaWVudCIsIm1hbmFnZS11c2VycyIsInF1ZXJ5LXJlYWxtcyIsInZpZXctYXV0aG9yaXphdGlvbiIsInF1ZXJ5LWNsaWVudHMiLCJxdWVyeS11c2VycyIsIm1hbmFnZS1ldmVudHMiLCJtYW5hZ2UtcmVhbG0iLCJ2aWV3LWV2ZW50cyIsInZpZXctdXNlcnMiLCJ2aWV3LWNsaWVudHMiLCJtYW5hZ2UtYXV0aG9yaXphdGlvbiIsIm1hbmFnZS1jbGllbnRzIiwicXVlcnktZ3JvdXBzIl19LCJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJzY29wZSI6Im9wZW5pZCBwcm9maWxlIGVtYWlsIiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJhZG1pbiJ9.YukZ4nb3kZBRmiwG7lYAI4LJY-ruBA2XXtg2uFWvybi6FIX2HsqMGo79vru2u0Rn8ko46VtsstV056Llop66w7r0hDql1TQm8mmcmktxdDf-JFFYj7e1exbIYXRwuV6ttVAUdahzCAnVM-4JUBTBMrAnSQC1UAB0XveN6l-6IBri-YKSgmn0lofPETiPPzpWDXpYuHDYI9QtoaNKOYhWQGfE1sl5PRsHCsm-qWTr3eoy2OtBkX1yclao2JY27RduS1gl8klSAPOAQDc0wJztRmdK1G79W2pPt0UHx6D34_8zk_mf0PKK5ybZ50bFYOCvIvaNuf3RgQLBJrNovvqplA";
    private static String BearerError ="Bearer eyJthbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICI3WHZWQ2t2anBGYTlCMW1RVUVKLVAyRXYwa1RTMTh1US1zdm5vWEdRSzNnIn0.eyJqdGkiOiJiMjNkNGQ3ZC05ZTYxLTQ4MzgtOTEwZi01YTIzOGFiNGM0NDIiLCJleHAiOjE1OTA3NTc5OTEsIm5iZiI6MCwiaWF0IjoxNTkwNzU3OTMxLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODAvYXV0aC9yZWFsbXMvbWFzdGVyIiwiYXVkIjpbIm1hc3Rlci1yZWFsbSIsImFjY291bnQiXSwic3ViIjoiOWRjMjY2MTgtZGUwNi00ODYyLWI5NDctNzk5ZmI1NTFkMmE0IiwidHlwIjoiQmVhcmVyIiwiYXpwIjoid2ViLXNzbyIsIm5vbmNlIjoiZGJiZmM3MjItNGU4MC00M2VlLTkwMWItOTY2YjU0NGM0NmNmIiwiYXV0aF90aW1lIjoxNTkwNzU3OTMwLCJzZXNzaW9uX3N0YXRlIjoiMjk0MDBmNWYtMTUzMy00NTUxLWJlNjItYTMxOGMzMzczNTNiIiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyIqIl0sInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJjcmVhdGUtcmVhbG0iLCJVc2VyIiwib2ZmbGluZV9hY2Nlc3MiLCJhZG1pbiIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsibWFzdGVyLXJlYWxtIjp7InJvbGVzIjpbInZpZXctcmVhbG0iLCJ2aWV3LWlkZW50aXR5LXByb3ZpZGVycyIsIm1hbmFnZS1pZGVudGl0eS1wcm92aWRlcnMiLCJpbXBlcnNvbmF0aW9uIiwiY3JlYXRlLWNsaWVudCIsIm1hbmFnZS11c2VycyIsInF1ZXJ5LXJlYWxtcyIsInZpZXctYXV0aG9yaXphdGlvbiIsInF1ZXJ5LWNsaWVudHMiLCJxdWVyeS11c2VycyIsIm1hbmFnZS1ldmVudHMiLCJtYW5hZ2UtcmVhbG0iLCJ2aWV3LWV2ZW50cyIsInZpZXctdXNlcnMiLCJ2aWV3LWNsaWVudHMiLCJtYW5hZ2UtYXV0aG9yaXphdGlvbiIsIm1hbmFnZS1jbGllbnRzIiwicXVlcnktZ3JvdXBzIl19LCJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJzY29wZSI6Im9wZW5pZCBwcm9maWxlIGVtYWlsIiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJhZG1pbiJ9.YukZ4nb3kZBRmiwG7lYAI4LJY-ruBA2XXtg2uFWvybi6FIX2HsqMGo79vru2u0Rn8ko46VtsstV056Llop66w7r0hDql1TQm8mmcmktxdDf-JFFYj7e1exbIYXRwuV6ttVAUdahzCAnVM-4JUBTBMrAnSQC1UAB0XveN6l-6IBri-YKSgmn0lofPETiPPzpWDXpYuHDYI9QtoaNKOYhWQGfE1sl5PRsHCsm-qWTr3eoy2OtBkX1yclao2JY27RduS1gl8klSAPOAQDc0wJztRmdK1G79W2pPt0UHx6D34_8zk_mf0PKK5ybZ50bFYOCvIvaNuf3RgQLBJrNovvqplA";  
   
    private static Header header= new Header("Authorization",Bearer);
    private static Header headerError= new Header("Authorization",BearerError);
   
   
 
   
    @Nested
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class TestPost {
 
 
        @Test
        @Order(1)
        void testPostRecipe() {
           
            List<Ingredient> listIng = new ArrayList<Ingredient>();
            Ingredient ingredient1 = createIngredient(50l, (short)10);
            Ingredient ingredient2 = createIngredient(55l, (short)15);
            listIng.add(ingredient1);
            listIng.add(ingredient2);
            Comment comment1 = createComment("bonjour je suis pas content", "asdfakasy","myName", (short)1);
            Comment comment2 = createComment("bonjour je suis content", "lasdfasdf","yourName", (short)5);
            List<Comment> listComment = new ArrayList<Comment>();
            listComment.add(comment1);
            listComment.add(comment2);
            Recipe recipe = createRecipe("maRecette", listIng, (short)5, (short)5, (short)4, "maPhoto", "fais ceci cela",
                    "aprfg","myName", Date.valueOf("2019-01-26"), 4.5f, listComment);
            String id_string = with().contentType(ContentType.JSON).header(header).body(recipe).when().request("POST","/").then().statusCode(200).extract().asString();
            String error = with().contentType(ContentType.JSON).header(headerError).body(recipe).when().request("POST","/").then().statusCode(401).extract().asString();
            assertEquals("There is no header or the token is not valid.",error);
           
            idOfPostRecipe = Long.parseLong(id_string);
           
        }
       
 
        @Test
        @Order(2)
        void testGetRecipeAfterPost() {
            long id = idOfPostRecipe;
            when().get("/" + id).then().assertThat()
            .body("authorId", equalTo(idOfUser))
            .body("authorName", equalTo("myName"))
            .body("name", equalTo("maRecette"))
            .body("picture", equalTo("maPhoto"))
            .body("people", equalTo(4))
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
        void testGetMyRecipes() {
           
            String error = with().contentType(ContentType.JSON).header(headerError).get("/user").then().assertThat().statusCode(401).extract().asString();
 
            assertEquals("There is no header or the token is not valid.",error);
           
            List<Recipe> recipes = with().contentType(ContentType.JSON).header(header).get("/user").then().assertThat().statusCode(200).extract().body().jsonPath().getList(".", Recipe.class);
            Recipe recipe = when().get("/" + idOfPostRecipe).then().assertThat().statusCode(200).extract().as(Recipe.class);
           
            List<Recipe> recipesOfOne = new ArrayList<Recipe>();
            recipesOfOne.add(recipe);
            assertEquals(1,recipes.size());
            assertEquals(recipes.get(0).getPreparation(),recipesOfOne.get(0).getPreparation());
            assertEquals(recipesOfOne.get(0).getAuthorId(),recipes.get(0).getAuthorId());
            assertEquals(recipesOfOne.get(0).getAuthorName(),recipes.get(0).getAuthorName());
            assertEquals(recipesOfOne.get(0).getDifficulty(),recipes.get(0).getDifficulty());
            assertEquals(recipesOfOne.get(0).getPeople(),recipes.get(0).getPeople());
            assertEquals(recipesOfOne.get(0).getName(),recipes.get(0).getName());
            assertEquals(recipesOfOne.get(0).getPicture(),recipes.get(0).getPicture());
            assertEquals(recipesOfOne.get(0).getPreparationTime(),recipes.get(0).getPreparationTime());
            assertEquals(recipesOfOne.get(0).getId(),recipes.get(0).getId());
            assertEquals(recipesOfOne.get(0).getPublicationDate(),recipes.get(0).getPublicationDate());
            assertEquals(recipesOfOne.get(0).getGrade(),recipes.get(0).getGrade());
            assertEquals(recipesOfOne.get(0).getComments().size(),recipes.get(0).getComments().size());
            assertEquals(recipesOfOne.get(0).getIngredients().size(),recipes.get(0).getIngredients().size());
        }
       
       
        @Test
        @Order(4)
        void testPostComment() {
            long id = idOfPostRecipe;
            Comment comment =  createComment("dégueu", "méchant","myBadName",(short) 5);
 
       
            when().get("/"+id).then().body("comments", hasSize(0));
            String error = with().contentType(ContentType.JSON).header(headerError).body(comment).when().request("POST","/"+id+"/comment").then().statusCode(401).extract().asString();
            assertEquals("There is no header or the token is not valid.",error);
            String idComment = with().contentType(ContentType.JSON).header(header).body(comment).when().request("POST","/"+id+"/comment").then().statusCode(200).extract().asString();
            idOfPostComment = Long.parseLong(idComment);
            when().get("/"+id).then().body("comments", hasSize(1));
            when().get("/"+id).then().assertThat()
            .body("comments.userID", hasItems(idOfUser))
            .body("comments.userName", hasItems("myBadName"))
            .body("comments.text", hasItems("dégueu"))
            .body("comments.grade", hasItems(5));
           
        }
       
        @Test
        @Order(5)
        void testGetCommentAfterPost() {
            long id = idOfPostRecipe;
            long idComment = idOfPostComment;
            when().get("/"+id+"/comment/"+idComment).then().assertThat()
            .body("userID", equalTo(idOfUser))
            .body("userName", equalTo("myBadName"))
            .body("text", equalTo("dégueu"))
            .body("grade", equalTo(5));
        }
       
        @Test
        @Order(6)
        void testGetCommentsAfterPost() {
            long id = idOfPostRecipe;
            List<Comment> response = when().get("/"+id+"/comments").then().extract().body().jsonPath().getList(".", Comment.class);
            assertEquals(1,response.size());
            Comment myComment = response.get(0);
            assertEquals(idOfUser,myComment.getUserID());
            assertEquals("myBadName",myComment.getUserName());
            assertEquals("dégueu",myComment.getText());
            assertEquals(5,myComment.getGrade());
        }
       
       
        @Test
        @Order(7)
        void testDeleteComment() {
            long id = idOfPostRecipe;
            long idComment = idOfPostComment;
           
       
            String error = with().contentType(ContentType.JSON).header(headerError).delete("/"+id+"/comment/"+idComment).then().statusCode(401).extract().asString();
            assertEquals("There is no header or the token is not valid.",error);
           
           
            with().get("/1").then().body("comments", hasSize(3));
            String error1 = with().contentType(ContentType.JSON).header(header).delete("/1/comment/1").then().statusCode(403).extract().asString();
            assertEquals("You don't have the rights to call this request.",error1);
            with().get("/1").then().body("comments", hasSize(3));
           
            with().get("/"+id).then().body("comments", hasSize(1));
            with().contentType(ContentType.JSON).header(header).delete("/"+id+"/comment/"+idComment).then().statusCode(200);
            with().get("/"+id).then().body("comments", hasSize(0));
           
        }
       
        @Test
        @Order(8)
        void testDeleteRecipeAfterPost() {
            long id = idOfPostRecipe;
           
            String error1 = with().contentType(ContentType.JSON).header(header).delete("/1").then().assertThat().statusCode(403).extract().asString();
            assertEquals("You don't have the rights to call this request.",error1);
           
           
            String error = with().contentType(ContentType.JSON).header(headerError).delete("/" + id).then().assertThat().statusCode(401).extract().asString();
            assertEquals("There is no header or the token is not valid.",error);
           
            when().get("/" + id).then().assertThat().statusCode(200); //Content a response
            with().contentType(ContentType.JSON).header(header).delete("/" + id).then().assertThat().statusCode(200);
            when().get("/" + id).then().assertThat().statusCode(204); //Doesn't content anything
        }
       
    }
 
   
    @Test
    void testGetById() {
 
        when().get("/2").then().body(containsString("Choux à la crème"));
        Date date = Date.valueOf("2020-07-12");
        when().get("/1").then().assertThat()
        .body("authorId", equalTo("testId"))
        .body("authorName", equalTo("testName"))
        .body("name", equalTo("Tarte aux citrons"))
        .body("picture", equalTo("monImage"))
        .body("people", equalTo(5))
        .body("preparationTime", equalTo(10))
        .body("difficulty", equalTo(5))
        .body("preparation", equalTo("Prépare la tarte"))
        .body("publicationDate", equalTo(date.getTime()))
        .body("grade", equalTo(4.5f))
        .body("ingredients.quantity", hasItems(4,12))
        .body("ingredients.detailsID", hasItems(0,1))
        .body("comments.text", hasItems("Pas mal","Très bon", "Dégeulasse"))
        .body("comments.userID", hasItems("commentateur","AutreCommentateur", "randomGuy"))
        .body("comments.userName", hasItems("commentateurName","AutreCommentateurName", "randomGuyName"))
        .body("comments.grade", hasItems(3,4,1));
    }
   
   
   
    @Test
    void testGetByUserId() {
       
        List<Recipe> response = when().get("/user/autreId").then().extract().body().jsonPath().getList(".", Recipe.class);
       
        assertEquals(1,response.size());
       
        Recipe myRecipe = response.get(0);
       
        assertEquals("Choux à la crème", myRecipe.getName());
        assertEquals("monAutreImage", myRecipe.getPicture());
        assertEquals(2, myRecipe.getPeople());
        assertEquals(15, myRecipe.getPreparationTime());
        assertEquals(3, myRecipe.getDifficulty());
        assertEquals("Prépare le choux", myRecipe.getPreparation());
        assertEquals("autreId", myRecipe.getAuthorId());
        assertEquals("autreName", myRecipe.getAuthorName());
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
        assertEquals("myName", myRecipe.getComments().get(0).getUserName());
       
    }
   
   
    @Test
    void testGetAll() {
 
        List<Recipe> response = when().get("/recipes").then().extract().body().jsonPath().getList(".", Recipe.class);
       
        assertEquals(2,response.size());
       
        Recipe Recipe1 = response.get(0);
        Recipe Recipe2 = response.get(1);
       
        assertEquals("Tarte aux citrons", Recipe1.getName());
        assertEquals("monImage", Recipe1.getPicture());
        assertEquals(5, Recipe1.getPeople());
        assertEquals(10, Recipe1.getPreparationTime());
        assertEquals(5, Recipe1.getDifficulty());
        assertEquals("Prépare la tarte", Recipe1.getPreparation());
        assertEquals("testId", Recipe1.getAuthorId());
        assertEquals("testName", Recipe1.getAuthorName());
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
        assertEquals("randomGuyName", Recipe1.getComments().get(2).getUserName());
       
        assertEquals("Choux à la crème", Recipe2.getName());
        assertEquals("monAutreImage", Recipe2.getPicture());
        assertEquals(2, Recipe2.getPeople());
        assertEquals(15, Recipe2.getPreparationTime());
        assertEquals(3, Recipe2.getDifficulty());
        assertEquals("Prépare le choux", Recipe2.getPreparation());
        assertEquals("autreId", Recipe2.getAuthorId());
        assertEquals("autreName", Recipe2.getAuthorName());
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
        assertEquals("myName", Recipe2.getComments().get(0).getUserName());
    }
 
 
    @Test
    void testSearch() {
   
       
        List<Ingredient> ingredients = new ArrayList<Ingredient>();
        Ingredient ing = createIngredient(20l, (short)10);
        Ingredient ing2 = createIngredient(55l, (short)15);
        ingredients.add(ing);
        ingredients.add(ing2);
        List<Comment> listComment = new ArrayList<Comment>();
       
        Recipe newRecipe = createRecipe("tarte aux fraises bernoise", ingredients, (short)5, (short)5, (short)4, "maPhoto", "fais ceci cela",
                "aprfg", "myName", Date.valueOf("2019-01-26"), 4.5f, listComment);
        newRecipe.setName("tarte aux fraises bernoise");
       
       
        Recipe newRecipe2 = createRecipe("tarte aux fraises suisse", ingredients, (short)6, (short)4, (short)2, "maPhoto", "Prepare bien",
                "moi", "myName", Date.valueOf("2020-03-28"), 4.7f, listComment);
       
        Ingredient ing3 = new Ingredient();
        ing3.setDetailsID(22);
        ing3.setQuantity((short) 4);
        Ingredient ing4 = new Ingredient();
        ing4.setDetailsID(25);
        ing4.setQuantity((short) 50);
       
        List<Ingredient> newIngredients = new ArrayList<>();
        newIngredients.add(ing3);
        newIngredients.add(ing4);
       
        Recipe newRecipe3 = createRecipe("Glace à l'abricot", newIngredients, (short)6, (short)4, (short)2, "maPhoto", "Prepare bien",
                "moi", "myName", Date.valueOf("2020-03-28"), 4.7f, listComment);
       
        List<Ingredient> otherIngredients = new ArrayList<>();
        otherIngredients.add(ing4);
       
        Recipe newRecipe4 = createRecipe("Glace à l'abricot remasterisée", otherIngredients, (short)6, (short)4, (short)2, "maPhoto", "Prepare bien",
                "moi", "myName", Date.valueOf("2020-03-28"), 4.7f, listComment);
     
        String id_string1 = with().contentType(ContentType.JSON).header(header).body(newRecipe).when().request("POST","/").then().statusCode(200).extract().asString();
        long id_1 = Long.parseLong(id_string1);
       
        String id_string2 = with().contentType(ContentType.JSON).header(header).body(newRecipe2).when().request("POST","/").then().statusCode(200).extract().asString();
        long id_2 = Long.parseLong(id_string2);
       
        String id_string3 = with().contentType(ContentType.JSON).header(header).body(newRecipe3).when().request("POST","/").then().statusCode(200).extract().asString();
        long id_3 = Long.parseLong(id_string3);
       
        String id_string4 = with().contentType(ContentType.JSON).header(header).body(newRecipe4).when().request("POST","/").then().statusCode(200).extract().asString();
        long id_4 = Long.parseLong(id_string4);
       
       
        String mySearch1 = "Tartes à la fraises"; //-> 200
        String mySearch2 = "poires aux truffes"; //->204 no content
        String mySearch3 = "Abricots au chocolat noir";
        String mySearch4 = "Glace à l'abricot";
       
 
        List<Recipe> response1 = when().get("/search/" + mySearch1).then().statusCode(200).extract().body().jsonPath().getList(".", Recipe.class);
        assertEquals(2,response1.size());
        assertEquals("tarte aux fraises bernoise",response1.get(0).getName());
        when().get("/search/" + mySearch2).then().statusCode(200); // There is no recipe
        when().get("/search/" + mySearch3).then().statusCode(200); // There is no recipe
        List<Recipe> response2 = when().get("/search/" + mySearch4 + "?id=25&id=22").then().statusCode(200).extract().body().jsonPath().getList(".", Recipe.class);
        assertEquals(2,response2.size());
        assertEquals("Glace à l'abricot",response2.get(0).getName());
        List<Recipe> response3 = when().get("/search/" + mySearch4 + "?id=25").then().statusCode(200).extract().body().jsonPath().getList(".", Recipe.class);
        assertEquals(1,response3.size());
        assertEquals("Glace à l'abricot remasterisée",response3.get(0).getName());
       
        with().contentType(ContentType.JSON).header(header).delete("/" + id_1).then().assertThat().statusCode(200);
        with().contentType(ContentType.JSON).header(header).delete("/" + id_2).then().assertThat().statusCode(200);
        with().contentType(ContentType.JSON).header(header).delete("/" + id_3).then().assertThat().statusCode(200);
        with().contentType(ContentType.JSON).header(header).delete("/" + id_4).then().assertThat().statusCode(200);
   
    }
   
   
 
 
 
    private Comment createComment(String text, String userID, String userName, short grade) {
        Comment comment = new Comment();
        comment.setText(text);
        comment.setUserID(userID);
        comment.setUserName(userName);
        comment.setGrade(grade);
        return comment;
    }
   
    private Ingredient createIngredient(long detailsID, short quantity) {
        Ingredient ingredient = new Ingredient();
        ingredient.setDetailsID(detailsID);
        ingredient.setQuantity(quantity);
       
        return ingredient;
    }
   
    private Recipe createRecipe(String name, List<Ingredient> ingredients, short prepTime, short difficulty, short people,
            String photo, String preparation, String authorId, String authorName, Date date, float note, List<Comment> comments) {
 
        Recipe i = new Recipe();
        i.setName(name);
        i.setIngredients(ingredients);
        i.setPreparationTime(prepTime);
        i.setDifficulty(difficulty);
        i.setPeople(people);
        i.setPicture(photo);
        i.setPreparation(preparation);
        i.setAuthorId(authorId);
        i.setAuthorName(authorName);
        i.setPublicationDate(date);
        i.setGrade(note);
        i.setComments(comments);
 
        return i;
 
    }
   
   
}