package com.project.vaccine.dto;

import javax.validation.constraints.NotBlank;

public class VaccinationUpdateDTO {

    private Integer vaccinationId;

    @NotBlank(message = "Trường này không được để trống")
    private String startTime;

    @NotBlank(message = "Trường này không được để trống")
    private String endTime;

    @NotBlank(message = "Trường này không được để trống")
    private String date;
    private Boolean status;
    private String name;
    @NotBlank(message = "Trường này không được để trống")
    private String description;

    private String type;
    private String locationId;

    private Integer times;

    private String age;

    public VaccinationUpdateDTO(){

    }

    public VaccinationUpdateDTO(Integer vaccinationId, @NotBlank(message = "Trường này không được để trống") String startTime, @NotBlank(message = "Trường này không được để trống") String endTime, @NotBlank(message = "Trường này không được để trống") String date, Boolean status, String name, @NotBlank(message = "Trường này không được để trống") String description, String type, String locationId, Integer times, String age) {
        this.vaccinationId = vaccinationId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.status = status;
        this.name = name;
        this.description = description;
        this.type = type;
        this.locationId = locationId;
        this.times = times;
        this.age = age;
    }

    public Integer getVaccinationId() {
        return vaccinationId;
    }

    public void setVaccinationId(Integer vaccinationId) {
        this.vaccinationId = vaccinationId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
