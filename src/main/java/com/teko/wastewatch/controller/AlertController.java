package com.teko.wastewatch.controller;

import com.teko.wastewatch.dto.AlertDto;
import com.teko.wastewatch.service.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alerts")
public class AlertController {

    private final AlertService alertService;

    @Autowired
    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    @GetMapping
    public ResponseEntity<List<AlertDto>> list() {
        return ResponseEntity.ok(alertService.listAlerts());
    }
}
