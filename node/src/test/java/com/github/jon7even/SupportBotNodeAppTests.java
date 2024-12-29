package com.github.jon7even;

import com.github.jon7even.NodeApp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles(value = "test")
@SpringBootTest(classes = NodeApp.class)
class SupportBotNodeAppTests {

    @Test
    void contextLoads() {
    }

    @Test
    void testMain() {
        Assertions.assertDoesNotThrow(NodeApp::new);
    }

}
