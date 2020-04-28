package domain.service;
import java.util.ArrayList;
import java.util.Arrays;

import javax.enterprise.context.ApplicationScoped;

import domain.model.Comment;
import domain.model.Ingredient;
import domain.model.Recipe;
import domain.model.Utensil;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.CompoundSelection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.arjuna.ats.internal.jdbc.drivers.modifiers.list;

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
		
		List<Recipe> recipes = recipeService.getAllRecipes();
		recipeService.create(getRandomRecipe());
		recipeService.create(getRandomRecipe());
		Recipe recipe = recipeService.getAllRecipes().get(0);
		
		assertNotNull(recipe);
	}
	
	@Test
	void testCreation() {
		
		int size = recipeService.getAllRecipes().size();
		List<Ingredient> listIng = new ArrayList<Ingredient>();
		Ingredient ingredient1 = recipeService.createIngredient(50l, (short)10);
		Ingredient ingredient2 = recipeService.createIngredient(55l, (short)15);
		listIng.add(ingredient1);
		listIng.add(ingredient2);
		List<Utensil> listUtensil = new ArrayList<Utensil>();
		Utensil utensil1 = recipeService.createUtensil("poele");
		Utensil utensil2 = recipeService.createUtensil("fourchette");
		listUtensil.add(utensil1);
		listUtensil.add(utensil2);
		Comment comment1 = recipeService.createComment("bonjour je suis pas content", 20l, (short)1);
		Comment comment2 = recipeService.createComment("bonjour je suis content", 22l, (short)5);
		List<Comment> listComment = new ArrayList<Comment>();
		listComment.add(comment1);
		listComment.add(comment2);

		recipeService.create(recipeService.createRecipe("maRecette", listIng, listUtensil, (short)5, (short)5, (short)4, "maPhoto", "fais ceci cela",
				42, Date.valueOf("2019-01-26"), "dessert", "suisse", 4.5f, listComment));
		
		List<Recipe> recipes = recipeService.getAllRecipes();
		Recipe recipe = recipes.get(size);
		
		assertEquals("maRecette", recipe.getName());
		assertEquals(listIng, recipe.getIngredients());
		assertEquals(10, recipe.getIngredients().get(0).getQuantity());
		assertEquals(listUtensil, recipe.getUtensils());
		assertEquals("poele", recipe.getUtensils().get(0).getName());
		assertEquals(5, recipe.getPreparationTime());
		assertEquals(5, recipe.getDifficulty());
		assertEquals(4, recipe.getNbPersons());
		assertEquals("maPhoto", recipe.getPicture());
		assertEquals("fais ceci cela", recipe.getPreparation());
		assertEquals(42, recipe.getAuthor());
		assertEquals(Date.valueOf("2019-01-26"), recipe.getPublicationDate());
		assertEquals("dessert", recipe.getPlateCategory());
		assertEquals("suisse", recipe.getKitchenType());
		assertEquals(4.5, recipe.getGrade());
		assertEquals(listComment, recipe.getComments());
		assertEquals(5, recipe.getComments().get(1).getGrade());
	}

	
	private Ingredient getRandomIngredient() {
		Ingredient ingredient = new Ingredient();
		ingredient.setDetailsID((long) (Math.random()*1000));
		ingredient.setQuantity((short) (Math.random()*1000));
		
		return ingredient;
	}
	
	private Utensil getRandomUtensil() {
		Utensil utensil = new Utensil();
		utensil.setName(UUID.randomUUID().toString());
		
		return utensil;
	}
	
	private Comment getRandomComment() {
		Comment comment = new Comment();
		comment.setText(UUID.randomUUID().toString());
		comment.setUserID((long) (Math.random()*1000));
		comment.setGrade((short) (Math.random()*1000));
		
		return comment;
	}
	
	private Recipe getRandomRecipe() {
		
		List<Ingredient> listIng = new ArrayList<Ingredient>();
		Ingredient ingredient1 = getRandomIngredient();
		Ingredient ingredient2 = getRandomIngredient();
		listIng.add(ingredient1);
		listIng.add(ingredient2);
		
		List<Utensil> listUtensil = new ArrayList<Utensil>();
		Utensil utensil1 = getRandomUtensil();
		Utensil utensil2 = getRandomUtensil();
		listUtensil.add(utensil1);
		listUtensil.add(utensil2);
		
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
		i.setAuthor((int) (Math.random()*1000));
		i.setPublicationDate(Date.valueOf("2019-01-26"));
		i.setPlateCategory(UUID.randomUUID().toString());
		i.setKitchenType(UUID.randomUUID().toString());
		i.setGrade((float) (Math.random()*1000));
		i.setComments(listComment);
		i.setUtensils(listUtensil);
		i.setIngredients(listIng);
		
		return i;
	}

}
