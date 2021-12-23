package accessGitHubAPI;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.bson.Document;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.github.cdimascio.dotenv.Dotenv;


class AccessGitHubAPITest {
	
	private static Dotenv dotenv;

	@BeforeAll
	static void dotEnvSetUpTest() {
		assertDoesNotThrow(() -> dotenv = Dotenv.load());
	}
	
	@Test
	void mongoDBConnectionTest() {
		MongoDB mongodb =  new MongoDB(dotenv.get("MONGO_USERNAME"), dotenv.get("MONGO_PASSWORD"), dotenv.get("MONGO_CLUSTER_URL"), dotenv.get("DATABASE_TEST"), dotenv.get("COLLECTION_TEST"));
		assertDoesNotThrow(() -> mongodb.clearCollection());
		Document mongoDocument = new Document("_id", 1);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy");  
	    LocalDateTime now = LocalDateTime.now();   
		mongoDocument.append("The last time java tests were run was: ", dtf.format(now));
		assertDoesNotThrow(() -> mongodb.insertDocument(mongoDocument));
	}

}
