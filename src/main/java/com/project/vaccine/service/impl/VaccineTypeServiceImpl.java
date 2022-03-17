package com.project.vaccine.service.impl;

import com.project.vaccine.entity.VaccineType;
import com.project.vaccine.repository.VaccineTypeRepository;
import com.project.vaccine.service.VaccineTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VaccineTypeServiceImpl implements VaccineTypeService {

    @Autowired
    VaccineTypeRepository vaccineTypeRepository;

    /**
     * create vaccine type
     * @return
     */
    @Override
    public void createVaccineType(String name) {
        vaccineTypeRepository.createVaccineType(name);
    }

    /**
     * find vaccineType by name
     * @return
     */
    @Override
    public VaccineType findVaccineType(String name) {
        return vaccineTypeRepository.findName(name);
    }
}
