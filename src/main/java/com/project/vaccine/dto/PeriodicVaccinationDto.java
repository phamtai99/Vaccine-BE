package com.project.vaccine.dto;
/**
 *
 * Class DTO for sending data to register form
 */
public interface PeriodicVaccinationDto {
    Integer getPatientId();
    String getName();
    String getDateOfBirth();
    String getGender();
    String getGuardian();
    String getPhone();
    String getAddress();
    String getStatus();

}
