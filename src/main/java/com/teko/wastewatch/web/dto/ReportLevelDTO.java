package com.teko.wastewatch.web.dto;

import jakarta.validation.constraints.PositiveOrZero;

public class ReportLevelDTO {
    @PositiveOrZero
    public double currentLevelKg;
}
