package api.msg;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.aerogear.kafka.SimpleKafkaProducer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Random;

import domain.model.Ingredient;
import domain.model.Fridge;
import domain.service.FridgeService;

@ExtendWith(MockitoExtension.class)
public class FridgeProducerTest {

	@Mock
	private SimpleKafkaProducer<String, List<Ingredient>> kafkaProducer;
	@Mock
	private FridgeService fridgeService;

	@InjectMocks
	private FridgeProducer producer;
	
	@Test
	void testSendIngredients() {
		Fridge fridge = getRandomFridge();
		when(fridgeService.getByUserId(fridge.getUserId())).thenReturn(fridge);
		producer.sendIngredients(fridge.getUserId());
		verify(kafkaProducer, times(1)).send("ingredients", fridge.getIngredients());
	}
	
	private Fridge getRandomFridge() {
		Fridge fridge = new Fridge();
		fridge.setIngredients(getRandomIngredients());
		fridge.setUserId(UUID.randomUUID().toString());
		return fridge;
	}
	
	private List<Ingredient> getRandomIngredients() {
		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		Ingredient ing1 = new Ingredient();
		ing1.setDetailsID((long) Math.random()*1000);
		ing1.setExpiration(Date.valueOf("2020-10-26"));
		Random rand = new Random();
		ing1.setQuantity((short) rand.nextInt(1 << 15));
		return ingredients;
	}
	
}
