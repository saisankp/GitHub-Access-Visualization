package accessGitHubAPI;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.bson.Document;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import io.github.cdimascio.dotenv.Dotenv;


class AccessGitHubAPITest {
	
	private static Dotenv dotenv;
	
	@BeforeAll
	static void disableLoggingWarning() {
		LoggerContext loggerContext = (LoggerContext)LoggerFactory.getILoggerFactory();
		loggerContext.getLogger("org.mongodb.driver").setLevel(Level.ERROR);
	}
	
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
