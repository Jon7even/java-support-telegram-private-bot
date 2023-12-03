package com.github.jon7even;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(
		properties = { "TG_NAME=test", "TG_TOKEN=test" }
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
