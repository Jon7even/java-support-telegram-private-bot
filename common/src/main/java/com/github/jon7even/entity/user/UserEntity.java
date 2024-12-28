package com.github.jon7even.entity.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

/**
 * Класс описывающий сущность пользователя
 *
 * @author Jon7even
 * @version 2.0
 */
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user", schema = "bot-schema")
public class UserEntity {

    @Id
    @SequenceGenerator(name = "UserGenId", sequenceName = "user_seq", allocationSize = 7)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UserGenId")
    @Column(name = "id")
    private Long id;

    @Column(name = "chat_id", nullable = false, unique = true)
    private Long chatId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "register_on", nullable = false)
    private LocalDateTime registeredOn;

    @Column(name = "updated_on")
    private LocalDateTime updatedOn;

    @Column(name = "auth_on")
    @ColumnDefault(value = "false")
    private Boolean authorization;
}