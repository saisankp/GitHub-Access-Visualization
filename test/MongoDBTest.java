import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.eclipse.egit.github.core.client.GitHubClient;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.github.cdimascio.dotenv.Dotenv;

class MongoDBTest {

	private static Dotenv dotenv;
	protected static MongoDB mongoDB;
	protected static GitHubClient GHclient;
	
	@BeforeAll
	static void setupDB() {
		dotenv = Dotenv.load();
		mongoDB = new MongoDB(dotenv.get("MONGO_USERNAME"), dotenv.get("MONGO_PASSWORD"), "test", "test");
	}
	
	@BeforeAll
	static void setupGHClient() {
		GHclient = new GitHubClient();
		GHclient.setCredentials(dotenv.get("GITHUB_USERNAME"), dotenv.get("GITHUB_PASSWORD"));
	}
	
	@Test
	void clearMongoDBTest() {
		mongoDB.clearCollection();
		assertEquals(0, mongoDB.getCollection().countDocuments());
	}
	
	@Test
	void getAndStoreUserRepositoryInfoTest() {
		try {
			mongoDB.getAndStoreUserRepositoryInfo(GHclient, dotenv.get("GITHUB_USERNAME"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void getAndStoreUserInfoTest() {
		try {
			mongoDB.getAndStoreUserInfo(GHclient, dotenv.get("GITHUB_USERNAME"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
