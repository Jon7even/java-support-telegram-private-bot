package jon7even.setup;

import com.github.jon7even.dto.UserAuthTrueDto;
import com.github.jon7even.entity.user.UserEntity;

import java.time.LocalDateTime;

public class PreparationForTests {
    protected UserEntity userEntityFirst;
    protected UserEntity userEntitySecond;
    protected UserEntity userEntityThird;

    protected UserAuthTrueDto userCreateDtoFirst;
    protected UserAuthTrueDto userCreateDtoSecond;
    protected UserAuthTrueDto userCreateDtoThird;

    protected Long firstUserId = 1L;
    protected Long secondUserId = 2L;
    protected Long thirdUserId = 3L;

    protected void initUserEntity() {
        userEntityFirst = UserEntity.builder()
                .chatId(1111111L)
                .firstName("FirstName")
                .lastName("FirstLastName")
                .userName("FirstUserName")
                .registeredOn(LocalDateTime.now())
                .build();

        userEntitySecond = UserEntity.builder()
                .chatId(2222222L)
                .registeredOn(LocalDateTime.now())
                .build();

        userEntityThird = UserEntity.builder()
                .chatId(3333333L)
                .firstName("ThirdName")
                .lastName("ThirdLastName")
                .userName("ThirdUserName")
                .registeredOn(LocalDateTime.now())
                .authorization(true)
                .build();
    }

    protected void initUserDto() {
        userCreateDtoFirst = UserAuthTrueDto.builder()
                .chatId(1111111L)
                .firstName("FirstName")
                .lastName("FirstLastName")
                .userName("FirstUserName")
                .build();

        userCreateDtoSecond = UserAuthTrueDto.builder()
                .chatId(2222222L)
                .firstName("SecondName")
                .lastName("SecondLastName")
                .userName("SecondUserName")
                .build();

        userCreateDtoThird = UserAuthTrueDto.builder()
                .chatId(3333333L)
                .firstName("ThirdName")
                .lastName("ThirdLastName")
                .userName("ThirdUserName")
                .build();
    }
}