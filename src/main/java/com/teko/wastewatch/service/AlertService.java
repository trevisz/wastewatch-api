package com.teko.wastewatch.service;

import com.teko.wastewatch.dto.AlertDto;
import com.teko.wastewatch.domain.CollectionPoint;
import com.teko.wastewatch.repository.CollectionPointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AlertService {

    private final CollectionPointRepository collectionPointRepository;

    @Autowired
    public AlertService(CollectionPointRepository collectionPointRepository) {
        this.collectionPointRepository = collectionPointRepository;
    }

    /**
     * Lista APENAS os alerts ativos (quando fillPercent >= thresholdPercent).
     */
    public List<AlertDto> listAlerts() {
        List<AlertDto> alerts = new ArrayList<>();
        for (CollectionPoint cp : collectionPointRepository.findAll()) {
            double capacity = cp.getCapacityKg();
            double current = cp.getCurrentLevelKg();
            // threshold como int; trata null com 0
            int threshold = (cp.getThresholdPercent() == null) ? 0 : cp.getThresholdPercent();

            double fill = (capacity > 0.0) ? (current / capacity) * 100.0 : 0.0;

            if (threshold > 0 && fill >= threshold) {
                alerts.add(new AlertDto(
                        cp.getId(),
                        cp.getName(),
                        cp.getLocation(),
                        current,
                        capacity,
                        threshold,
                        fill
                ));
            }
        }
        return alerts;
    }
}
