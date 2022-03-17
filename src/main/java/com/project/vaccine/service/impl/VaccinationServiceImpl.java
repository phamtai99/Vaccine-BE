package com.project.vaccine.service.impl;

import com.project.vaccine.dto.*;
import com.project.vaccine.entity.Vaccination;
import com.project.vaccine.repository.*;
import com.project.vaccine.service.VaccinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VaccinationServiceImpl implements VaccinationService {
    @Autowired
    private VaccinationRepository vaccinationRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private VaccinationHistoryRepository vaccinationHistoryRepository;
    @Autowired
    private VaccineRepository vaccineRepository;
    @Autowired
    private StorageRepository storageRepository;



    /**
     *find a periodical vaccination by Id
     */
    @Override
    public RegistrablePeriodicalVaccinationDTO findRegistrableVaccinationById(Integer id) {
        return this.vaccinationRepository.findRegistrableVaccinationById(id);
    }
    /**
     *save patient and register for periodical vaccination
     * @return
     */
    @Override
    public int saveRegister(PeriodicalVaccinationRegisterDTO register) {
        this.patientRepository.savePatient(register.getName(), register.getDateOfBirth(), register.getGender(), register.getGuardian(), register.getPhone(), register.getAddress(), register.getEmail());
        int patientId = this.patientRepository.findLatestPatientId();
        this.vaccinationHistoryRepository.savePeriodicalVaccinationRegister(register.getVaccinationId(), patientId);
        return patientId;
    }


    /**
     * Tạo mới lịch tiêm theo yêu cầu
     **/
    @Override
    public Vaccination registerVaccination(Vaccination vaccinationTemp) {
        return vaccinationRepository.save(vaccinationTemp);
    }

    /**
     *get the list of all vaccine's ages
     */
    @Override
    public List<String> findAllVaccineAge() {
        return this.vaccinationRepository.findAllAge();
    }

    /**
     *find all available vaccination time stamps
     */
    @Override
    public List<TimeDTO> findAllVaccinationTime() {
        return this.vaccinationRepository.findAllTimeStamp();
    }

    /**
     *get the total page of search data
     */
    @Override
    public double getTotalPage(PeriodicalSearchDataDTO searchData) {
        if (searchData.getDate().equals("")) {
            return Math.ceil((double) this.vaccinationRepository.findTotalPage('%'+searchData.getAge()+'%', '%'+searchData.getStartTime()+'%', '%'+searchData.getEndTime()+'%',
                    '%'+searchData.getVaccineName()+'%', '%'+ searchData.getDescription()+ '%')/5);
        }
        return Math.ceil( (double) this.vaccinationRepository.findTotalPage('%'+searchData.getAge()+'%', '%'+ searchData.getDate() +'%', '%'+searchData.getStartTime()+'%', '%'+searchData.getEndTime()+'%',
                '%'+searchData.getVaccineName()+'%', '%'+ searchData.getDescription()+ '%')/5);
    }

    /**
     *get list of periodical vaccination with custom search and page
     */
    @Override
    public List<RegistrablePeriodicalVaccinationDTO> findCustomVaccination(PeriodicalSearchDataDTO searchData) {
        if (searchData.getDate().equals("")) {
            return this.vaccinationRepository.findCustomListWithPageWithoutDate('%'+searchData.getAge()+'%', '%'+searchData.getStartTime()+'%', '%'+searchData.getEndTime()+'%',
                    '%'+searchData.getVaccineName()+'%','%'+ searchData.getDescription()+ '%', (searchData.getCurrentPage()-1)*5);
        } else {
            return this.vaccinationRepository.findCustomListWithPageWithDate('%'+searchData.getAge()+'%', '%'+searchData.getDate()+'%', '%'+searchData.getStartTime()+'%', '%'+searchData.getEndTime()+'%',
                    '%'+searchData.getVaccineName()+'%','%'+ searchData.getDescription()+ '%', (searchData.getCurrentPage()-1)*5);
        }
    }
    /**
     *check available time frame and quantity for a periodical vaccination register
     */
    @Override
    public PeriodicalVaccinationTempRegisterDTO checkRegister(PeriodicalVaccinationTempRegisterDTO register) {
        Integer vaccineId = this.vaccinationRepository.getOne(register.getVaccinationId()).getVaccine().getVaccineId();
        Integer registerQuantity = this.vaccinationHistoryRepository.findAllByVaccination_VaccinationIdIs(register.getVaccinationId()).size();
        Long maximumRegister = this.storageRepository.findAllByVaccine_VaccineIdIs(vaccineId).getQuantity();
        System.out.println("maximum register : " + maximumRegister);
        register.setQuantityIsValid(registerQuantity+1 <= maximumRegister);
        register.setTimeIsValid(this.vaccinationHistoryRepository.findAllByVaccination_VaccinationIdIsAndStartTimeContainsAndEndTimeContains(register.getVaccinationId(), register.getStartTime(), register.getEndTime()).size()+1 < 3);
        String vaccineName = this.vaccineRepository.getOne(vaccineId).getName();
        register.setAlreadyRegister(this.vaccinationHistoryRepository.findAllByPatient_PatientIdAndVaccination_Vaccine_NameIsAndDeleteFlagIs(register.getPatientId(), vaccineName, false).size() > 0);
        return register;
    }
}
