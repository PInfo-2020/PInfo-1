package api.msg;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.aerogear.kafka.SimpleKafkaProducer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import domain.model.Ingredient;
import domain.service.IngredientService;

@ExtendWith(MockitoExtension.class)
class IngredientProducerTest {

	@Mock
	private SimpleKafkaProducer<String, String> kafkaProducer;
	@Mock
	private IngredientService ingredientService;

	@InjectMocks
	private IngredientProducer producer;


	@Test
	void testSendLong() {
		Ingredient ingredient = getRandomIngredient();
		System.out.println("TEST testSendLong : " + ingredient);
		when(ingredientService.get(ingredient.getId())).thenReturn(ingredient);
		producer.send(ingredient.getId());
		verify(kafkaProducer, times(1)).send("ingredientName", ingredient.getName());
	}

	@Test
	void testSendLongNull() {
		Ingredient ingredient = getRandomIngredient();
		when(ingredientService.get(ingredient.getId())).thenReturn(null);
		producer.send(ingredient.getId());
		verify(kafkaProducer, times(0)).send("ingredient", ingredient.getName());
	}


	private Ingredient getRandomIngredient() {
		Ingredient ing = new Ingredient();
		ing.setName("ingredient test0");
		return ing;
	}
	
}