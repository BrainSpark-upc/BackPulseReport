package com.brainspark.pulsereport.platform.handover.infrastructure.persistence.jpa.entities;

import com.brainspark.pulsereport.platform.handover.domain.model.valueobjects.HandoverStatus;
import com.brainspark.pulsereport.platform.shared.infrastructure.persistence.jpa.configuration.entities.AuditableAbstractPersistenceEntity;
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

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private HandoverStatus status;
}
