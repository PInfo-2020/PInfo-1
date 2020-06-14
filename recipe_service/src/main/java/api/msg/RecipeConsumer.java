package api.msg;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.aerogear.kafka.cdi.annotation.Consumer;
import org.aerogear.kafka.cdi.annotation.KafkaConfig;

import domain.service.RecipeService;



@ApplicationScoped
@KafkaConfig(bootstrapServers = "#{thorntail.kafka-configuration.host}:#{thorntail.kafka-configuration.port}")

public class RecipeConsumer {

	@Inject
	private RecipeService recipeService;

	@Consumer(topics = "returnIngredientName", groupId = "pinfo-microservices")
	public void askedName(String name) {
		if(name != "Nous n'avons pas cet ingredient.") {
			
		}
	}
}

