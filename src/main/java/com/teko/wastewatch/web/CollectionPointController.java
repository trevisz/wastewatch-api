package com.teko.wastewatch.web;

import com.teko.wastewatch.domain.CollectionPoint;
import com.teko.wastewatch.service.CollectionPointService;
import com.teko.wastewatch.web.dto.CollectionPointDTO;
import com.teko.wastewatch.web.dto.ReportLevelDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/collection-points")
public class CollectionPointController {

    private final CollectionPointService service;

    public CollectionPointController(CollectionPointService service) {
        this.service = service;
    }

    @GetMapping
    public List<CollectionPoint> list() {
        return service.list();
    }

    @GetMapping("/alerts")
    public List<CollectionPoint> alerts() { return service.alerts(); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CollectionPoint create(@RequestBody @Valid CollectionPointDTO dto) {
        CollectionPoint cp = new CollectionPoint();
        cp.setName(dto.name);
        cp.setLocation(dto.location);
        cp.setCapacityKg(dto.capacityKg);
        cp.setCurrentLevelKg(dto.currentLevelKg);
        cp.setThresholdPercent(dto.thresholdPercent);
        return service.create(cp);
    }

    @PutMapping("/{id}")
    public CollectionPoint update(@PathVariable Long id, @RequestBody @Valid CollectionPointDTO dto) {
        CollectionPoint cp = new CollectionPoint();
        cp.setName(dto.name);
        cp.setLocation(dto.location);
        cp.setCapacityKg(dto.capacityKg);
        cp.setCurrentLevelKg(dto.currentLevelKg);
        cp.setThresholdPercent(dto.thresholdPercent);
        return service.update(id, cp);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @PostMapping("/{id}/report-level")
    public CollectionPoint report(@PathVariable Long id, @RequestBody @Valid ReportLevelDTO body) {
        return service.reportLevel(id, body.currentLevelKg);
    }
}
