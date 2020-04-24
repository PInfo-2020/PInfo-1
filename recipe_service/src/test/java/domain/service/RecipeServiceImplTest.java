package domain.service;
import java.util.ArrayList;
import java.util.Arrays;

import javax.enterprise.context.ApplicationScoped;

import domain.model.Recipe;

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
		Map<Long, Short> listIng = new HashMap<>();
		listIng.put(10l, (short)1);
		listIng.put(20l, (short)2);
		recipeService.create(recipeService.createRecipe("maRecette", listIng, Arrays.asList("Voici", "mes", "ustensiles"), Arrays.asList("Mes", "tags", "sont", "cools"), (short)5, "difficile", (short)4, "maPhoto", "fais ceci cela",
				42, Date.valueOf("2019-01-26"), "dessert", "suisse", 4.5f, 43));
		List<Recipe> recipes = recipeService.getAllRecipes();
		Recipe recipe = recipes.get(size);
		assertEquals("maRecette", recipe.getNom());
		assertEquals(listIng, recipe.getIngredientsOfRecipe());
		assertEquals(Arrays.asList("Voici", "mes", "ustensiles"), recipe.getUstensiles());
		assertEquals(Arrays.asList("Mes", "tags", "sont", "cools"), recipe.getTags());
		assertEquals(5, recipe.getTempsPreparation());
		assertEquals("difficile", recipe.getDifficulte());
		assertEquals(4, recipe.getNbPersonnes());
		assertEquals("maPhoto", recipe.getPhoto());
		assertEquals("fais ceci cela", recipe.getPreparation());
		assertEquals(42, recipe.getAuteur());
		assertEquals(Date.valueOf("2019-01-26"), recipe.getDatePublication());
		assertEquals("dessert", recipe.getCategoriePlat());
		assertEquals("suisse", recipe.getTypeCuisine());
		assertEquals(4.5, recipe.getNote());
		assertEquals(43, recipe.getCommentaires());
		
	}
	
	private Recipe getRandomRecipe() {
		Recipe i = new Recipe();
		i.setNom(UUID.randomUUID().toString());
		i.setTempsPreparation((short) (Math.random()*1000));
		i.setDifficulte(UUID.randomUUID().toString());
		i.setNbPersonnes((short) (Math.random()*1000));
		i.setPhoto(UUID.randomUUID().toString());
		i.setPreparation(UUID.randomUUID().toString());
		i.setAuteur((int) (Math.random()*1000));
		i.setDatePublication(Date.valueOf("2019-01-26"));
		i.setCategoriePlat(UUID.randomUUID().toString());
		i.setTypeCuisine(UUID.randomUUID().toString());
		i.setNote((float) (Math.random()*1000));
		i.setCommentaires((int) (Math.random()*1000));
		
		return i;
	}
}
