package api.msg;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.aerogear.kafka.cdi.annotation.Consumer;
import org.aerogear.kafka.cdi.annotation.KafkaConfig;

import domain.model.Ingredient;
import domain.service.IngredientService;




@ApplicationScoped
@KafkaConfig(bootstrapServers = "#{thorntail.kafka-configuration.host}:#{thorntail.kafka-configuration.port}")

public class IngredientConsumer {

	@Inject
	private IngredientProducer producer;
	
	@Inject
	private IngredientService ingredientService;
	

	@Consumer(topics = "askIngredientName", groupId = "pinfo-microservices")
	public void askedName(Long ingredientId) {
		
		Ingredient ingredient = ingredientService.get(ingredientId);
		if (ingredient != null) {
			producer.sendName(ingredient.getName());
		}else {
			producer.sendError();
			}
		
	}
	
}

