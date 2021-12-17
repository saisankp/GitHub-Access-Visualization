import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import io.github.cdimascio.dotenv.Dotenv;

class AccessGitHubAPITest {

	@Test
	void dotEnvSetUpTest() {
		assertDoesNotThrow(() -> Dotenv.load());
	}

}
