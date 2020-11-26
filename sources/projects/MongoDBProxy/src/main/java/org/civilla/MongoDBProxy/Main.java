package org.civilla.MongoDBProxy;

import com.mongodb.client.MongoDatabase;
import org.civilla.common.Logging;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;

@SpringBootApplication
public class Main {
	public static void main(String[] args) {
		MongoDatabase database = MongoSingleSession.getDatabase();

		ArrayList<String> collections = new ArrayList<>();
		for (String c : database.listCollectionNames()) {
			collections.add(c);
		}

		for (String c : new String[]{"Users", "BotSessions", "Cameras", "Dominators"})
			if (!collections.contains(c)) {
				database.createCollection(c);
				Logging.log.info(String.join(" ","Created collection", c));
			}
		SpringApplication.run(Main.class, args);
	}
}
