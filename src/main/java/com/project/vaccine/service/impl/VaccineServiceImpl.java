package com.project.vaccine.service.impl;

import com.project.vaccine.dto.CreateVaccineDTO;
import com.project.vaccine.dto.VaccineDTO;
import com.project.vaccine.dto.VaccineFindIdDTO;
import com.project.vaccine.entity.Vaccine;
import com.project.vaccine.repository.VaccineRepository;
import com.project.vaccine.service.VaccineService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VaccineServiceImpl implements VaccineService {

    private static Logger logger= LogManager.getLogger(VaccineServiceImpl.class);

    @Autowired
    private VaccineRepository vaccineRepository;

    @Override
    public Vaccine findById(Integer id) {

        Vaccine vaccine=new Vaccine();
        try{
            vaccine=vaccineRepository.findByVaccineId(id);
        }catch (Exception ex){
            logger.error(" Lỗi lấy vaccine theo id : "+ex);
        }

        return vaccine;
    }

    @Override
    public VaccineFindIdDTO findVaccineById(Integer id) {

        VaccineFindIdDTO vaccine=null ;
        try{
                vaccine =vaccineRepository.getInfoVaccineById(id);
        }catch (Exception ex){
            logger.error(" Lỗi lấy vaccine theo id : "+ex);
        }

        return vaccine;
    }

    @Override
    public void saveVaccine(Vaccine vaccine) {

    }

    @Override
    public Page<Vaccine> findAllByNameContainingAndVaccineType_NameContainingAndOriginContaining(String name, String vaccineTypeName, String origin, Pageable pageable) {
        return vaccineRepository.findAllByNameContainingAndVaccineType_NameContainingAndOriginContaining(name, vaccineTypeName, origin, pageable);
    }

    @Override
    public Page<Vaccine> findAllByQuantityIsExits(String name, String vaccineType_name, String origin, Pageable pageable) {
        return vaccineRepository.findAllByNameContainingAndVaccineType_NameContainingAndOriginContainingAndQuantityGreaterThan(name, vaccineType_name, origin, 0L, pageable);
    }

    @Override
    public Page<Vaccine> findAllByQuantityIsNotExits(String name, String vaccineType_name, String origin, Pageable pageable) {
        return vaccineRepository.findAllByNameContainingAndVaccineType_NameContainingAndOriginContainingAndQuantityLessThan(name, vaccineType_name, origin, 1L, pageable);
    }

    @Override
    public VaccineDTO getVaccineById(Integer id) {
        return vaccineRepository.getVaccineById(id);
    }

    @Override
    public List<VaccineDTO> getAllVaccineDTO(int index) {
        return vaccineRepository.getAllVaccineDTO(index);
    }

    @Override
    public List<VaccineDTO> getAllVaccineDTONotPagination() {
        return vaccineRepository.getAllVaccineDTONotPagination();
    }

    @Override
    public List<VaccineDTO> search(String name, String vaccineType, String origin) {
        List<VaccineDTO> listVaccine= new ArrayList<VaccineDTO>();
        try {
            listVaccine=vaccineRepository.search('%' +name  +'%','%'+ vaccineType +'%','%' + origin +'%');
        }catch (Exception ex){
            logger.error(" Lỗi query : "+ ex);
        }
        return listVaccine;
    }

    @Override
    public void createVaccine(CreateVaccineDTO createVaccineDTO) {
        vaccineRepository.createVaccine(
                createVaccineDTO.getNameVaccine(), createVaccineDTO.getDosage(),
                createVaccineDTO.getLicenseCode(), createVaccineDTO.getMaintenance(),
                createVaccineDTO.getOrigin(), createVaccineDTO.getExpired(),
                createVaccineDTO.getAge(), createVaccineDTO.getQuantity(),
                Integer.parseInt(createVaccineDTO.getTypeVaccine()), createVaccineDTO.getDuration(),
                createVaccineDTO.getTimes(), createVaccineDTO.getImgVaccine());
    }

    @Override
    public Vaccine searchName(String name) {
        return vaccineRepository.searchName(name);
    }

    @Override
    public List<Vaccine> getAllVaccine() {
        return vaccineRepository.getAllVaccine();
    }

    @Override
    public Vaccine getVaccineByIdNameQuery(Integer id) {
        return vaccineRepository.findById(id).orElse(null);
    }

    @Override
    public List<Vaccine> getAllVaccineByDuration(String name) {
        return vaccineRepository.getAllVaccineBySuggesssion('%' +name+ '%');
    }

    @Override
    public void editVaccine(Vaccine VaccineEditDTO) {
        try{
            vaccineRepository.editVaccine(VaccineEditDTO.getName(),VaccineEditDTO.getAge(),VaccineEditDTO.getExpired(), VaccineEditDTO.getLicenseCode(),
                    VaccineEditDTO.getMaintenance(), VaccineEditDTO.getOrigin(), VaccineEditDTO.getDuration(),
                    VaccineEditDTO.getTimes(), VaccineEditDTO.getDosage(), VaccineEditDTO.getVaccineId());
        }catch (Exception ex){
            logger.error(" Lỗi cập nhật thông tin vaccine : "+ ex);
        }

    }


}
