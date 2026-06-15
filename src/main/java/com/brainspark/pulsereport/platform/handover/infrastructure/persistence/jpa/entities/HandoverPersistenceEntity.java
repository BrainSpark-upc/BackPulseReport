package com.brainspark.pulsereport.platform.handover.infrastructure.persistence.jpa.entities;

import com.brainspark.pulsereport.platform.handover.domain.model.valueobjects.HandoverStatus;
import com.brainspark.pulsereport.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "handovers")
@Getter
@Setter
@NoArgsConstructor
public class HandoverPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "patient_id", nullable = false)
    private Long patientId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private HandoverStatus status;
}
