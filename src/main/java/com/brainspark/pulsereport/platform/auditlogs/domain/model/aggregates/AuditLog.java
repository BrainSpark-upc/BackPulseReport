package com.brainspark.pulsereport.platform.auditlogs.domain.model.aggregates;

import com.brainspark.pulsereport.platform.auditlogs.domain.model.valueobjects.AuditActionType;
import com.brainspark.pulsereport.platform.auditlogs.domain.model.valueobjects.AuditedEntityType;
import com.brainspark.pulsereport.platform.auditlogs.domain.model.valueobjects.ResponsibleUserId;
import com.brainspark.pulsereport.platform.auditlogs.domain.model.commands.CreateAuditLogCommand;
import com.brainspark.pulsereport.platform.shared.infrastructure.persistence.jpa.configuration.entities.AuditableAbstractPersistenceEntity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.Instant;
//import org.apache.logging.log4j.util.Strings;

@Getter
@NoArgsConstructor
@Entity
public class AuditLog extends AuditableAbstractPersistenceEntity{
    @Embedded
    private ResponsibleUserId responsibleUserId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuditedEntityType auditedEntityType;

    @Column(nullable = false)
    private Long auditedEntityId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuditActionType actionType;

    @Column(nullable = false, updatable = false)
    private Instant occurredAt;

    @Column(length = 500)
    private String detail;

    public AuditLog(CreateAuditLogCommand command) {
        this.responsibleUserId = new ResponsibleUserId(command.responsibleUserId());
        this.auditedEntityType = command.auditedEntityType();
        this.auditedEntityId = command.auditedEntityId();
        this.actionType = command.actionType();
        this.occurredAt = command.occurredAt() != null ? command.occurredAt() : Instant.now();
        this.detail = command.detail();
    }
}
