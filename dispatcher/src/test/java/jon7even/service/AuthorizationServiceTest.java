package jon7even.service;

import com.github.jon7even.cache.UserAuthCache;
import com.github.jon7even.cache.UserAuthCacheImpl;
import com.github.jon7even.configuration.SecurityConfig;
import com.github.jon7even.dto.UserShortDto;
import com.github.jon7even.mapper.UserMapper;
import com.github.jon7even.model.user.UserEntity;
import com.github.jon7even.repository.UserRepository;
import com.github.jon7even.service.AuthorizationService;
import com.github.jon7even.service.AuthorizationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.HashMap;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.anyLong;

@ExtendWith(MockitoExtension.class)
public class AuthorizationServiceTest {

    private AuthorizationService authorizationService;

    @Mock
    private UserRepository userRepository;

    private SecurityConfig securityConfig;

    private UserAuthCache userAuthCache;

    private UserShortDto correctUserShortTO;

    private UserShortDto incorrectUserShortTO;

    @BeforeEach
    void setUp() {
        securityConfig = new SecurityConfig();
        userAuthCache = new UserAuthCacheImpl(new HashMap<>());
        securityConfig.setAttemptsAuth(2);
        securityConfig.setKeyPass("777");
        authorizationService = new AuthorizationServiceImpl(userRepository, securityConfig, userAuthCache);

        correctUserShortTO = UserShortDto.builder()
                .chatId(1111111L)
                .firstName("FirstName")
                .lastName("LastName")
                .userName("UserNameFirst")
                .textMessage("777")
                .build();

        incorrectUserShortTO = UserShortDto.builder()
                .chatId(1111111L)
                .firstName("FirstName")
                .lastName("LastNameSecond")
                .userName("UserName")
                .textMessage("0")
                .build();
    }

    @Test
    @DisplayName("Новый пользователь ввел правильный пароль и авторизовался")
    void authUser_whenNewUserSetValidPass() {
        UserEntity userEntityFromDB = UserMapper.INSTANCE.toEntityFromShortDto(correctUserShortTO, LocalDateTime.now());
        userEntityFromDB.setId(1L);
        when(userRepository.save(any())).thenReturn(userEntityFromDB);

        boolean resultAuth = authorizationService.processAuthorization(correctUserShortTO);
        assertThat(resultAuth, notNullValue());
        assertThat(resultAuth, equalTo(true));

        verify(userRepository, times(1)).existsByChatId(anyLong());
        verify(userRepository, never()).findByChatId(anyLong());
        verify(userRepository, times(2)).save(any(UserEntity.class));
    }

    @Test
    @DisplayName("Зарегистрированный пользователь ввел правильный пароль и авторизовался")
    void authUser_whenOldUserSetValidPass() {
        UserEntity userEntityFromDB = UserMapper.INSTANCE.toEntityFromShortDto(correctUserShortTO, LocalDateTime.now());
        userEntityFromDB.setId(1L);
        when(userRepository.findByChatId(any())).thenReturn(userEntityFromDB);
        when(userRepository.existsByChatId(any())).thenReturn(true);

        boolean resultAuth = authorizationService.processAuthorization(correctUserShortTO);
        assertThat(resultAuth, notNullValue());
        assertThat(resultAuth, equalTo(true));

        verify(userRepository, times(1)).existsByChatId(anyLong());
        verify(userRepository, times(1)).findByChatId(anyLong());
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    @DisplayName("Пользователь есть в системе и уже авторизован")
    void authUser_whenUserAlreadyInDbWithTrueAuthorization() {
        UserEntity userEntityFromDB = UserMapper.INSTANCE.toEntityFromShortDto(correctUserShortTO, LocalDateTime.now());
        userEntityFromDB.setId(1L);
        userEntityFromDB.setAuthorization(true);
        when(userRepository.findByChatId(any())).thenReturn(userEntityFromDB);
        when(userRepository.existsByChatId(any())).thenReturn(true);

        boolean resultAuth = authorizationService.processAuthorization(correctUserShortTO);
        assertThat(resultAuth, notNullValue());
        assertThat(resultAuth, equalTo(true));

        verify(userRepository, times(1)).existsByChatId(anyLong());
        verify(userRepository, times(1)).findByChatId(anyLong());
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    @DisplayName("Новый пользователь пытается авторизоваться с неправильным паролем")
    void notAuthUser_whenNewUserSetNotValidPass() {
        UserEntity userEntityFromDB = UserMapper.INSTANCE.toEntityFromShortDto(
                incorrectUserShortTO, LocalDateTime.now()
        );
        userEntityFromDB.setId(1L);
        when(userRepository.save(any())).thenReturn(userEntityFromDB);

        boolean resultAuth = authorizationService.processAuthorization(incorrectUserShortTO);
        assertThat(resultAuth, notNullValue());
        assertThat(resultAuth, equalTo(false));

        verify(userRepository, times(1)).existsByChatId(anyLong());
        verify(userRepository, never()).findByChatId(anyLong());
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    @DisplayName("Зарегистрированный пользователь пытается авторизоваться с неправильным паролем")
    void notAuthUser_whenOldUserSetNotValidPass() {
        UserEntity userEntityFromDB = UserMapper.INSTANCE.toEntityFromShortDto(
                incorrectUserShortTO, LocalDateTime.now()
        );
        userEntityFromDB.setId(1L);
        when(userRepository.findByChatId(any())).thenReturn(userEntityFromDB);
        when(userRepository.existsByChatId(any())).thenReturn(true);

        boolean resultAuth = authorizationService.processAuthorization(incorrectUserShortTO);
        assertThat(resultAuth, notNullValue());
        assertThat(resultAuth, equalTo(false));

        verify(userRepository, times(1)).existsByChatId(anyLong());
        verify(userRepository, times(1)).findByChatId(anyLong());
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    @DisplayName("Зарегистрированный пользователь вводил пароль неправильно и должен быть заблокирован")
    void notAuthUser_whenOldUserSetNotValidPassOverLimits() {
        UserEntity userEntityFromDB = UserMapper.INSTANCE.toEntityFromShortDto(
                incorrectUserShortTO, LocalDateTime.now()
        );
        userEntityFromDB.setId(1L);
        when(userRepository.findByChatId(any())).thenReturn(userEntityFromDB);
        when(userRepository.existsByChatId(any())).thenReturn(true);

        boolean resultAuthOne = authorizationService.processAuthorization(incorrectUserShortTO);
        assertThat(resultAuthOne, notNullValue());
        assertThat(resultAuthOne, equalTo(false));

        incorrectUserShortTO.setTextMessage("1");
        boolean resultAuthSecond = authorizationService.processAuthorization(incorrectUserShortTO);
        assertThat(resultAuthSecond, notNullValue());
        assertThat(resultAuthSecond, equalTo(false));

        incorrectUserShortTO.setTextMessage("77");
        boolean resultAuthThird = authorizationService.processAuthorization(incorrectUserShortTO);
        assertThat(resultAuthThird, notNullValue());
        assertThat(resultAuthThird, equalTo(false));

        verify(userRepository, times(3)).existsByChatId(anyLong());
        verify(userRepository, times(3)).findByChatId(anyLong());
        verify(userRepository, never()).save(any(UserEntity.class));
    }

}
