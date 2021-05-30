package com.project.service;

import com.project.dto.*;
import com.project.entity.Vaccination;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface VaccinationService {


    /**
     *Find registrable vaccination by Id
     */
    RegistrablePeriodicalVaccinationDTO findRegistrableVaccinationById(Integer id);

    /**
     *Find registrable vaccination by Id
     * @return
     */
    int saveRegister(PeriodicalVaccinationRegisterDTO register);


    /**
     * Tạo mới lịch tiêm theo yêu cầu
     **/
    Vaccination registerVaccination(Vaccination vaccinationTemp);

    /**
     *get the list of all vaccine's ages
     */
    List<String> findAllVaccineAge();

    /**
     *get the list of all vaccine's ages
     */
    List<TimeDTO> findAllVaccinationTime();
    /**
     *get the total page of search data
     */
    double getTotalPage(PeriodicalSearchDataDTO searchData);

    /**
     *get the search periodical vaccination result
     */
    List<RegistrablePeriodicalVaccinationDTO> findCustomVaccination(PeriodicalSearchDataDTO searchData);

    PeriodicalVaccinationTempRegisterDTO checkRegister(PeriodicalVaccinationTempRegisterDTO register);
}
