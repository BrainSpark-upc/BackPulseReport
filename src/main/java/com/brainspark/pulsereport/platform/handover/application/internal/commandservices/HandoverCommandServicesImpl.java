package com.brainspark.pulsereport.platform.handover.application.internal.commandservices;

import com.brainspark.pulsereport.platform.handover.application.commandservices.HandoverCommandService;
import com.brainspark.pulsereport.platform.handover.domain.model.aggregates.Handover;
import com.brainspark.pulsereport.platform.handover.domain.model.commands.CreateHandoverCommand;
import com.brainspark.pulsereport.platform.handover.domain.repositories.HandoverRepository;
import com.brainspark.pulsereport.platform.shared.application.result.ApplicationError;
import com.brainspark.pulsereport.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

@Service
public class HandoverCommandServicesImpl implements HandoverCommandService {
    private final HandoverRepository handoverRepository;

    public HandoverCommandServicesImpl(HandoverRepository handoverRepository) {
        this.handoverRepository = handoverRepository;
    }

    @Override
    public Result<Long, ApplicationError> handle(CreateHandoverCommand command) {
        if (handoverRepository.existsByTitle(command.title()))
            return Result.failure(ApplicationError.conflict("Handover", "Title %s already exists".formatted(command.title())));
        var handover = new Handover(command);
        try {
            handover = handoverRepository.save(handover);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("create-handover", e.getMessage()));
        }
        return Result.success(handover.getId());
    }
}