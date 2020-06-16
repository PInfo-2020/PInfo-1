package api.msg;

import javax.enterprise.context.ApplicationScoped;


import org.aerogear.kafka.SimpleKafkaProducer;
import org.aerogear.kafka.cdi.annotation.KafkaConfig;
import org.aerogear.kafka.cdi.annotation.Producer;


@ApplicationScoped
@KafkaConfig(bootstrapServers = "#{thorntail.kafka-configuration.host}:#{thorntail.kafka-configuration.port}")
public class IngredientProducer {

	@Producer
	private SimpleKafkaProducer<String, String> producer;

	
	public void sendName(String name) {
			producer.send("returnIngredientName", name);
		
	}
	
	public void sendError() {
		String error = "Nous n'avons pas cet ingredient.";
		producer.send("returnIngredientName", error);
	}
}