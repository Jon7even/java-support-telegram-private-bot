package jon7even.setup;

import com.github.jon7even.entity.user.UserEntity;

import java.time.LocalDateTime;

public class GenericTests {
    protected UserEntity userEntityOne;
    protected UserEntity userEntitySecond;
    protected UserEntity userEntityThird;

    protected Long firstId = 1L;
    protected Long secondId = 2L;
    protected Long thirdId = 3L;

    protected void initUsers() {
        userEntityOne = UserEntity.builder()
                .chatId(1111111L)
                .firstName("FirstName")
                .lastName("FirstLastName")
                .userName("FirstUserName")
                .authorization(true)
                .registeredOn(LocalDateTime.now())
                .build();

        userEntitySecond = UserEntity.builder()
                .chatId(2222222L)
                .firstName("SecondName")
                .userName("SecondUserName")
                .authorization(true)
                .registeredOn(LocalDateTime.now())
                .build();

        userEntityThird = UserEntity.builder()
                .chatId(3333333L)
                .firstName("ThirdName")
                .lastName("ThirdLastName")
                .authorization(true)
                .registeredOn(LocalDateTime.now())
                .build();
    }

}