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
import domain.service.RecipeService;

@ExtendWith(MockitoExtension.class)
class IngredientProducerTest {

	@Mock
	private SimpleKafkaProducer<String, Long> kafkaProducer;
	@Mock
	private RecipeService recipeService;

	@InjectMocks
	private RecipeProducer producer;
/*

	@Test
	void testAskName() {
		Long ingredientId = (long) 3;
		producer.askName(ingredientId);
		verify(kafkaProducer, times(1)).send("askIngredientName", ingredientId);
	}*/
}