package accessGitHubAPI;
import java.io.IOException;
import java.util.Scanner;

import org.eclipse.egit.github.core.client.GitHubClient;

import io.github.cdimascio.dotenv.Dotenv;

public class AccessGitHubAPI {
	public static void main(String[] args) throws IOException {
		Dotenv dotenv = Dotenv.load();
		MongoDB mongodb = new MongoDB(dotenv.get("MONGO_USERNAME"), dotenv.get("MONGO_PASSWORD"), dotenv.get("DATABASE_NAME"), dotenv.get("COLLECTION_REPOSITORIES"));
		GitHubClient GHclient = new GitHubClient();
		GHclient.setCredentials(dotenv.get("GITHUB_USERNAME"), dotenv.get("GITHUB_PASSWORD"));
		GHclient.setOAuth2Token(dotenv.get("OAUTH_TOKEN"));
		System.out.println(GHclient.getUser());
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter a GitHub username to store their information into your MongoDB collection: ");
		String username = scanner.next();
		mongodb.clearCollection();
		mongodb.getAndStoreUserRepositoryInfo(GHclient, username);
		mongodb.setCollection(dotenv.get("COLLECTION_USER"));
		mongodb.clearCollection();
		mongodb.getAndStoreUserInfo(GHclient, username);
	}	
}