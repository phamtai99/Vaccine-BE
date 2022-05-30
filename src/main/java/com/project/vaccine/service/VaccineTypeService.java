package com.project.vaccine.service;

import com.project.vaccine.entity.VaccineType;

public interface VaccineTypeService {
    /**
     * create vaccineType
     * @return
     */
    void createVaccineType(String name);

    /**
     * find vaccineType by name
     * @return
     */
    VaccineType findVaccineType(String name);


    void editVaccineType(int id, String name);
}
