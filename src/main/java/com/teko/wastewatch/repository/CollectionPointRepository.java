package com.teko.wastewatch.repository;

import com.teko.wastewatch.domain.CollectionPoint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CollectionPointRepository extends JpaRepository<CollectionPoint, Long> {
    List<CollectionPoint> findByCurrentLevelKgGreaterThanEqual(double level);
}
