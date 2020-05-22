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
		ing.setDetailsID(42);
		ing.setQuantity((short)2);
		ing.setExpiration(new java.util.Date());
		Ingredient ing2 = new Ingredient();
		ing2.setDetailsID(43);
		ing2.setQuantity((short)5);
		ing2.setExpiration(new java.util.Date());
		List<Ingredient> ings = new ArrayList<Ingredient>();
		ings.add(ing);
		ings.add(ing2);
		Fridge fridge = new Fridge();
		fridge.setIngredients(ings);
		fridge.setUserId("aosédv");
		fridgeService.create(fridge);
		assertEquals(2, fridge.getIngredients().get(0).getQuantity());
		Ingredient newIng = new Ingredient();
		newIng.setDetailsID(42);
		newIng.setQuantity((short)12);
		newIng.setExpiration(new java.util.Date());
		fridgeService.updateIngredient(fridge.getId(), newIng);
		assertEquals(12, fridge.getIngredients().get(0).getQuantity());
	}
	
	@Test
	void testAddQuantity() {
		Ingredient ing = new Ingredient();
		ing.setDetailsID(42);
		ing.setQuantity((short)2);
		ing.setExpiration(new java.util.Date());
		Ingredient ing2 = new Ingredient();
		ing2.setDetailsID(43);
		ing2.setQuantity((short)5);
		ing2.setExpiration(new java.util.Date());
		List<Ingredient> ings = new ArrayList<Ingredient>();
		ings.add(ing);
		ings.add(ing2);
		Fridge fridge = new Fridge();
		fridge.setIngredients(ings);
		fridge.setUserId("aosédv");
		fridgeService.create(fridge);
		assertEquals(2, fridge.getIngredients().get(0).getQuantity());
		fridgeService.addQuantity(fridge.getId(), ing.getDetailsID(), (short)3);
		assertEquals(5, fridge.getIngredients().get(0).getQuantity());
		assertEquals(2, fridge.getIngredients().size());
		fridgeService.addQuantity(fridge.getId(), 44, (short)3);
		assertEquals(3, fridge.getIngredients().size());
	}
	
	
	@Test
	void testRemoveQuantity() {
		Ingredient ing = new Ingredient();
		ing.setDetailsID(42);
		ing.setQuantity((short)2);
		ing.setExpiration(new java.util.Date());
		Ingredient ing2 = new Ingredient();
		ing2.setDetailsID(43);
		ing2.setQuantity((short)5);
		ing2.setExpiration(new java.util.Date());
		List<Ingredient> ings = new ArrayList<Ingredient>();
		ings.add(ing);
		ings.add(ing2);
		Fridge fridge = new Fridge();
		fridge.setIngredients(ings);
		fridge.setUserId("aosédv");
		fridgeService.create(fridge);
		assertEquals(2, fridge.getIngredients().get(0).getQuantity());
		fridgeService.removeQuantity(fridge.getId(), ing.getDetailsID(), (short)1);
		assertEquals(1, fridge.getIngredients().get(0).getQuantity());
		assertEquals(2, fridge.getIngredients().size());
		fridgeService.removeQuantity(fridge.getId(), ing.getDetailsID(), (short)3);
		assertEquals(1, fridge.getIngredients().size());
	}
	
	@Test
	void testAddIngredient() {
		Ingredient ing = new Ingredient();
		ing.setDetailsID(42);
		ing.setQuantity((short)2);
		ing.setExpiration(new java.util.Date());
		Ingredient ing2 = new Ingredient();
		ing2.setDetailsID(43);
		ing2.setQuantity((short)5);
		ing2.setExpiration(new java.util.Date());
		List<Ingredient> ings = new ArrayList<Ingredient>();
		ings.add(ing);
		Fridge fridge = new Fridge();
		fridge.setIngredients(ings);
		fridge.setUserId("aosédv");
		fridgeService.create(fridge);
		assertEquals(1, fridge.getIngredients().size());
		fridgeService.addIngredient(fridge.getId(), ing2);
		assertEquals(2, fridge.getIngredients().size());
	}
	
	@Test
	void testDeleteIngredient() {
		Ingredient ing = new Ingredient();
		ing.setDetailsID(42);
		ing.setQuantity((short)2);
		ing.setExpiration(new java.util.Date());
		Ingredient ing2 = new Ingredient();
		ing2.setDetailsID(43);
		ing2.setQuantity((short)5);
		ing2.setExpiration(new java.util.Date());
		List<Ingredient> ings = new ArrayList<Ingredient>();
		ings.add(ing);
		ings.add(ing2);
		Fridge fridge = new Fridge();
		fridge.setIngredients(ings);
		fridge.setUserId("aosédv");
		fridgeService.create(fridge);
		assertEquals(2, fridge.getIngredients().size());
		fridgeService.deleteIngredient(fridge.getId(), 42);
		assertEquals(1, fridge.getIngredients().size());
	}
	
	@Test
	void testUpdateFridge() {
		Ingredient ing = new Ingredient();
		ing.setDetailsID(42);
		ing.setQuantity((short)2);
		ing.setExpiration(new java.util.Date());
		List<Ingredient> ings = new ArrayList<Ingredient>();
		ings.add(ing);
		Fridge fridge = new Fridge();
		fridge.setIngredients(ings);
		fridge.setUserId("aosédv");
		fridgeService.create(fridge);
		assertEquals(1, fridgeService.getAllIngredient(fridge.getId()).size());
		fridgeService.updateFridge(fridge.getId(), createListIngredients());
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
	private List<Ingredient> createListIngredients(){
		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		Ingredient ing1 = new Ingredient();
		ing1.setDetailsID(123);
		ing1.setQuantity((short)12);
		ing1.setExpiration(new java.util.Date());
		Ingredient ing2 = new Ingredient();
		ing2.setDetailsID(124);
		ing2.setQuantity((short)2);
		ing2.setExpiration(new java.util.Date());
		ingredients.add(ing1);
		ingredients.add(ing2);
		return ingredients;
	}
	private Fridge createFridge() {
		Fridge fridge = new Fridge();
		fridge.setIngredients(createListIngredients());
		fridge.setUserId("asfasydd");
		return fridge;
		
	}
}
