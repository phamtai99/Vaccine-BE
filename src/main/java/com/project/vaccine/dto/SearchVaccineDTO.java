package com.project.vaccine.dto;

public interface SearchVaccineDTO {
    Integer getVaccinationId();
    String getDate();
    String getStartTime();
    String getEndTime();
    String getName();
    String getAge();
    String getDescription();
    String getLocation();
    Integer getTimes();
    Boolean getStatus();
    String getType();
    Integer getLocationId();
}
