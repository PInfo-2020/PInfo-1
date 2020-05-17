package api.rest;


import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.containsString;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import domain.model.Ingredient;
import domain.service.IngredientServiceImpl;
import eu.drus.jpa.unit.api.JpaUnit;
import io.restassured.RestAssured;

@ExtendWith(JpaUnit.class)
@ExtendWith(MockitoExtension.class)
public class IngredientRestServiceIT {

	@BeforeAll
	public static void setup() {
		RestAssured.baseURI = "http://localhost:28080/ingredients";
		RestAssured.port = 8080;
	}
	
	@Spy
	@PersistenceContext(unitName = "IngredientPUTest")
	EntityManager em;
	
	@InjectMocks
	private IngredientServiceImpl ingredientService;

	@Test
	public void testGetMinInfos() {
		when().get("/minInfos").then().body(containsString("Abricot, sucré, conserve"));
	}
	
	@Test
	public void testGetIngredients() {
		Ingredient i = createIngredient("patate", 12, "g", "légume");
		ingredientService.create(i);
		when().get("/").then().body(containsString("patate"));
	}

	private Ingredient createIngredient(String nom, int poids, String unite, String categorie) {
	Ingredient i = new Ingredient();
	i.setCategory(categorie);
	i.setAverageWeight(poids);
	i.setUnity(unite);
	i.setName(nom);
	return i;
}
	
	
}