package com.project.vaccine.service.impl;

import com.project.vaccine.entity.VaccineType;
import com.project.vaccine.repository.VaccineTypeRepository;
import com.project.vaccine.service.VaccineTypeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VaccineTypeServiceImpl implements VaccineTypeService {
    private static Logger logger= LogManager.getLogger(VaccineTypeServiceImpl.class);
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

    @Override
    public void editVaccineType(int id, String name) {
        try{
            vaccineTypeRepository.editVaccineType(name, id);
        }catch (Exception ex){
            logger.error("Lỗi cập nhật loại vaccine "+ex);
        }

    }
}
