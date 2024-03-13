package jon7even.setup;

import com.github.jon7even.model.user.UserEntity;

import java.time.LocalDateTime;

public class GenericTests {
    protected UserEntity userOneFull;
    protected UserEntity userSecondMaxNull;
    protected UserEntity userThirdAuth;
    protected Long firstUserId = 1L;
    protected Long secondUserId = 2L;

    protected Long thirdUserId = 3L;

    protected void initUsers() {
        userOneFull = UserEntity.builder()
                .chatId(1111111L)
                .firstName("FirstName")
                .lastName("FirstLastName")
                .userName("FirstUserName")
                .registeredOn(LocalDateTime.now())
                .build();

        userSecondMaxNull = UserEntity.builder()
                .chatId(2222222L)
                .registeredOn(LocalDateTime.now())
                .build();

        userThirdAuth = UserEntity.builder()
                .chatId(3333333L)
                .firstName("ThirdName")
                .lastName("ThirdLastName")
                .userName("ThirdUserName")
                .registeredOn(LocalDateTime.now())
                .authorization(true)
                .build();
    }

}
