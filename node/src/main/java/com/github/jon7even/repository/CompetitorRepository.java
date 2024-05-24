package com.github.jon7even.repository;

import com.github.jon7even.entity.competitor.CompetitorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Интерфейс DAO для конкурентов(CompetitorEntity), использует JpaRepository
 *
 * @author Jon7even
 * @version 1.0
 */
@Repository
public interface CompetitorRepository extends JpaRepository<CompetitorEntity, Long> {
}
