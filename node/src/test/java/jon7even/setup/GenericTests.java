package jon7even.setup;

import com.github.jon7even.model.user.UserEntity;

import java.time.LocalDateTime;

public class GenericTests {
    protected UserEntity userOneFull;
    protected UserEntity userSecondMaxNull;
    protected Long firstId = 1L;
    protected Long secondId = 2L;

    protected void initUsers() {
        userOneFull = UserEntity.builder()
                .chatId(124354L)
                .firstName("FirstName")
                .lastName("LastName")
                .userName("UserName")
                .registeredOn(LocalDateTime.now())
                .build();

        userSecondMaxNull = UserEntity.builder()
                .chatId(12432254L)
                .registeredOn(LocalDateTime.now())
                .build();
    }

}
