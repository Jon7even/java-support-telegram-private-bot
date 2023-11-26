package com.github.jon7even.repository;

import com.github.jon7even.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Интерфейс DAO для пользователя(UserEntity), использует JpaRepository
 *
 * @author Jon7even
 * @version 2.0
 * @apiNote chatId присваивается самим Telegram и считается, что он уникальный
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    /**
     * Метод проверки существования пользователя по chatId
     *
     * @param chatId - это chatId, который присваивает Telegram
     * @return boolean есть ли пользователь в БД с таким chatId
     */
    boolean existsByChatId(@Param("chatId") Long chatId);

    /**
     * Метод поиска пользователя по chatId(присваивается Telegram)
     *
     * @param chatId - это chatId, который присваивает Telegram
     * @return Entity пользователя, если он есть в системе
     */
    Optional<UserEntity> findByChatId(@Param("chatId") Long chatId);
}