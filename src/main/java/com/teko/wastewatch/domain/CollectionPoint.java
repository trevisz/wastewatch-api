package com.teko.wastewatch.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "collection_point")
public class CollectionPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable=false)
    private String name;

    @NotBlank
    @Column(nullable=false)
    private String location;

    @Positive
    @Column(nullable=false)
    private double capacityKg;

    @PositiveOrZero
    @Column(nullable=false)
    private double currentLevelKg;

    @Min(1) @Max(100)
    @Column(nullable=false)
    private Integer thresholdPercent;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public double getCapacityKg() { return capacityKg; }
    public void setCapacityKg(double capacityKg) { this.capacityKg = capacityKg; }
    public double getCurrentLevelKg() { return currentLevelKg; }
    public void setCurrentLevelKg(double currentLevelKg) { this.currentLevelKg = currentLevelKg; }
    public Integer getThresholdPercent() { return thresholdPercent; }
    public void setThresholdPercent(Integer thresholdPercent) { this.thresholdPercent = thresholdPercent; }

    @Transient
    public double getFillPercent() {
        if (capacityKg <= 0) return 0;
        return Math.min(100.0, (currentLevelKg / capacityKg) * 100.0);
    }

    @Transient
    public boolean isOverThreshold() {
        return getFillPercent() >= thresholdPercent;
    }
}
