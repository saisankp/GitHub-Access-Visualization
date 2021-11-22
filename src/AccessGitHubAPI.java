import java.io.IOException;
import java.util.Scanner;

import org.eclipse.egit.github.core.client.GitHubClient;


import io.github.cdimascio.dotenv.Dotenv;

public class AccessGitHubAPI {
	public static void main(String[] args) throws IOException {
		//Get user password from .env file
		Dotenv dotenv = Dotenv.load();
		//Make MongoDB object to store GitHub data to MongoDB Atlas
		MongoDB mongodb = new MongoDB("saisankp", dotenv.get("MONGO_PASSWORD"), "GitHubAPI", "repositories");
		//Make GitHubClient object to sign into GitHub to make authenticated requests.
		GitHubClient GHclient = new GitHubClient();
		GHclient.setCredentials(dotenv.get("GITHUB_USERNAME"), dotenv.get("GITHUB_PASSWORD"));
		Scanner scanner = new Scanner(System.in);
		System.out.println("Please enter a username to view their repository information, or type 'quit' to exit.");
		String username = scanner.next();
		mongodb.clearCollection();
		mongodb.getAndStoreUserRepositoryInfo(GHclient, username);
	}	
}