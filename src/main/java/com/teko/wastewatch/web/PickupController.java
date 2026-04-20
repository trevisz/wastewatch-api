package com.teko.wastewatch.web;

import com.teko.wastewatch.domain.Pickup;
import com.teko.wastewatch.domain.enums.PickupStatus;
import com.teko.wastewatch.service.PickupService;
import com.teko.wastewatch.web.dto.PickupDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pickups")
public class PickupController {

    private final PickupService service;

    public PickupController(PickupService service) {
        this.service = service;
    }

    @GetMapping
    public List<Pickup> list(@RequestParam(required = false) PickupStatus status) {
        return service.list(status);
    }

    @PostMapping("/{collectionPointId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Pickup schedule(@PathVariable Long collectionPointId, @RequestBody @Valid PickupDTO dto) {
        Pickup p = new Pickup();
        p.setScheduledAt(dto.scheduledAt);
        return service.schedule(collectionPointId, p);
    }

    @PutMapping("/{id}/complete")
    public Pickup complete(@PathVariable Long id) {
        return service.complete(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancel(@PathVariable Long id) {
        service.cancel(id);
    }
}
