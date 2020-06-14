package api.msg;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.aerogear.kafka.SimpleKafkaProducer;
import org.aerogear.kafka.cdi.annotation.KafkaConfig;
import org.aerogear.kafka.cdi.annotation.Producer;

import domain.model.Ingredient;
import domain.service.RecipeService;
import lombok.extern.java.Log;

@ApplicationScoped
@KafkaConfig(bootstrapServers = "#{thorntail.kafka-configuration.host}:#{thorntail.kafka-configuration.port}")
@Log
public class RecipeProducer {

	@Producer
	private SimpleKafkaProducer<String, Long> producer;

	
	public void askName(Long ingredientId) {
		log.info("Send the state of an ingredient to the topic with id " + ingredientId);
		producer.send("askIngredientName", ingredientId);
		
	}
}