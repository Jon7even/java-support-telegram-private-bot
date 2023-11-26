package com.github.jon7even.repository;

import com.github.jon7even.model.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByChatId(@Param("chatId") Long chatId);

    UserEntity findByChatId(@Param("chatId") Long chatId);
}
