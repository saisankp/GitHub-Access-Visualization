package accessGitHubAPI;
import java.io.IOException;
import java.util.Scanner;

import org.eclipse.egit.github.core.client.GitHubClient;

import io.github.cdimascio.dotenv.Dotenv;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import org.slf4j.LoggerFactory; 
public class AccessGitHubAPI {
	public static void main(String[] args) throws IOException {
		LoggerContext loggerContext = (LoggerContext)LoggerFactory.getILoggerFactory();
		loggerContext.getLogger("org.mongodb.driver").setLevel(Level.ERROR);
		Dotenv dotenv = Dotenv.load();
		MongoDB mongodb = new MongoDB(dotenv.get("MONGO_USERNAME"), dotenv.get("MONGO_PASSWORD"), dotenv.get("MONGO_CLUSTER_URL"), dotenv.get("DATABASE_NAME"), dotenv.get("COLLECTION_REPOSITORIES"));
		GitHubClient GHclient = new GitHubClient();
		GHclient.setCredentials(dotenv.get("GITHUB_USERNAME"), dotenv.get("GITHUB_PASSWORD"));
		GHclient.setOAuth2Token(dotenv.get("OAUTH_TOKEN"));
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter a GitHub username to store their information into your MongoDB collection: ");
		String username = scanner.next();
		mongodb.clearCollection();
		mongodb.getAndStoreUserRepositoryInfo(GHclient, username);
		mongodb.setCollection(dotenv.get("COLLECTION_USER"));
		mongodb.clearCollection();
		mongodb.getAndStoreUserInfo(GHclient, username);
		System.exit(0);
	}	
}