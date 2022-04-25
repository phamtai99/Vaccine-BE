package com.project.vaccine.dto;

public interface VacHistoryRegisteredDTO {
    Integer getPatientId();
    String getDateOfBirth();
    String getName();
    String getGender();
    String getGuardian();
    String getPhone();
    String getAddress();
    String getVaccineName();
    String getVaccinationDate();
    Boolean getStatus();
    String getEndTime();
    String getStartTime();
    Integer getVaccinationTimes();
    Integer getVaccinationHistoryId();

}
