package com.github.jon7even.repository;

import com.github.jon7even.entities.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Интерфейс DAO для пользователя(UserEntity), использует JpaRepository
 *
 * @author Jon7even
 * @version 1.0
 * @apiNote chatId присваивается самим Telegram и считается, что он уникальный
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    /**
     * Метод проверки существования пользователя по chatId
     *
     * @param chatId пользователя
     * @return boolean есть ли пользователь в БД с таким chatId
     */
    boolean existsByChatId(@Param("chatId") Long chatId);

    /**
     * Метод поиска пользователя по chatId(присваивается Telegram)
     *
     * @param chatId ID пользователя
     * @return Entity пользователя, если он есть в системе
     */
    UserEntity findByChatId(@Param("chatId") Long chatId);
}
