package domain.service;
import java.util.ArrayList;
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
import java.util.Date;
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
		recipeService.create(getRandomRecipe());
		Recipe ingredient = recipeService.getAllRecipes().get(0);
		assertNotNull(ingredient);
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
		final java.sql.Date dateSQL = new java.sql.Date(new Date().getTime()) ;
		i.setDatePublication(dateSQL);
		i.setCategoriePlat(UUID.randomUUID().toString());
		i.setTypeCuisine(UUID.randomUUID().toString());
		i.setNote((float) (Math.random()*1000));
		i.setCommentaires((int) (Math.random()*1000));
		
		
		
		return i;
	}
}
