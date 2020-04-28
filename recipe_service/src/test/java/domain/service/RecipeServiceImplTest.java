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
		List<Ingredient> listIng = null;
		Ingredient ingredient1 = recipeService.createIngredient((short)10, 50l);
		Ingredient ingredient2 = recipeService.createIngredient((short)15, 55l);
		listIng.add(ingredient1);
		listIng.add(ingredient2);
		List<Utensil> listUtensil = null;
		Utensil utensil1 = recipeService.createUtensil("poele");
		Utensil utensil2 = recipeService.createUtensil("fourchette");
		listUtensil.add(utensil1);
		listUtensil.add(utensil2);
		Comment comment1 = recipeService.createComment("bonjour je suis pas content", 20l, (short)1);
		Comment comment2 = recipeService.createComment("bonjour je suis content", 22l, (short)5);
		List<Comment> listComment = null;
		listComment.add(comment1);
		listComment.add(comment1);

		recipeService.create(recipeService.createRecipe("maRecette", listIng, listUtensil, (short)5, "difficile", (short)4, "maPhoto", "fais ceci cela",
				42, Date.valueOf("2019-01-26"), "dessert", "suisse", 4.5f, listComment));
		List<Recipe> recipes = recipeService.getAllRecipes();
		Recipe recipe = recipes.get(size);
		assertEquals("maRecette", recipe.getName());
		assertEquals(listIng, recipe.getIngredients());
		assertEquals(Arrays.asList("Voici", "mes", "ustensiles"), recipe.getUtensils());
		assertEquals(5, recipe.getPreparationTime());
		assertEquals("difficile", recipe.getDifficulty());
		assertEquals(4, recipe.getNbPersonnes());
		assertEquals("maPhoto", recipe.getPhoto());
		assertEquals("fais ceci cela", recipe.getPreparation());
		assertEquals(42, recipe.getAuteur());
		assertEquals(Date.valueOf("2019-01-26"), recipe.getDatePublication());
		assertEquals("dessert", recipe.getCategoriePlat());
		assertEquals("suisse", recipe.getTypeCuisine());
		assertEquals(4.5, recipe.getNote());
		assertEquals(43, recipe.getComments());
		
	}
	
	private Recipe getRandomRecipe() {
		List<Ingredient> listIng = null;
		Ingredient ingredient1 = recipeService.createIngredient((short)10, 50l);
		Ingredient ingredient2 = recipeService.createIngredient((short)15, 55l);
		listIng.add(ingredient1);
		listIng.add(ingredient2);
		List<Utensil> listUtensil = null;
		Utensil utensil1 = recipeService.createUtensil("poele");
		Utensil utensil2 = recipeService.createUtensil("fourchette");
		listUtensil.add(utensil1);
		listUtensil.add(utensil2);
		Comment comment1 = recipeService.createComment("bonjour je suis pas content", 20l, (short)1);
		Comment comment2 = recipeService.createComment("bonjour je suis content", 22l, (short)5);
		List<Comment> listComment = null;
		listComment.add(comment1);
		listComment.add(comment1);
		Recipe i = new Recipe();
		i.setName(UUID.randomUUID().toString());
		i.setPreparationTime((short) (Math.random()*1000));
		i.setDifficulty(UUID.randomUUID().toString());
		i.setNbPersonnes((short) (Math.random()*1000));
		i.setPhoto(UUID.randomUUID().toString());
		i.setPreparation(UUID.randomUUID().toString());
		i.setAuteur((int) (Math.random()*1000));
		i.setDatePublication(Date.valueOf("2019-01-26"));
		i.setCategoriePlat(UUID.randomUUID().toString());
		i.setTypeCuisine(UUID.randomUUID().toString());
		i.setNote((float) (Math.random()*1000));
		i.setComments(listComment);
		i.setUtensils(listUtensil);
		i.setIngredients(listIng);
		
		return i;
	}
}
