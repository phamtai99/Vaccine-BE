package com.project.vaccine.dto;

public interface VaccinationHistoryFeedbackDTO {
    Integer getPatientId();
    String getPatientName();
    String getPatientGender();
    String getPatientAge();
    String getPatientGuardian();
    String getPatientAddress();
    String getPatientPhone();
    String getExpired();
    String getName();
    String getVaccineTypeName();
    String getVaccinationDate();
    String getVaccineHistoryAfterStatus();
    String getLocation();
    String getStartTime();
    String getEndTime();
}
