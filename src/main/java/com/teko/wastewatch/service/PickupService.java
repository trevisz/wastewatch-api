package com.teko.wastewatch.service;

import com.teko.wastewatch.domain.CollectionPoint;
import com.teko.wastewatch.domain.Pickup;
import com.teko.wastewatch.domain.enums.PickupStatus;
import com.teko.wastewatch.repository.CollectionPointRepository;
import com.teko.wastewatch.repository.PickupRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PickupService {

    private final PickupRepository pickupRepository;
    private final CollectionPointRepository collectionPointRepository;

    public PickupService(PickupRepository pickupRepository, CollectionPointRepository collectionPointRepository) {
        this.pickupRepository = pickupRepository;
        this.collectionPointRepository = collectionPointRepository;
    }

    public List<Pickup> list(PickupStatus status) {
        if (status == null) return pickupRepository.findAll();
        return pickupRepository.findByStatus(status);
    }

    public Pickup get(Long id) {
        return pickupRepository.findById(id).orElseThrow(() -> new RuntimeException("Pickup not found: " + id));
    }

    @Transactional
    public Pickup schedule(Long collectionPointId, Pickup p) {
        CollectionPoint cp = collectionPointRepository.findById(collectionPointId)
                .orElseThrow(() -> new RuntimeException("CollectionPoint not found: " + collectionPointId));
        p.setCollectionPoint(cp);
        p.setStatus(PickupStatus.SCHEDULED);
        return pickupRepository.save(p);
    }

    @Transactional
    public Pickup complete(Long id) {
        Pickup p = get(id);
        p.setStatus(PickupStatus.COMPLETED);
        return pickupRepository.save(p);
    }

    @Transactional
    public void cancel(Long id) { pickupRepository.deleteById(id); }
}
