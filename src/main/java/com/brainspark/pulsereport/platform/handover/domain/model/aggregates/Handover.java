package com.brainspark.pulsereport.platform.handover.domain.model.aggregates;

import com.brainspark.pulsereport.platform.handover.domain.model.commands.CreateHandoverCommand;
import com.brainspark.pulsereport.platform.handover.domain.model.valueobjects.HandoverStatus;
import com.brainspark.pulsereport.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.util.Strings;

@Getter
public class Handover extends AbstractDomainAggregateRoot<Handover> {

    @Setter
    private Long id;

    @Setter
    private String title;

    @Setter
    private String description;

    @Setter
    private HandoverStatus status;


    public Handover() {
        this.title = Strings.EMPTY;
        this.description = Strings.EMPTY;
        this.status = HandoverStatus.PENDING;
    }


    public Handover(CreateHandoverCommand command) {
        this.title = command.title();
        this.description = command.description();
        this.status = HandoverStatus.PENDING;
    }


    public Handover updateInformation(String title, String description) {
        this.title = title;
        this.description = description;
        return this;
    }
}
