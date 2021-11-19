
import java.io.IOException;
import java.util.Scanner;

import org.bson.Document;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.RepositoryService;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import io.github.cdimascio.dotenv.Dotenv;

public class AccessGitHubAPI {
	public static void main(String[] args) {
		//Get user password from .env file
		Dotenv dotenv = Dotenv.load();
		String password = dotenv.get("PASSWORD");

		//Connect to MongoDB Database collection.
		MongoClient client = MongoClients.create("mongodb+srv://saisankp:" + password + "@cluster0.yidvg.mongodb.net/myFirstDatabase?retryWrites=true&w=majority");
		MongoDatabase db = client.getDatabase("GitHubAPI");
		MongoCollection<Document> col = db.getCollection("repositories");
		
		//Store each repository as a separate document in the repositories collection in the GitHubAPI MongoDB database.
		while (true) {
			Scanner scanner = new Scanner(System.in);
			try {
				RepositoryService repoService = new RepositoryService();
				while (true) {
					System.out.println("Please enter a username to view their repository information, or type 'quit' to exit.");
					int id = 1;
					String username = scanner.nextLine();
					if (username.equalsIgnoreCase("quit"))
						return;
					FindIterable<Document> findIterable = col.find();
				       for (Document document : findIterable) {
				         col.deleteMany(document);
				       }
					for (Repository repo : repoService.getRepositories(username)) {
						Document mongoDocument = new Document("_id", id);
						mongoDocument.append("Repository name", repo.getName());
						mongoDocument.append("Language", repo.getLanguage());
						mongoDocument.append("Description", repo.getDescription());
						mongoDocument.append("Size", repo.getSize());
						mongoDocument.append("Number of watchers", repo.getWatchers());
						mongoDocument.append("Number of forks", repo.getForks());
						mongoDocument.append("Creation", repo.getCreatedAt());
						mongoDocument.append("Updated", repo.getUpdatedAt());
						mongoDocument.append("URL", repo.getUrl());
						col.insertOne(mongoDocument);
						id++;
					}
				}
			} catch (Exception e) {
				System.out.println("Sorry, an unexpected error has occured.");
			}
			scanner.close();
		}
	}
}