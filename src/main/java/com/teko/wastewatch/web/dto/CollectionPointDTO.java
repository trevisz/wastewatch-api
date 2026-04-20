package com.teko.wastewatch.web.dto;

import jakarta.validation.constraints.*;

public class CollectionPointDTO {
    @NotBlank public String name;
    @NotBlank public String location;
    @Positive public double capacityKg;
    @PositiveOrZero public double currentLevelKg;
    @Min(1) @Max(100) public int thresholdPercent;
}
