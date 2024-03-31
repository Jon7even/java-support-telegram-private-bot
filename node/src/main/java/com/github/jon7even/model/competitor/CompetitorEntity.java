package com.github.jon7even.model.competitor;

import com.github.jon7even.model.user.UserEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "competitors", schema = "public")
public class CompetitorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name_competitor", nullable = false)
    private String name;

    @Column(name = "created_on", nullable = false)
    private LocalDateTime created;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "creator_id", nullable = false)
    @ToString.Exclude
    private UserEntity creator;
}
