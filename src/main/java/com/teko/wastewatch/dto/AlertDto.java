package com.teko.wastewatch.dto;

public class AlertDto {
    private Long collectionPointId;
    private String name;
    private String location;
    private double currentLevelKg;
    private double capacityKg;
    private int thresholdPercent;   // int, como o service usa
    private double fillPercent;     // 0..100

    public AlertDto() {}

    // Construtor exatamente na ordem usada pelo AlertService:
    // (Long, String, String, double, double, int, double)
    public AlertDto(Long collectionPointId,
                    String name,
                    String location,
                    double currentLevelKg,
                    double capacityKg,
                    int thresholdPercent,
                    double fillPercent) {
        this.collectionPointId = collectionPointId;
        this.name = name;
        this.location = location;
        this.currentLevelKg = currentLevelKg;
        this.capacityKg = capacityKg;
        this.thresholdPercent = thresholdPercent;
        // se o service já envia fillPercent calculado, mantém;
        // se vier 0, calculamos aqui para garantir.
        this.fillPercent = (fillPercent == 0.0 && capacityKg > 0)
                ? (currentLevelKg / capacityKg) * 100.0
                : fillPercent;
    }

    // Getters/Setters
    public Long getCollectionPointId() { return collectionPointId; }
    public void setCollectionPointId(Long collectionPointId) { this.collectionPointId = collectionPointId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public double getCurrentLevelKg() { return currentLevelKg; }
    public void setCurrentLevelKg(double currentLevelKg) { this.currentLevelKg = currentLevelKg; }

    public double getCapacityKg() { return capacityKg; }
    public void setCapacityKg(double capacityKg) { this.capacityKg = capacityKg; }

    public int getThresholdPercent() { return thresholdPercent; }
    public void setThresholdPercent(int thresholdPercent) { this.thresholdPercent = thresholdPercent; }

    public double getFillPercent() { return fillPercent; }
    public void setFillPercent(double fillPercent) { this.fillPercent = fillPercent; }
}
