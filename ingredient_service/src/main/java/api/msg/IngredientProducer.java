package api.msg;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.aerogear.kafka.SimpleKafkaProducer;
import org.aerogear.kafka.cdi.annotation.KafkaConfig;
import org.aerogear.kafka.cdi.annotation.Producer;

import domain.model.Ingredient;
import domain.service.IngredientService;
import lombok.extern.java.Log;

@ApplicationScoped
@KafkaConfig(bootstrapServers = "#{thorntail.kafka-configuration.host}:#{thorntail.kafka-configuration.port}")
@Log
public class IngredientProducer {

	@Producer
	private SimpleKafkaProducer<String, String> producer;

	@Inject
	private IngredientService ingredientService;
	
	public void send(Long ingredientId) {
		log.info("Send the state of an ingredient to the topic with id " + ingredientId);
		Ingredient ingredient = ingredientService.get(ingredientId);
		if (ingredient != null) {
			producer.send("ingredientName", ingredient.getName());
		}
	}
}