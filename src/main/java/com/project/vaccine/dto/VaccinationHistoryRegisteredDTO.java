package com.project.vaccine.dto;


public interface VaccinationHistoryRegisteredDTO {
    Integer getVaccinationHistoryId();
    Integer getPatientId();
    String getPatientName();
    String getPatientDob();
    String getPatientGender();
    String getPatientGuardian();
    String getPatientPhone();
    String getPatientAddress();
    String getVaccineName();
    String getVaccineTypeName();
    Boolean getVaccinationHistoryStatus();
    String getEndTime();
    String getDosage();
    String getExpired();
    String getPreStatus();
    String getAfterStatus();
    Integer getVaccinationTimes();
}
