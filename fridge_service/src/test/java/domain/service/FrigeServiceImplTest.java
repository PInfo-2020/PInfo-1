package domain.service;
import java.util.ArrayList;
import java.util.Arrays;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
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

import domain.model.Fridge;
import domain.model.Ingredient;
import eu.drus.jpa.unit.api.JpaUnit;

@ExtendWith(JpaUnit.class)
@ExtendWith(MockitoExtension.class)
public class FrigeServiceImplTest {
	
	@Spy
	@PersistenceContext(unitName = "FridgePUTest")
	EntityManager em;

	@InjectMocks
	private FridgeServiceImpl fridgeService;
	

	@Test
	void testCreation() {
		
	}
	@Test
	void testGetAllIngredient() {
		List<Fridge> fridge = fridgeService.getAll();
		int size = fridge.size();
		Fridge test = createFridge();
		fridgeService.create(test);
		fridge = fridgeService.getAll();
		assertEquals(size+1, fridge.size());
		List<Ingredient> ing = fridgeService.getAllIngredient(test.getId());
		assertNotNull(ing);
		assertEquals(2, ing.size());
	}
	@Test
	void testGetAll() {
		List<Fridge> fridge = fridgeService.getAll();
		int size = fridge.size();
		fridgeService.create(createFridge());
		fridgeService.create(createFridge());
		fridgeService.create(createFridge());
		fridgeService.create(createFridge());
		assertEquals(size+4, fridgeService.getAll().size());
	}
	@Test
	void testUpdateIngredient() {
		Ingredient ing = new Ingredient();
		ing.setDetail("test");
		ing.setExpiration(new java.util.Date());
		ing.setName("abricot");
		List<Ingredient> ings   = new ArrayList<Ingredient>();
		ings.add(ing);
		Fridge fridge = new Fridge();
		fridge.setIngredients(ings);
		fridge.setUser(1);
		fridge.setName("frigo1");
		fridgeService.create(fridge);
		assertEquals(1, fridgeService.getAllIngredient(fridge.getId()).size());
		fridgeService.updateIngredient(fridge.getId(), createIngredient());
		assertEquals(2, fridgeService.getAllIngredient(fridge.getId()).size());
	}
	@Test
	void testDelete() {
		List<Fridge> fridge = fridgeService.getAll();
		int size = fridge.size();
		fridgeService.create(createFridge());
		fridgeService.create(createFridge());
		Fridge test = createFridge();
		fridgeService.create(test);
		fridgeService.create(createFridge());
		fridgeService.create(createFridge());
		assertEquals(size+5, fridgeService.getAll().size());
		fridgeService.deleteFridge(test.getId());
		assertEquals(size+4, fridgeService.getAll().size());
		
	}
	private List<Ingredient> createIngredient(){
		List<Ingredient> test = new ArrayList<Ingredient>();
		Ingredient ing1 = new Ingredient();
		ing1.setDetail("test");
		ing1.setExpiration(new java.util.Date());
		ing1.setName("abricot");
		Ingredient ing2 = new Ingredient();
		ing2.setDetail("test2");
		ing2.setExpiration(new java.util.Date());
		ing2.setName("abricot2");
		test.add(ing1);
		test.add(ing2);
		return test;
	}
	private Fridge createFridge() {
		Fridge fridge = new Fridge();
		fridge.setIngredients(createIngredient());
		fridge.setUser(1);
		fridge.setName("frigo1");
		return fridge;
		
	}
}
