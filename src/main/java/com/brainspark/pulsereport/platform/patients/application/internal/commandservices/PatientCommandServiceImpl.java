package com.brainspark.pulsereport.platform.patients.application.internal.commandservices;

import com.brainspark.pulsereport.platform.patients.application.commandservices.PatientCommandService;
import com.brainspark.pulsereport.platform.patients.domain.exceptions.PatientAlreadyExistsException;
import com.brainspark.pulsereport.platform.patients.domain.model.aggregates.Patient;
import com.brainspark.pulsereport.platform.patients.domain.model.commands.CreatePatientCommand;
import com.brainspark.pulsereport.platform.patients.domain.repositories.PatientRepository;
import com.brainspark.pulsereport.platform.shared.application.result.ApplicationError;
import com.brainspark.pulsereport.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

@Service
public class PatientCommandServiceImpl implements PatientCommandService {

    private final PatientRepository patientRepository;

    public PatientCommandServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public Result<Patient, ApplicationError> handle(CreatePatientCommand command) {
        try {
            if (patientRepository.existsByDocumentNumber(command.documentNumber())) {
                throw new PatientAlreadyExistsException(command.documentNumber());
            }

            var patient = new Patient(command);
            var savedPatient = patientRepository.save(patient);

            return Result.success(savedPatient);
        } catch (PatientAlreadyExistsException exception) {
            return Result.failure(ApplicationError.conflict(
                    "Patient",
                    exception.getMessage()
            ));
        } catch (RuntimeException exception) {
            return Result.failure(ApplicationError.businessRuleViolation(
                    "create patient",
                    exception.getMessage()
            ));
        }
    }
}
