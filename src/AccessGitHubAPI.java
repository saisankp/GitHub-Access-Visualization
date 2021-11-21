
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import org.bson.Document;
import org.eclipse.egit.github.core.Commit;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.RepositoryCommit;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.CommitService;
import org.eclipse.egit.github.core.service.OAuthService;
import org.eclipse.egit.github.core.service.RepositoryService;

import com.mongodb.BasicDBObject;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
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
		MongoClient client = MongoClients.create("mongodb+srv://saisankp:" + dotenv.get("MONGO_PASSWORD") + "@cluster0.yidvg.mongodb.net/myFirstDatabase?retryWrites=true&w=majority");
		MongoDatabase db = client.getDatabase("GitHubAPI");
		MongoCollection<Document> col = db.getCollection("repositories");
		
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
						clearCollection(col);
						getAndStoreUserRepositoryInfo(GHclient, username, col);
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
	
	
	public static void clearCollection(MongoCollection<Document> col) {
		if(col.countDocuments() == 0) {
			return;
		}
		FindIterable<Document> findIterable = col.find();
		BasicDBObject document = new BasicDBObject();
		// Delete All documents from collection Using blank BasicDBObject
		col.deleteMany(document);
	}
	
	public static void getAndStoreUserRepositoryInfo(GitHubClient client, String username, MongoCollection<Document> col) throws IOException {
		int id = 1;
		RepositoryService repoService = new RepositoryService(client);
		CommitService commitService = new CommitService();
		for (Repository repo : repoService.getRepositories(username)) {
			System.out.println(repo.getName());
			Document mongoDocument = new Document("_id", id);
			mongoDocument.append("Repository name", repo.getName());
			HashMap<String, List<String>> hm = new HashMap<>();
			List<String> list = new ArrayList<>();
			for(RepositoryCommit commit : commitService.getCommits(repo)) {
				list = hm.getOrDefault(commit.getCommit().getAuthor().getName(), new ArrayList<>());
			    list.add(commit.getCommit().getMessage().toString());
				hm.put(commit.getCommit().getAuthor().getName(), list);
			}
			mongoDocument.append("Commits", hm);
			mongoDocument.append("Language", repo.getLanguage());
			mongoDocument.append("Description", repo.getDescription());
			mongoDocument.append("Size", repo.getSize());
			mongoDocument.append("Number of watchers", repo.getWatchers());
			mongoDocument.append("Number of forks", repo.getForks());
			mongoDocument.append("Creation", repo.getCreatedAt());
			mongoDocument.append("Updated", repo.getUpdatedAt());
			mongoDocument.append("URL", repo.getUrl());
			System.out.println(mongoDocument.toJson());
			col.insertOne(mongoDocument);
			id++;
		}
	}
	
	
}