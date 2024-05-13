package com.github.jon7even.entity.user;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

import static com.github.jon7even.constants.DataTimePattern.DATE_TIME_FORMATTER;

/**
 * Класс описывающий сущность пользователя
 *
 * @author Jon7even
 * @version 1.0
 */
@Entity
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user", schema = "bot")
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

    @Column(name = "auth_on")
    @ColumnDefault(value = "false")
    private Boolean authorization;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserEntity)) return false;
        return chatId != null && chatId.equals(((UserEntity) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", chatId=" + chatId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userName='" + userName + '\'' +
                ", registeredOn=" + registeredOn.format(DATE_TIME_FORMATTER) + '\'' +
                ", authorization='" + authorization +
                '}';
    }
}
