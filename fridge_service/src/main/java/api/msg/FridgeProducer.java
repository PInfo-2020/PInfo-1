package api.msg;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.aerogear.kafka.SimpleKafkaProducer;
import org.aerogear.kafka.cdi.annotation.KafkaConfig;
import org.aerogear.kafka.cdi.annotation.Producer;

import domain.model.Fridge;
import domain.model.Ingredient;
import domain.service.FridgeService;
import lombok.extern.java.Log;

@ApplicationScoped
@KafkaConfig(bootstrapServers = "#{thorntail.kafka-configuration.host}:#{thorntail.kafka-configuration.port}")
@Log
public class FridgeProducer {

	@Producer
	private SimpleKafkaProducer<String, List<Ingredient>> producer;

	@Inject
	private FridgeService FridgeService;

	public void sendIngredients(String userId) {
		log.info("Send the ingredients of the fridge of user with id " + userId);
		Fridge fridge = FridgeService.getByUserId(userId);
		if(fridge!= null) {
			List<Ingredient> ingredients = fridge.getIngredients();
			if (ingredients != null) {
				producer.send("ingredients", ingredients);	
			}
		}
	}
	
}