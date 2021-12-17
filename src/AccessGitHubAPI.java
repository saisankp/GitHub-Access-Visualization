import java.io.IOException;
import java.util.Scanner;

import org.eclipse.egit.github.core.client.GitHubClient;

import io.github.cdimascio.dotenv.Dotenv;

public class AccessGitHubAPI {
	public static void main(String[] args) throws IOException {
		//Get user password from .env file
		Dotenv dotenv = Dotenv.load();
		//Make MongoDB object to store GitHub data to MongoDB Atlas
		MongoDB mongodb = new MongoDB(dotenv.get("MONGO_USERNAME"), dotenv.get("MONGO_PASSWORD"), dotenv.get("DATABASE_NAME"), dotenv.get("COLLECTION_NAME"));
		//Make GitHubClient object to sign into GitHub to make authenticated requests.
		GitHubClient GHclient = new GitHubClient();
		GHclient.setCredentials(dotenv.get("GITHUB_USERNAME"), dotenv.get("GITHUB_PASSWORD"));
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter a GitHub username to store their information into your MongoDB collection: ");
		String username = scanner.next();
		mongodb.clearCollection();
		mongodb.getAndStoreUserRepositoryInfo(GHclient, username);
		mongodb.setCollection("userData");
		mongodb.clearCollection();
		mongodb.getAndStoreUserInfo(GHclient, username);
	}	
}