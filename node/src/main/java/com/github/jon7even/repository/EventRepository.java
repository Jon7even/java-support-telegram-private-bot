package com.github.jon7even.repository;

import com.github.jon7even.entity.event.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Интерфейс DAO для событий(EventEntity), использует JpaRepository
 *
 * @author Jon7even
 * @version 1.0
 */
@Repository
public interface EventRepository extends JpaRepository<EventEntity, Long> {
}
