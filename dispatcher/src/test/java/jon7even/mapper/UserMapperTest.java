package jon7even.mapper;

import com.github.jon7even.entity.user.UserEntity;
import com.github.jon7even.mapper.UserMapper;
import com.github.jon7even.mapper.UserMapperImpl;
import jon7even.setup.PreparationForTests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class UserMapperTest extends PreparationForTests {
    private UserMapper userMapper;

    @BeforeEach
    public void setUp() {
        userMapper = new UserMapperImpl();
        initUserEntity();
        initUserDto();
    }

    @Test
    @DisplayName("Должен произойти правильный маппинг в сущность для создания новых данных в БД")
    public void toEntityFromDtoCreate_ReturnEntityNotId() {
        UserEntity actualResult = userMapper.toEntityFromShortDto(userCreateDtoFirst, LocalDateTime.now());

        assertThat(actualResult)
                .isNotNull();
        assertThat(actualResult.getId())
                .isNull();

        assertThat(actualResult.getChatId())
                .isNotNull()
                .isEqualTo(userCreateDtoFirst.getChatId());
        assertThat(actualResult.getFirstName())
                .isNotNull()
                .isEqualTo(userCreateDtoFirst.getFirstName());
        assertThat(actualResult.getLastName())
                .isNotNull()
                .isEqualTo(userCreateDtoFirst.getLastName());
        assertThat(actualResult.getUserName())
                .isNotNull()
                .isEqualTo(userCreateDtoFirst.getUserName());
    }
}