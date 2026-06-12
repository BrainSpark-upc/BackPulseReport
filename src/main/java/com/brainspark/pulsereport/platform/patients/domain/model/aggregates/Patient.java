package com.brainspark.pulsereport.platform.patients.domain.model.aggregates;

import com.brainspark.pulsereport.platform.patients.domain.exceptions.InvalidPatientException;
import com.brainspark.pulsereport.platform.patients.domain.model.commands.CreatePatientCommand;
import com.brainspark.pulsereport.platform.patients.domain.model.valueobjects.PatientStatus;
import com.brainspark.pulsereport.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
public class Patient extends AbstractDomainAggregateRoot<Patient> {

    @Setter
    private Long id;

    private String firstName;
    private String lastName;
    private String documentNumber;
    private LocalDate birthDate;
    private String gender;
    private String diagnosis;
    private String roomNumber;
    private String bedNumber;
    private String attendingPhysician;
    private PatientStatus status;
    private LocalDate admissionDate;

    public Patient() {
    }

    public Patient(CreatePatientCommand command) {
        validate(command);

        this.firstName = command.firstName().trim();
        this.lastName = command.lastName().trim();
        this.documentNumber = command.documentNumber().trim();
        this.birthDate = command.birthDate();
        this.gender = command.gender().trim();
        this.diagnosis = command.diagnosis().trim();
        this.roomNumber = command.roomNumber().trim();
        this.bedNumber = command.bedNumber().trim();
        this.attendingPhysician = command.attendingPhysician().trim();
        this.status = command.status() != null ? command.status() : PatientStatus.OBSERVATION;
        this.admissionDate = command.admissionDate() != null ? command.admissionDate() : LocalDate.now();
    }

    private Patient(
            Long id,
            String firstName,
            String lastName,
            String documentNumber,
            LocalDate birthDate,
            String gender,
            String diagnosis,
            String roomNumber,
            String bedNumber,
            String attendingPhysician,
            PatientStatus status,
            LocalDate admissionDate
    ) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.documentNumber = documentNumber;
        this.birthDate = birthDate;
        this.gender = gender;
        this.diagnosis = diagnosis;
        this.roomNumber = roomNumber;
        this.bedNumber = bedNumber;
        this.attendingPhysician = attendingPhysician;
        this.status = status;
        this.admissionDate = admissionDate;
    }

    private void validate(CreatePatientCommand command) {
        if (command.firstName() == null || command.firstName().isBlank()) {
            throw new InvalidPatientException("First name is required");
        }

        if (command.lastName() == null || command.lastName().isBlank()) {
            throw new InvalidPatientException("Last name is required");
        }

        if (command.documentNumber() == null || command.documentNumber().isBlank()) {
            throw new InvalidPatientException("Document number is required");
        }

        if (command.birthDate() == null) {
            throw new InvalidPatientException("Birth date is required");
        }

        if (command.birthDate().isAfter(LocalDate.now())) {
            throw new InvalidPatientException("Birth date cannot be in the future");
        }

        if (command.gender() == null || command.gender().isBlank()) {
            throw new InvalidPatientException("Gender is required");
        }

        if (command.diagnosis() == null || command.diagnosis().isBlank()) {
            throw new InvalidPatientException("Diagnosis is required");
        }

        if (command.roomNumber() == null || command.roomNumber().isBlank()) {
            throw new InvalidPatientException("Room number is required");
        }

        if (command.bedNumber() == null || command.bedNumber().isBlank()) {
            throw new InvalidPatientException("Bed number is required");
        }

        if (command.attendingPhysician() == null || command.attendingPhysician().isBlank()) {
            throw new InvalidPatientException("Attending physician is required");
        }
    }

    public String getFullName() {
        return "%s %s".formatted(firstName, lastName);
    }

    public static Patient reconstitute(
            Long id,
            String firstName,
            String lastName,
            String documentNumber,
            LocalDate birthDate,
            String gender,
            String diagnosis,
            String roomNumber,
            String bedNumber,
            String attendingPhysician,
            PatientStatus status,
            LocalDate admissionDate
    ) {
        return new Patient(
                id,
                firstName,
                lastName,
                documentNumber,
                birthDate,
                gender,
                diagnosis,
                roomNumber,
                bedNumber,
                attendingPhysician,
                status,
                admissionDate
        );
    }
}
