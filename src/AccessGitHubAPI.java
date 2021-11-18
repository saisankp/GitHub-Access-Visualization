
import java.io.IOException;
import java.util.Scanner;

import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.RepositoryService;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

public class AccessGitHubAPI {
	public static void main(String[] args) {
		MongoClient client = MongoClients.create("mongodb+srv://saisankp:zT4mSQDWNRN24Jh@cluster0.yidvg.mongodb.net/myFirstDatabase?retryWrites=true&w=majority");
		while (true) {
			Scanner scanner = new Scanner(System.in);
			try {
				RepositoryService repoService = new RepositoryService();
				while (true) {
					System.out.println(
							"Please enter a username to view their repository information, or type 'quit' to exit.");
					String username = scanner.nextLine();
					if (username.equalsIgnoreCase("quit"))
						return;
					for (Repository repo : repoService.getRepositories(username)) {
						System.out.println("Repository name: " + repo.getName());
						System.out.println("	- Language: " + repo.getLanguage());
						System.out.println("	- Description: " + repo.getDescription());
						System.out.println("	- Size: " + repo.getSize() + " kB");
						System.out.println("	- Number of watchers: " + repo.getWatchers());
						System.out.println("	- Number of forks: " + repo.getForks());
						System.out.println("	- Created at: " + repo.getCreatedAt());
						System.out.println("	- Updated at: " + repo.getUpdatedAt());
						System.out.println("	- URL: " + repo.getGitUrl());
					}
				}
			} catch (Exception e) {
				System.out.println("Sorry, an unexpected error has occured.");
			}
			scanner.close();
		}
	}
}