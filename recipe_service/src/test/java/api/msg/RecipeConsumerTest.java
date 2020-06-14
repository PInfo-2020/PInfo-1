
package api.msg;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.mockito.Mockito.times;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import domain.model.Recipe;
import domain.service.RecipeService;

@ExtendWith(MockitoExtension.class)
class RecipeConsumerTest {
	
	
	@InjectMocks
	private RecipeService recipeService;
	
	@InjectMocks
	private RecipeConsumer consumer;
	
	@InjectMocks
	private RecipeProducer producer;
	/*
	@Test
	void testAskedName() {
		String name = "nom d'un ingredient";
		Long ingredientId = (long) 3;
		producer.askName(ingredientId);
		consumer.askedName(name);
		
		
	}
	*/
	
}