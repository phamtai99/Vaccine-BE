package com.project.vaccine.service.impl;

import com.project.vaccine.controller.SecurityController;
import com.project.vaccine.dto.*;
import com.project.vaccine.entity.Vaccination;
import com.project.vaccine.entity.VaccinationHistory;
import com.project.vaccine.repository.*;
import com.project.vaccine.service.VaccinationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VaccinationServiceImpl implements VaccinationService {
    private static Logger logger= LogManager.getLogger(VaccinationServiceImpl.class);


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
                    '%'+searchData.getVaccineName()+'%','%'+ searchData.getDescription()+ '%');
        } else {
            return this.vaccinationRepository.findCustomListWithPageWithDate('%'+searchData.getAge()+'%', '%'+searchData.getDate()+'%', '%'+searchData.getStartTime()+'%', '%'+searchData.getEndTime()+'%',
                    '%'+searchData.getVaccineName()+'%','%'+ searchData.getDescription()+ '%');
        }
    }

    /**
     *check available time frame and quantity for a periodical vaccination register
     */
    @Override
    public PeriodicalVaccinationTempRegisterDTO checkRegister(PeriodicalVaccinationTempRegisterDTO register) {
        Integer vaccineId = this.vaccinationRepository.getOne(register.getVaccinationId()).getVaccine().getVaccineId();

        List<VaccinationHistory> listRegisterQuantity=this.vaccinationHistoryRepository.findAllByVaccination_VaccinationIdIs(register.getVaccinationId());
        Integer registerQuantity = listRegisterQuantity.size();
        Long maximumRegister = this.storageRepository.findAllByVaccine_VaccineIdIs(vaccineId).getQuantity();
        System.out.println("maximum register : " + maximumRegister);
        register.setQuantityIsValid(registerQuantity+1 <= maximumRegister);
//        List<VaccinationHistory> listTime=this.vaccinationHistoryRepository.findAllByVaccination_VaccinationIdIsAndStartTimeContainsAndEndTimeContains(register.getVaccinationId(), register.getStartTime(), register.getEndTime());
        List<Vaccination> listTime=this.vaccinationRepository.findAllByVaccinationIdAndStartTimeEndTime(register.getVaccinationId(), register.getStartTime(), register.getEndTime());


        register.setTimeIsValid(listTime.size() > 0);
        String vaccineName = this.vaccineRepository.getOne(vaccineId).getName();

        List<VaccinationHistory> list=this.vaccinationHistoryRepository.findAllByPatient_PatientIdAndVaccination_Vaccine_NameIsAndDeleteFlagIs(register.getPatientId(), vaccineName, false);
        register.setAlreadyRegister(list.size() > 0);
        return register;
    }

    @Override
    public Integer getVaccineId(int vaccinationId) {
        Integer vaccineId= null;
        try {
            vaccineId=this.vaccinationRepository.getVaccineId(vaccinationId);
        }catch (Exception ex){
            logger.error(" Lỗi getVaccineId :" + ex);
        }
        return vaccineId;
    }

    @Override
    public List<Integer> getAllVaccinationIdbyPatientAndVaccineId(Integer patientId, Integer vaccineId) {
        List<Integer> listVaccinationId=new ArrayList<Integer>();
        try {
            listVaccinationId=this.vaccinationRepository.getVaccinationIdByVaccineId(patientId, vaccineId);
        }catch (Exception ex){
            logger.error(" Lỗi getAllVaccinationIdbyPatientAndVaccineId :"+ex);
        }
        return listVaccinationId;
    }
}
