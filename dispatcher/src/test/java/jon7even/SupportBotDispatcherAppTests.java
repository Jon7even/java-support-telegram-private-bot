package jon7even;

import com.github.jon7even.DispatcherApp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles(value = "test")
@SpringBootTest(classes = DispatcherApp.class)
class SupportBotDispatcherAppTests {

    @Test
    void contextLoads() {
    }

    @Test
    void testMain() {
        Assertions.assertDoesNotThrow(DispatcherApp::new);
    }

}
