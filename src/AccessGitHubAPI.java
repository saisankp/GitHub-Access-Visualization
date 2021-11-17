import org.kohsuke.github.*;
import java.io.IOException;

public class AccessGitHubAPI {
    private final String TOKEN = "";
    private GitHub gitHub;

    public AccessGitHubAPI() throws IOException {
        gitHub = new GitHubBuilder().withOAuthToken(TOKEN).build();
    }

    public static void main(String[] args) throws IOException {
    	AccessGitHubAPI app = new AccessGitHubAPI();
        app.printBanner();
    }

    private void printBanner(){
        System.out.println("8888888b.                   .d888 d8b 888                                 \n" +
                "888   Y88b                 d88P\"  Y8P 888                                 \n" +
                "888    888                 888        888                                 \n" +
                "888   d88P 888d888 .d88b.  888888 888 888  .d88b.                         \n" +
                "8888888P\"  888P\"  d88\"\"88b 888    888 888 d8P  Y8b                        \n" +
                "888        888    888  888 888    888 888 88888888                        \n" +
                "888        888    Y88..88P 888    888 888 Y8b.                            \n" +
                "888        888     \"Y88P\"  888    888 888  \"Y8888                         \n" +
                "                                                                          \n" +
                "                                                                          \n" +
                "                                                                          \n" +
                " .d8888b.                                                                 \n" +
                "d88P  Y88b                                                                \n" +
                "Y88b.                                                                     \n" +
                " \"Y888b.   888  888 88888b.d88b.  88888b.d88b.   8888b.  888d888 888  888 \n" +
                "    \"Y88b. 888  888 888 \"888 \"88b 888 \"888 \"88b     \"88b 888P\"   888  888 \n" +
                "      \"888 888  888 888  888  888 888  888  888 .d888888 888     888  888 \n" +
                "Y88b  d88P Y88b 888 888  888  888 888  888  888 888  888 888     Y88b 888 \n" +
                " \"Y8888P\"   \"Y88888 888  888  888 888  888  888 \"Y888888 888      \"Y88888 \n" +
                "                                                                      888 \n" +
                "                                                                 Y8b d88P \n" +
                "                                                                  \"Y88P\"  ");
    }
}