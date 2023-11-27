package com.github.jon7even;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PrivateBotApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void testMain() {
		Assertions.assertDoesNotThrow(PrivateBotApp::new);
	}

}
