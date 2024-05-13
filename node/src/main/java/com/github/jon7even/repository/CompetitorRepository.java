package com.github.jon7even.repository;

import com.github.jon7even.entity.competitor.CompetitorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompetitorRepository extends JpaRepository<CompetitorEntity, Long> {
}
