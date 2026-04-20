package com.teko.wastewatch.web.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.FutureOrPresent;
import java.time.OffsetDateTime;

public class PickupDTO {
    @NotNull
    @FutureOrPresent
    public OffsetDateTime scheduledAt;
}
