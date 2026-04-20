package com.teko.wastewatch.domain;

import com.teko.wastewatch.domain.enums.PickupStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;

@Entity
@Table(name = "pickup")
public class Pickup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional=false)
    @JoinColumn(name="collection_point_id")
    private CollectionPoint collectionPoint;

    @NotNull
    @FutureOrPresent
    @Column(nullable=false)
    private OffsetDateTime scheduledAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private PickupStatus status = PickupStatus.PENDING;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public CollectionPoint getCollectionPoint() { return collectionPoint; }
    public void setCollectionPoint(CollectionPoint collectionPoint) { this.collectionPoint = collectionPoint; }

    public OffsetDateTime getScheduledAt() { return scheduledAt; }
    public void setScheduledAt(OffsetDateTime scheduledAt) { this.scheduledAt = scheduledAt; }

    public PickupStatus getStatus() { return status; }
    public void setStatus(PickupStatus status) { this.status = status; }
}
