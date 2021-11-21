import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import org.bson.Document;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.RepositoryCommit;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.CommitService;
import org.eclipse.egit.github.core.service.RepositoryService;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

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
		boolean bool = true;
		//Store each repository as a separate document in the repositories collection in the GitHubAPI MongoDB database.
		while (bool) {
			Scanner scanner = new Scanner(System.in);
			try {
				
				while (bool) {
					System.out.println("Please enter a username to view their repository information, or type 'quit' to exit.");
					String username = "saisankp";
					if (username.equalsIgnoreCase("quit")) {
						return;
					}
					else {
						mongodb.clearCollection();
						mongodb.getAndStoreUserRepositoryInfo(GHclient, username);
					}
					bool = false;
				}
			} catch (Exception e) {
				e.printStackTrace();

			}
			scanner.close();
			bool = false;
		}
	}	
}