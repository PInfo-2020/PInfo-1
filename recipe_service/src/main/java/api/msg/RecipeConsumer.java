package api.msg;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.aerogear.kafka.cdi.annotation.Consumer;
import org.aerogear.kafka.cdi.annotation.KafkaConfig;

import domain.service.RecipeService;



@ApplicationScoped
@KafkaConfig(bootstrapServers = "#{thorntail.kafka-configuration.host}:#{thorntail.kafka-configuration.port}")

public class RecipeConsumer {

	
	@Consumer(topics = "returnIngredientName", groupId = "pinfo-microservices")
	public String askedName(String name) {
		return name;
	}
}

