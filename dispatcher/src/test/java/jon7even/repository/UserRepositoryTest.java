package jon7even.repository;

import com.github.jon7even.model.user.UserEntity;
import com.github.jon7even.repository.UserRepository;
import jon7even.setup.GenericTests;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
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
    void saveHit() {
        initUsers();
        UserEntity userOne = userRepository.save(userOneFull);
        UserEntity userSecond = userRepository.save(userSecondMaxNull);

        assertNotNull(userOne);
        assertEquals(firstId, userOne.getId());
        assertEquals(userOneFull.getChatId(), userOne.getChatId());
        assertEquals(userOneFull.getFirstName(), userOne.getFirstName());
        assertEquals(userOneFull.getLastName(), userOne.getLastName());
        assertEquals(userOneFull.getUserName(), userOne.getUserName());
        assertEquals(userOneFull.getRegisteredOn(), userOne.getRegisteredOn());

        assertNotNull(userSecond);
        assertEquals(secondId, userSecond.getId());
        assertEquals(userSecondMaxNull.getChatId(), userSecond.getChatId());
        assertEquals(userSecondMaxNull.getFirstName(), userSecond.getFirstName());
        assertEquals(userSecondMaxNull.getLastName(), userSecond.getLastName());
        assertEquals(userSecondMaxNull.getUserName(), userSecond.getUserName());
        assertEquals(userSecondMaxNull.getRegisteredOn(), userSecond.getRegisteredOn());

        assertEquals(userRepository.findAll().size(), 2);
    }

}
