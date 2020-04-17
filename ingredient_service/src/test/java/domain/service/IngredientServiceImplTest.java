package domain.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
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

import domain.model.Ingredient;
import eu.drus.jpa.unit.api.JpaUnit;

@ExtendWith(JpaUnit.class)
@ExtendWith(MockitoExtension.class)
public class IngredientServiceImplTest {
	@Spy
	@PersistenceContext(unitName = "IngredientPUTest")
	EntityManager em;

	@InjectMocks
	private IngredientServiceImpl ingredientService;
	
	@Test
	void testGetAll() {
		List<Ingredient> ingredients = ingredientService.getAllIngredients();
		int size = ingredients.size();
		ingredientService.create(getRandomIngredient());
		ingredientService.create(getRandomIngredient());
		ingredientService.create(getRandomIngredient());
		ingredientService.create(getRandomIngredient());
		assertEquals(4+size, ingredientService.getAllIngredients().size());
		
	}
	
	@Test
	void testget() {
		ingredientService.create(getRandomIngredient());
		Ingredient ingredient = ingredientService.getAllIngredients().get(0);
		assertNotNull(ingredient);
	}

		
	private Ingredient getRandomIngredient() {
		Ingredient i = new Ingredient();
		i.setCategorie(UUID.randomUUID().toString());
		i.setPoid_moyen((int) (Math.random()*1000));
		i.setUnite(UUID.randomUUID().toString());
		i.setNom(UUID.randomUUID().toString());
		return i;
	}
}
