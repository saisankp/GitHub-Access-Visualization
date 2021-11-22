import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

public class MongoDB {
	 private static MongoClient client;
	 private static MongoDatabase db;
	 private static MongoCollection<Document> col;
	 
	public MongoDB(String mongoUsername, String mongoPassword, String databaseName, String collectionName) {
		client = MongoClients.create("mongodb+srv://" + mongoUsername + ":" + mongoPassword + "@cluster0.yidvg.mongodb.net/myFirstDatabase?retryWrites=true&w=majority");
		db = client.getDatabase(databaseName);
		col = db.getCollection(collectionName);
	}
	
	public void getAndStoreUserRepositoryInfo(GitHubClient client, String username) throws IOException {
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
	
	public void clearCollection() {
		if(col.countDocuments() == 0) {
			return;
		}
		FindIterable<Document> findIterable = col.find();
		BasicDBObject document = new BasicDBObject();
		// Delete All documents from collection Using blank BasicDBObject
		col.deleteMany(document);
	}
}