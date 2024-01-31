package jon7even;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(
		properties = { "TG_NAME=${TEST_NAME}", "TG_TOKEN=${TEST_TOKEN}" }
)
class PrivateBotAppTests {

	@Test
	void contextLoads() {
	}

	@Test
	void testMain() {
		Assertions.assertDoesNotThrow(PrivateBotApp::new);
	}

}
