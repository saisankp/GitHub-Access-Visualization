package accessGitHubAPI;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.eclipse.egit.github.core.client.GitHubClient;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import io.github.cdimascio.dotenv.Dotenv;


class MongoDBTest {

	private static Dotenv dotenv;
	protected static MongoDB mongoDB;
	protected static GitHubClient GHclient;
	
	@BeforeAll
	static void disableLoggingWarning() {
		LoggerContext loggerContext = (LoggerContext)LoggerFactory.getILoggerFactory();
		loggerContext.getLogger("org.mongodb.driver").setLevel(Level.ERROR);
	}
	
	@BeforeAll
	static void setupDB() {
		dotenv = Dotenv.load();
		mongoDB = new MongoDB(dotenv.get("MONGO_USERNAME"), dotenv.get("MONGO_PASSWORD"), dotenv.get("MONGO_CLUSTER_URL"), dotenv.get("DATABASE_TEST"), dotenv.get("COLLECTION_TEST"));
	}
	
	@BeforeAll
	static void setupGHClient() {
		dotenv = Dotenv.load();
		GHclient = new GitHubClient();
		GHclient.setCredentials(dotenv.get("GITHUB_USERNAME"), dotenv.get("GITHUB_PASSWORD"));
		GHclient.setOAuth2Token(dotenv.get("OAUTH_TOKEN"));
	}
	
	@Test
	void clearMongoDBTest() {
		mongoDB.clearCollection();
		assertEquals(0, mongoDB.getCollection().countDocuments());
	}
	
	@Test
	void getAndStoreUserRepositoryInfoTest() {
		mongoDB.clearCollection();
		try {
			mongoDB.getAndStoreUserRepositoryInfo(GHclient, dotenv.get("GITHUB_USERNAME"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void getAndStoreUserInfoTest() {
		mongoDB.clearCollection();
		try {
			mongoDB.getAndStoreUserInfo(GHclient, dotenv.get("GITHUB_USERNAME"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
