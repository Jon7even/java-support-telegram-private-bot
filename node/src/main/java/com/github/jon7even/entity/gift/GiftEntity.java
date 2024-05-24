package com.github.jon7even.entity.gift;

import com.github.jon7even.entity.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;

/**
 * Класс описывающий сущность подарков
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
@Table(name = "gifts", schema = "public")
public class GiftEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name_gift", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "creator_id", nullable = false)
    @ToString.Exclude
    private UserEntity creator;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status", nullable = false)
    private GiftStatus status;
}
