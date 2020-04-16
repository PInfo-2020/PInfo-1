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

import domain.model.Ingredient;
import eu.drus.jpa.unit.api.JpaUnit;

@ExtendWith(JpaUnit.class)
@ExtendWith(MockitoExtension.class)
public class IngredientServiceImplTest {
	@Spy
	@PersistenceContext(unitName = "InstrumentPUTest")
	EntityManager em;

	@InjectMocks
	private IngredientServiceImpl ingredientService;
	
	@Test
	void testGet() {
		assertEquals(1, 1);
	}
	
	@Test
	void testGet2() {
		Ingredient ingredient = ingredientService.getAll().get(0);
		assertNotNull(ingredient);
		Long id = ingredient.getId();
		Ingredient getIngredient = ingredientService.get(id);
		assertEquals(1, ingredient);
	}
}
