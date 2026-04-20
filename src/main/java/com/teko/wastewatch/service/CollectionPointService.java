package com.teko.wastewatch.service;

import com.teko.wastewatch.domain.CollectionPoint;
import com.teko.wastewatch.repository.CollectionPointRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.stream.Collectors;

import java.util.List;

@Service
public class CollectionPointService {

    private final CollectionPointRepository repo;

    public CollectionPointService(CollectionPointRepository repo) {
        this.repo = repo;
    }

    public List<CollectionPoint> list() { return repo.findAll(); }

    public CollectionPoint get(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("CollectionPoint not found: " + id));
    }

    @Transactional
    public CollectionPoint create(CollectionPoint cp) { return repo.save(cp); }

    @Transactional
    public CollectionPoint update(Long id, CollectionPoint in) {
        CollectionPoint cp = get(id);
        cp.setName(in.getName());
        cp.setLocation(in.getLocation());
        cp.setCapacityKg(in.getCapacityKg());
        cp.setCurrentLevelKg(in.getCurrentLevelKg());
        cp.setThresholdPercent(in.getThresholdPercent());
        return repo.save(cp);
    }

    @Transactional
    public void delete(Long id) { repo.deleteById(id); }




    public List<CollectionPoint> alerts() {
        return repo.findAll().stream()
                .filter(CollectionPoint::isOverThreshold)
                .collect(Collectors.toList());
    }


    @Transactional
    public CollectionPoint reportLevel(Long id, double currentKg) {
        CollectionPoint cp = get(id);
        cp.setCurrentLevelKg(currentKg);
        return repo.save(cp);
    }
}
