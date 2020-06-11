package domain.service;
import java.util.ArrayList;
import java.util.HashMap;

import domain.model.Comment;
import domain.model.Ingredient;
import domain.model.Recipe;


import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


import java.sql.Date;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import eu.drus.jpa.unit.api.JpaUnit;

@ExtendWith(JpaUnit.class)
@ExtendWith(MockitoExtension.class)
public class RecipeServiceImplTest {
	
	@Spy
	@PersistenceContext(unitName = "RecipePUTest")
	EntityManager em;

	@InjectMocks
	private RecipeServiceImpl recipeService;
	
	
	@Test
	void testGetAll() {
		
		List<Recipe> recipes = recipeService.getAllRecipes();
		int size = recipes.size();
		recipeService.create(getRandomRecipe());
		recipeService.create(getRandomRecipe());
		recipeService.create(getRandomRecipe());
		recipeService.create(getRandomRecipe());
		
		assertEquals(4+size, recipeService.getAllRecipes().size());
	}
	
	@Test
	void testget() {
		
		recipeService.create(getRandomRecipe());
		recipeService.create(getRandomRecipe());
		Recipe recipe = recipeService.getAllRecipes().get(0);
		
		assertNotNull(recipe);
		
		long id = recipe.getId();
		assertEquals(recipeService.get(id), recipe);
	}
	
	@Test
	void testdelete() {
		
		int size = recipeService.getAllRecipes().size();
		recipeService.create(getRandomRecipe());
		assertEquals(size+1, recipeService.getAllRecipes().size());
		Recipe recipe = recipeService.getAllRecipes().get(0);
		long id = recipe.getId();
		recipeService.delete(id);
		
		assertEquals(size, recipeService.getAllRecipes().size());
	}
	
	@Test
	void testCreation() {
		
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
		long returnedId = recipeService.create(createRecipe("maRecette", listIng, (short)5, (short)5, (short)4, "maPhoto", "fais ceci cela",
				"aprfg", Date.valueOf("2019-01-26"), 4.5f, listComment));
		
		List<Recipe> recipes = recipeService.getAllRecipes();
		Recipe recipe = recipes.get(0);
		
		assertEquals("maRecette", recipe.getName());
		assertEquals(returnedId,recipe.getId());
		assertEquals(listIng, recipe.getIngredients());
		assertEquals(10, recipe.getIngredients().get(0).getQuantity());
		assertEquals(ingredient1.getId(), recipe.getIngredients().get(0).getId());
		//assertEquals(recipe, recipe.getIngredients().get(0).getRecipeIng()); //Il y a NULL dans recipeIng ??? 
		assertEquals(5, recipe.getPreparationTime());
		assertEquals(5, recipe.getDifficulty());
		assertEquals(4, recipe.getNbPersons());
		assertEquals("maPhoto", recipe.getPicture());
		assertEquals("fais ceci cela", recipe.getPreparation());
		assertEquals("aprfg", recipe.getAuthor());
		assertEquals(Date.valueOf("2019-01-26"), recipe.getPublicationDate());
		assertEquals(4.5, recipe.getGrade());
		assertEquals(listComment, recipe.getComments());
		assertEquals(5, recipe.getComments().get(1).getGrade());
		assertEquals("lasdfasdf", recipe.getComments().get(1).getUserID());
		//assertEquals(recipe, recipe.getComments().get(1).getRecipe()); //Il y a NULL dans recipe ??? 
	}
	
	
	@Test
	void testgetListRecipebyUserId() {
		int size = recipeService.getListRecipesFromUserId("moi").size();
		Recipe r1 = getRandomRecipe();
		Recipe r2 = getRandomRecipe();
		Recipe r3 = getRandomRecipe();
		Recipe r4 = getRandomRecipe();
		r1.setAuthor("moi");
		r2.setAuthor("moi");
		r3.setAuthor("toi");
		r4.setAuthor("moi");
		r1.setName("canard laqué");
		r2.setName("toutre à la framboise");
		r3.setName("Fondue au chocolat");
		r4.setName("Salade de fruit");
		
		recipeService.create(r1);
		recipeService.create(r2);
		recipeService.create(r3);
		recipeService.create(r4);
		List<Recipe> recipeList = recipeService.getListRecipesFromUserId("moi");
		
		assertEquals("Salade de fruit", recipeList.get(2).getName());
		assertEquals(size+3, recipeService.getListRecipesFromUserId("moi").size());
	}

	
	@Test
	void testAddComment() {
		recipeService.create(getRandomRecipe());
        List<Recipe> recipes = recipeService.getAllRecipes();
        Recipe myRecipe = recipes.get(0);

        int size = myRecipe.getComments().size();
        Comment comment = createComment("bonjour je ne suis pas content", "asdfakasy", (short)2);
        Comment comment2 = createComment("bonjour je ne suis content", "AD<YX", (short)4);
        recipeService.addComment(myRecipe.getId(), comment);
        recipeService.addComment(myRecipe.getId(), comment2);
        assertEquals(size+2,myRecipe.getComments().size());
        assertEquals("bonjour je ne suis pas content", myRecipe.getComments().get(0).getText());
        assertEquals(3,myRecipe.getGrade());

    }
		
	
	
	@Test
	void testDeleteComment() {
		recipeService.create(getRandomRecipe());
        recipeService.create(getRandomRecipe());
        List<Recipe> recipes = recipeService.getAllRecipes();
        Recipe myRecipe = recipes.get(1);


        Comment comment1 = createComment("bonjour c'est moyen", "asdfakasy", (short)3);
        Comment comment2 = createComment("bonjour je suis content", "lasdfasdf", (short)5);
        Comment comment3 = createComment("bonjour je ne suis pas content", "asdfasy", (short)1);

        recipeService.addComment(myRecipe.getId(), comment1);
        recipeService.addComment(myRecipe.getId(), comment2);
        recipeService.addComment(myRecipe.getId(), comment3);
        int size = myRecipe.getComments().size();
        recipeService.deleteComment(myRecipe.getId(), comment3.getId());
        recipeService.deleteComment(myRecipe.getId(), myRecipe.getComments().get(0).getId());
        assertEquals(size-2,myRecipe.getComments().size());
        assertEquals("bonjour je suis content", myRecipe.getComments().get(size-3).getText());
        assertEquals(5,myRecipe.getGrade());
        recipeService.deleteComment(myRecipe.getId(), comment2.getId());
        assertEquals(-1,myRecipe.getGrade());

		
	}
	
	
	@Test
	void testGetComment() {
		recipeService.create(getRandomRecipe());
		recipeService.create(getRandomRecipe());
		List<Recipe> recipes = recipeService.getAllRecipes();
		Recipe myRecipe = recipes.get(1);
		
		Comment comment1 = createComment("bonjour c'est moyen", "asdfakasy", (short)3);
		Comment comment2 = createComment("bonjour je suis content", "lasdfasdf", (short)5);
		Comment comment3 = createComment("bonjour je ne suis pas content", "asdfasy", (short)1);

		recipeService.addComment(myRecipe.getId(), comment1);
		recipeService.addComment(myRecipe.getId(), comment2);
		recipeService.addComment(myRecipe.getId(), comment3);
		Comment myComment = myRecipe.getComments().get(2);
		
		Comment searchComment = recipeService.getComment(myRecipe.getId(), myComment.getId());
		Comment searchComment2 = recipeService.getComment(myRecipe.getId(), 3738204);
		
		assertEquals(myComment, searchComment);
		assertEquals(null, searchComment2);
	}
	
	
	@Test
	void testSearchRecipes() {
		Recipe newRecipe = getRandomRecipe();
		newRecipe.setName("tarte aux fraises bernoise");
		Recipe newRecipe2 = getRandomRecipe();
		newRecipe2.setName("tarte aux fraises suisse");
		recipeService.create(newRecipe);
		recipeService.create(newRecipe2);
		List<Recipe> recipes = recipeService.getAllRecipes();
		int size = recipes.size();
		Recipe myRecipe = recipes.get(size-1);
		
		List<Recipe> foundRecipes1 = recipeService.searchRecipes("Tartes à la fraises");
		List<Recipe> foundRecipes2 = recipeService.searchRecipes("poires aux truffes");
		

		assertEquals(2,foundRecipes1.size());
		assertEquals(myRecipe, foundRecipes1.get(1));
		assertEquals(null, foundRecipes2);
	}
	
	
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
        i.setGrade((float) -1);
        List<Comment> comments = new ArrayList<Comment>();
        i.setComments(comments);
        i.setIngredients(listIng);


        return i;
    }

}