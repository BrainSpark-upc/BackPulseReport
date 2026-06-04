package com.brainspark.pulsereport.platform.vitalsigns.domain.model.aggregates;

import com.brainspark.pulsereport.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import com.brainspark.pulsereport.platform.vitalsigns.domain.model.commands.CreateVitalSignRecordCommand;
import com.brainspark.pulsereport.platform.vitalsigns.domain.model.events.VitalSignRecordedEvent;
import com.brainspark.pulsereport.platform.vitalsigns.domain.model.valueobjects.BloodPressure;
import com.brainspark.pulsereport.platform.vitalsigns.domain.model.valueobjects.RiskLevel;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class VitalSignRecord extends AbstractDomainAggregateRoot<VitalSignRecord> {
    @Setter
    private Long id;
    private Long patientId;
    private Long nurseId;
    private Integer heartRate;
    private Integer respiratoryRate;
    private BloodPressure bloodPressure;
    private Integer oxygenSaturation;
    private BigDecimal temperature;
    private RiskLevel riskLevel;
    private LocalDateTime recordedAt;

    public VitalSignRecord() {
        this.riskLevel= RiskLevel.UNASSESSED;
        this.recordedAt= LocalDateTime.now();
    }

    public VitalSignRecord(CreateVitalSignRecordCommand command){
        this.patientId = command.patientId();
        this.nurseId = command.nurseId();
        this.heartRate = command.heartRate();
        this.respiratoryRate = command.respiratoryRate();
        this.bloodPressure = command.bloodPressure();
        this.oxygenSaturation = command.oxygenSaturation();
        this.temperature = command.temperature();
        this.riskLevel = RiskLevel.UNASSESSED;
        this.recordedAt= command.recordedAt() !=null
        ? command.recordedAt(): LocalDateTime.now();
        this.registerDomainEvent(new VitalSignRecordedEvent(
                this.patientId,
                this.nurseId,
                this.recordedAt
        ));
    }
    public VitalSignRecord assignRiskLevel(RiskLevel riskLevel) {
        if(riskLevel==null){
            throw new IllegalArgumentException("Risk level is required");
        }
        this.riskLevel = riskLevel;
        return this;
    }
}
