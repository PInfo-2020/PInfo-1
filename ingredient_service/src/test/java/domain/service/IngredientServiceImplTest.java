package domain.service;


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
	
	//@Test
	//void testSize() {
	//	List<Ingredient> ingredients = ingredientService.getAllIngredients();
	//	int size = ingredients.size();
	//	assertEquals(7, size);
	//}
	
	@Test
	void testgetAllBaseInfo() {
		ingredientService.create(createIngredient("patate", 12, "g", "légume"));
		ingredientService.create(createIngredient("carrote", 120, "g", "légume"));
		ingredientService.create(createIngredient("pâtes", 0, "g", "feculent"));
		ingredientService.create(createIngredient("riz", 0, "g", "feculent"));
		ArrayList<ArrayList<Object>> tab = ingredientService.getAllBaseInfo();
		assertEquals("pâtes", tab.get(2).get(1));
	}

		
	private Ingredient getRandomIngredient() {
		Ingredient i = new Ingredient();
		i.setCategorie(UUID.randomUUID().toString());
		i.setPoid_moyen((int) (Math.random()*1000));
		i.setUnite(UUID.randomUUID().toString());
		i.setNom(UUID.randomUUID().toString());
		return i;
	}
	
	private Ingredient createIngredient(String nom, int poids, String unite, String categorie) {
		Ingredient i = new Ingredient();
		i.setCategorie(categorie);
		i.setPoid_moyen(poids);
		i.setUnite(unite);
		i.setNom(nom);
		return i;
	}
}
