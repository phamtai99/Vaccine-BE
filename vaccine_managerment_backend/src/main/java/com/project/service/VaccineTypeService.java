package com.project.service;

import com.project.entity.VaccineType;

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
}
