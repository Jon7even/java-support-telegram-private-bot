package jon7even.repository;

import com.github.jon7even.DispatcherApp;
import com.github.jon7even.entities.user.UserEntity;
import com.github.jon7even.repository.UserRepository;
import jon7even.setup.GenericTests;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@ActiveProfiles(value = "test")
@SpringBootTest(classes = DispatcherApp.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserRepositoryTest extends GenericTests {
    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void clearRepository() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("Корректное сохранение сущности пользователя")
    void saveCorrectUserEntity() {
        initUsers();
        UserEntity userOneFullName = userRepository.save(userOneFull);
        UserEntity userSecondNull = userRepository.save(userSecondMaxNull);
        UserEntity userThirdAuthOn = userRepository.save(userThirdAuth);

        assertNotNull(userOneFullName);
        assertEquals(firstUserId, userOneFullName.getId());
        assertEquals(userOneFull.getChatId(), userOneFullName.getChatId());
        assertEquals(userOneFull.getFirstName(), userOneFullName.getFirstName());
        assertEquals(userOneFull.getLastName(), userOneFullName.getLastName());
        assertEquals(userOneFull.getUserName(), userOneFullName.getUserName());
        assertEquals(userOneFull.getRegisteredOn(), userOneFullName.getRegisteredOn());

        assertNotNull(userSecondNull);
        assertEquals(secondUserId, userSecondNull.getId());
        assertEquals(userSecondMaxNull.getChatId(), userSecondNull.getChatId());
        assertEquals(userSecondMaxNull.getFirstName(), userSecondNull.getFirstName());
        assertEquals(userSecondMaxNull.getLastName(), userSecondNull.getLastName());
        assertEquals(userSecondMaxNull.getUserName(), userSecondNull.getUserName());
        assertEquals(userSecondMaxNull.getRegisteredOn(), userSecondNull.getRegisteredOn());

        assertNotNull(userThirdAuthOn);
        assertEquals(thirdUserId, userThirdAuthOn.getId());
        assertEquals(userThirdAuth.getChatId(), userThirdAuthOn.getChatId());
        assertEquals(userThirdAuth.getFirstName(), userThirdAuthOn.getFirstName());
        assertEquals(userThirdAuth.getLastName(), userThirdAuthOn.getLastName());
        assertEquals(userThirdAuth.getUserName(), userThirdAuthOn.getUserName());
        assertNotNull(userThirdAuthOn.getAuthorization());
        assertEquals(userThirdAuth.getAuthorization(), userThirdAuthOn.getAuthorization());
        assertEquals(userThirdAuth.getRegisteredOn(), userThirdAuthOn.getRegisteredOn());

        assertEquals(userRepository.findAll().size(), 3);
    }

}
