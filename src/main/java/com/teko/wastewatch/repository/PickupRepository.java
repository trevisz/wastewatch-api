package com.teko.wastewatch.repository;

import com.teko.wastewatch.domain.Pickup;
import com.teko.wastewatch.domain.enums.PickupStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PickupRepository extends JpaRepository<Pickup, Long> {
    List<Pickup> findByStatus(PickupStatus status);
}
