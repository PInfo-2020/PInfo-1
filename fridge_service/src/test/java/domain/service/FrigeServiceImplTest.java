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
	void testget() {
		
		fridgeService.create(createFridge());
		Ingredient ing = new Ingredient();
		ing.setDetailsID(42);
		ing.setQuantity((short)2);
		ing.setExpiration(Date.valueOf("2020-10-26"));
		List<Ingredient> ings = new ArrayList<Ingredient>();
		ings.add(ing);
		Fridge fridge = new Fridge();
		fridge.setIngredients(ings);
		fridge.setUserId("aosédv");
		fridgeService.create(fridge);
		Fridge fridge1 = fridgeService.getAll().get(1);
		
		assertNotNull(fridge1);
		
		long id = fridge1.getId();
		assertEquals(fridgeService.get(id), fridge1);
		assertEquals(fridgeService.get(id).getId(),fridge1.getId());
		assertEquals("aosédv", fridge1.getUserId());
		assertEquals(42, fridge1.getIngredients().get(0).getDetailsID());
		assertEquals(2, fridge1.getIngredients().get(0).getQuantity());
		assertEquals(ing.getId(), fridge1.getIngredients().get(0).getId());
		assertEquals(Date.valueOf("2020-10-26"), fridge1.getIngredients().get(0).getExpiration());
		//assertEquals(fridge1, fridge1.getIngredients().get(0).getFridge()); //Il y a NULL dans fridge ??? 
	}

	@Test
	void testGetByUserId() {
		Ingredient ing = new Ingredient();
		ing.setDetailsID(42);
		ing.setQuantity((short)2);
		ing.setExpiration(Date.valueOf("2020-10-26"));
		List<Ingredient> ings = new ArrayList<Ingredient>();
		ings.add(ing);
		Fridge fridge = new Fridge();
		fridge.setIngredients(ings);
		fridge.setUserId("aosédv");
		
		fridgeService.create(fridge);
		fridgeService.create(createFridge());
		
		assertEquals(fridge, fridgeService.getByUserId("aosédv"));
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
	void testUpdateFridge() {

		Fridge fridge = createFridge();

		fridgeService.create(fridge);
		Fridge fridge2 = new Fridge();
		Ingredient ing = new Ingredient();
		ing.setDetailsID(42);
		ing.setQuantity((short)2);
		ing.setExpiration(Date.valueOf("2020-10-26"));
		List<Ingredient> ings = new ArrayList<Ingredient>();
		ings.add(ing);
		fridge2.setIngredients(ings);
		fridge2.setUserId(fridge.getUserId());
		
		assertEquals(2, fridge.getIngredients().size());
		fridgeService.updateFridge(fridge2);
		assertEquals(1, fridge.getIngredients().size());
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
		ing1.setExpiration(Date.valueOf("2020-10-26"));
		Ingredient ing2 = new Ingredient();
		ing2.setDetailsID(124);
		ing2.setQuantity((short)2);
		ing2.setExpiration(Date.valueOf("2020-10-26"));
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
