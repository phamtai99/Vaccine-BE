package com.project.vaccine.service;

import com.project.vaccine.dto.CreateVaccineDTO;
import com.project.vaccine.dto.VaccineDTO;
import com.project.vaccine.entity.Vaccine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VaccineService {

    Vaccine findById(Integer id);

    void saveVaccine(Vaccine vaccine);

    Page<Vaccine> findAllByNameContainingAndVaccineType_NameContainingAndOriginContaining(String name, String vaccineTypeName, String origin, Pageable pageable);

    Page<Vaccine> findAllByQuantityIsExits(String name, String vaccineType_name, String origin, Pageable pageable);

    Page<Vaccine> findAllByQuantityIsNotExits(String name, String vaccineType_name, String origin, Pageable pageable);

    VaccineDTO getVaccineById(Integer id);

    List<VaccineDTO> getAllVaccineDTO(int index);

    List<VaccineDTO> getAllVaccineDTONotPagination();

    List<VaccineDTO> search(String name, String vaccineType, String origin);

    void createVaccine(CreateVaccineDTO createVaccineDTO);

    Vaccine searchName(String name);

    List<Vaccine> getAllVaccine();

    Vaccine getVaccineByIdNameQuery(Integer id);

    List<Vaccine> getAllVaccineByDuration(String name );
}
