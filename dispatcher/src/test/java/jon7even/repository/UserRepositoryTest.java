package jon7even.repository;

import com.github.jon7even.DispatcherApp;
import com.github.jon7even.entity.user.UserEntity;
import com.github.jon7even.repository.UserRepository;
import jon7even.setup.ContainersSetup;
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
public class UserRepositoryTest extends ContainersSetup {
    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void clearRepository() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("Корректное сохранение сущности пользователя")
    void saveCorrectUserEntity() {
        initUserEntity();
        UserEntity userOneFullName = userRepository.save(userEntityFirst);
        UserEntity userSecondNull = userRepository.save(userEntitySecond);
        UserEntity userThirdAuthOn = userRepository.save(userEntityThird);

        assertNotNull(userOneFullName);
        assertEquals(firstUserId, userOneFullName.getId());
        assertEquals(userEntityFirst.getChatId(), userOneFullName.getChatId());
        assertEquals(userEntityFirst.getFirstName(), userOneFullName.getFirstName());
        assertEquals(userEntityFirst.getLastName(), userOneFullName.getLastName());
        assertEquals(userEntityFirst.getUserName(), userOneFullName.getUserName());
        assertEquals(userEntityFirst.getRegisteredOn(), userOneFullName.getRegisteredOn());

        assertNotNull(userSecondNull);
        assertEquals(secondUserId, userSecondNull.getId());
        assertEquals(userEntitySecond.getChatId(), userSecondNull.getChatId());
        assertEquals(userEntitySecond.getFirstName(), userSecondNull.getFirstName());
        assertEquals(userEntitySecond.getLastName(), userSecondNull.getLastName());
        assertEquals(userEntitySecond.getUserName(), userSecondNull.getUserName());
        assertEquals(userEntitySecond.getRegisteredOn(), userSecondNull.getRegisteredOn());

        assertNotNull(userThirdAuthOn);
        assertEquals(thirdUserId, userThirdAuthOn.getId());
        assertEquals(userEntityThird.getChatId(), userThirdAuthOn.getChatId());
        assertEquals(userEntityThird.getFirstName(), userThirdAuthOn.getFirstName());
        assertEquals(userEntityThird.getLastName(), userThirdAuthOn.getLastName());
        assertEquals(userEntityThird.getUserName(), userThirdAuthOn.getUserName());
        assertNotNull(userThirdAuthOn.getAuthorization());
        assertEquals(userEntityThird.getAuthorization(), userThirdAuthOn.getAuthorization());
        assertEquals(userEntityThird.getRegisteredOn(), userThirdAuthOn.getRegisteredOn());

        assertEquals(userRepository.findAll().size(), 3);
    }

}
