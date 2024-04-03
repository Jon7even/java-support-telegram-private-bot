package com.github.jon7even.model.event;

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
@Table(name = "events", schema = "public")
public class EventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name_event", nullable = false)
    private String name;

    @Column(name = "time_start", nullable = false)
    private LocalDateTime start;

    @Column(name = "time_end", nullable = false)
    private LocalDateTime end;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status", nullable = false)
    private EventStatus status;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "creator_id", nullable = false)
    @ToString.Exclude
    private UserEntity creator;
}
