package com.project.vaccine.repository;


import com.project.vaccine.dto.*;
import com.project.vaccine.entity.VaccinationHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;


@Transactional
@Repository
public interface VaccinationHistoryRepository extends JpaRepository<VaccinationHistory, Integer> {

    Page<VaccinationHistory> findAllByPatient_NameContainingAndVaccination_StatusIsAndVaccination_VaccinationType_VaccinationTypeId(String name, Boolean status, Integer number, Pageable pageable);

    Page<VaccinationHistory> findAllByPatient_NameContainingAndVaccination_VaccinationType_VaccinationTypeId(String name, Integer number, Pageable pageable);

    Page<VaccinationHistory> findAllByVaccination_VaccinationType_VaccinationTypeId(Integer number, Pageable pageable);

    Page<VaccinationHistory> findAllByVaccination_Vaccine_NameContainingAndVaccination_DateContainingAndPatient_PatientIdAndDeleteFlagFalse(String vaccination_vaccine_name, String vaccination_date, int patient_id, Pageable pageable);

    @Query(value = "select patient.patient_id as patientId, patient.name as patientName, \n" +
            "patient.gender as patientGender, patient.date_of_birth as patientAge, location.name as location, \n" +
            "patient.guardian as patientGuardian, patient.address as patientAddress,   vaccination.start_time as startTime, vaccination.end_time as endTime, \n" +
            "patient.phone as patientPhone,vaccine.expired as expired,vaccine.name as name,vaccine_type.name as vaccineTypeName,\n" +
            "vaccination.date as vaccinationDate, vaccination_history.after_status as vaccineHistoryAfterStatus\n" +
            "from vaccination_history\n" +
            "inner join patient on vaccination_history.patient_id = patient.patient_id\n" +
            "inner join vaccination on vaccination_history.vaccination_id = vaccination.vaccination_id\n" +
            "inner join vaccine on vaccination.vaccine_id = vaccine.vaccine_id\n" +
            "inner join vaccine_type on vaccine.vaccine_type_id = vaccine_type.vaccine_type_id\n" +
            "inner join location on vaccination.location_id = location.location_id \n"+
            "where vaccination_history.vaccination_history_id = ?1 ", nativeQuery = true)
    VaccinationHistoryFeedbackDTO findByIdVaccinationHistory(Integer vaccinationHistoryId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE vaccination_history" +
            " SET after_status = ?2" +
            " WHERE vaccination_history_id = ?1", nativeQuery = true)
    void updateFeedbackVaccinationHistory(Integer vaccinationHistoryId, String vaccinationHistoryAfterStatus);


    @Query(value = " SELECT vc.date as vaccinationDate, vc.start_time as startTime, vc.end_time as endTime, \n" +
            "   p.name , p.guardian,p.address, p.date_of_birth as dateOfBirth, p.gender, p.phone,p.patient_id as patientId, \n" +
            "  v.name as vaccineName,vh.status , vh.vaccination_times as vaccinationTimes , vh.vaccination_history_id as vaccinationHistoryId   \n"+
            "  FROM new_vaccine_maneger.vaccination_history as vh \n"+
            "join vaccination as vc  on vh.vaccination_id = vc.vaccination_id \n" +
            " join patient as p on p.patient_id=vh.patient_id \n" +
            "join vaccine as v on v.vaccine_id=vc.vaccine_id \n"+
            "  where  vc.delete_flag=0  and  vh.delete_flag=false and   vc.vaccination_type_id=1", nativeQuery = true)
    List<VacHistoryRegisteredDTO> getListPatientRegisted();



    @Query(value = " SELECT vc.date as vaccinationDate, vc.start_time as startTime, vc.end_time as endTime, \n" +
            "   p.name , p.guardian,p.address, p.date_of_birth as dateOfBirth, p.gender, p.phone,p.patient_id as patientId, \n" +
            "  v.name as vaccineName,vh.status , vh.vaccination_times as vaccinationTimes , vh.vaccination_history_id as vaccinationHistoryId   \n"+
            "  FROM new_vaccine_maneger.vaccination_history as vh \n"+
            "join vaccination as vc  on vh.vaccination_id = vc.vaccination_id \n" +
            " join patient as p on p.patient_id=vh.patient_id \n" +
            "join vaccine as v on v.vaccine_id=vc.vaccine_id \n"+
            "  where  vc.delete_flag=0  and    vh.delete_flag=false and   vc.vaccination_type_id=1  and p.name like ?1 \n" +
            "   and vh.status is null or vh.status =false ", nativeQuery = true)
    List<VacHistoryRegisteredDTO> findPatientRegistedWithStatusFalse(String name, Boolean status);




    @Query(value = " SELECT vc.date as vaccinationDate, vc.start_time as startTime, vc.end_time as endTime, \n" +
            "   p.name , p.guardian,p.address, p.date_of_birth as dateOfBirth, p.gender, p.phone,p.patient_id as patientId, \n" +
            "  v.name as vaccineName,vh.status , vh.vaccination_times as vaccinationTimes , vh.vaccination_history_id as vaccinationHistoryId   \n"+
            "  FROM new_vaccine_maneger.vaccination_history as vh \n"+
            "join vaccination as vc  on vh.vaccination_id = vc.vaccination_id \n" +
            " join patient as p on p.patient_id=vh.patient_id \n" +
            "join vaccine as v on v.vaccine_id=vc.vaccine_id \n"+
            "  where  vc.delete_flag=0 and vh.delete_flag=false  and   vc.vaccination_type_id=2", nativeQuery = true)
    List<VacHistoryRegisteredDTO> getAllRegisteredVaccinationHisTry();


    @Query(value = " SELECT vc.date as vaccinationDate, vc.start_time as startTime, vc.end_time as endTime, \n" +
            "   p.name , p.guardian,p.address, p.date_of_birth as dateOfBirth, p.gender, p.phone,p.patient_id as patientId, \n" +
            "  v.name as vaccineName,vh.status , vh.vaccination_times as vaccinationTimes , vh.vaccination_history_id as vaccinationHistoryId   \n"+
            "  FROM new_vaccine_maneger.vaccination_history as vh \n"+
            "join vaccination as vc  on vh.vaccination_id = vc.vaccination_id \n" +
            " join patient as p on p.patient_id=vh.patient_id \n" +
            "join vaccine as v on v.vaccine_id=vc.vaccine_id \n"+
            "  where  vc.delete_flag=0  and    vh.delete_flag=false and   vc.vaccination_type_id=1  and p.name like ?1 \n" +
            "   and vh.status =true ", nativeQuery = true)
    List<VacHistoryRegisteredDTO> findPatientRegistedWithStatusTrue(String name, Boolean status);

    @Query(value = " SELECT vc.date as vaccinationDate, vc.start_time as startTime, vc.end_time as endTime, \n" +
            "   p.name , p.guardian,p.address, p.date_of_birth as dateOfBirth, p.gender, p.phone,p.patient_id as patientId, \n" +
            "  v.name as vaccineName,vh.status , vh.vaccination_times as vaccinationTimes , vh.vaccination_history_id as vaccinationHistoryId   \n"+
            "  FROM new_vaccine_maneger.vaccination_history as vh \n"+
            "join vaccination as vc  on vh.vaccination_id = vc.vaccination_id \n" +
            " join patient as p on p.patient_id=vh.patient_id \n" +
            "join vaccine as v on v.vaccine_id=vc.vaccine_id \n"+
            "  where  vc.delete_flag=0   and    vh.delete_flag=false and   vc.vaccination_type_id=1  and p.name like ?1 \n" , nativeQuery = true)
    List<VacHistoryRegisteredDTO> findPatientRegistedWithNotStatus(String name);




    @Query(value = " SELECT vc.date as vaccinationDate, vc.start_time as startTime, vc.end_time as endTime, \n" +
            "   p.name , p.guardian,p.address, p.date_of_birth as dateOfBirth, p.gender, p.phone,p.patient_id as patientId, \n" +
            "  v.name as vaccineName,vh.status , vh.vaccination_times as vaccinationTimes , vh.vaccination_history_id as vaccinationHistoryId   \n"+
            "  FROM new_vaccine_maneger.vaccination_history as vh \n"+
            "join vaccination as vc  on vh.vaccination_id = vc.vaccination_id \n" +
            " join patient as p on p.patient_id=vh.patient_id \n" +
            "join vaccine as v on v.vaccine_id=vc.vaccine_id \n"+
            "  where  vc.delete_flag=0  and   vh.delete_flag=false  and   vc.vaccination_type_id=2  and p.name like ?1 \n" +
            "   and vh.status =true ", nativeQuery = true)
    List<VacHistoryRegisteredDTO> findPatientRegistedHistryWithStatusTrue(String name, Boolean status);

    @Query(value = " SELECT vc.date as vaccinationDate, vc.start_time as startTime, vc.end_time as endTime, \n" +
            "   p.name , p.guardian,p.address, p.date_of_birth as dateOfBirth, p.gender, p.phone,p.patient_id as patientId, \n" +
            "  v.name as vaccineName,vh.status , vh.vaccination_times as vaccinationTimes , vh.vaccination_history_id as vaccinationHistoryId   \n"+
            "  FROM new_vaccine_maneger.vaccination_history as vh \n"+
            "join vaccination as vc  on vh.vaccination_id = vc.vaccination_id \n" +
            " join patient as p on p.patient_id=vh.patient_id \n" +
            "join vaccine as v on v.vaccine_id=vc.vaccine_id \n"+
            "  where  vc.delete_flag=0  and    vh.delete_flag=false  and   vc.vaccination_type_id=2  and p.name like ?1 \n" , nativeQuery = true)
    List<VacHistoryRegisteredDTO> findPatientRegistedHistryWithNotStatus(String name);

    @Query(value = " SELECT vc.date as vaccinationDate, vc.start_time as startTime, vc.end_time as endTime, \n" +
            "   p.name , p.guardian,p.address, p.date_of_birth as dateOfBirth, p.gender, p.phone,p.patient_id as patientId, \n" +
            "  v.name as vaccineName,vh.status , vh.vaccination_times as vaccinationTimes , vh.vaccination_history_id as vaccinationHistoryId   \n"+
            "  FROM new_vaccine_maneger.vaccination_history as vh \n"+
            "join vaccination as vc  on vh.vaccination_id = vc.vaccination_id \n" +
            " join patient as p on p.patient_id=vh.patient_id \n" +
            "join vaccine as v on v.vaccine_id=vc.vaccine_id \n"+
            "  where  vc.delete_flag=0  and    vh.delete_flag=false and   vc.vaccination_type_id=2  and p.name like ?1 \n" +
            "   and vh.status is null or vh.status =false  ", nativeQuery = true)
    List<VacHistoryRegisteredDTO> findPatientRegistedHistryWithStatusFalse(String name, Boolean status);






    @Query(value = "select after_status as afterStatus from vaccination_history where vaccination_history_id = ?1", nativeQuery = true)
    VaccinationHistoryGetAfterStatusDTO getAfterStatus(Integer vaccinationHistoryId);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO vaccination_history (vaccination_id, patient_id) VALUES (?1 , ?2)", nativeQuery = true)
    void savePeriodicalVaccinationRegister(Integer vaccinationId, int patientId);

    @Query(value = "select * from (select vc.date, vc.start_time as startTime, vc.end_time as endTime, vaccine.name as nameVaccine, vaccine.origin,vcs.email, vcs.name, lc.name as location from (  " +
            " select vh.status as vh_status , vh.delete_flag as dlte, vh.vaccination_id as vc_id, patient.email, patient.name    " +
            " from  vaccination_history as vh join patient on patient.patient_id = vh.patient_id where   patient.delete_flag=false ) as vcs  "
            +"   join vaccination as vc on vc.vaccination_id = vcs.vc_id  join vaccine on vc.vaccine_id=vaccine.vaccine_id  join location as lc on lc.location_id=vc.location_id " +
            "where vcs.dlte=false and  vcs.vh_status  is null or vcs.vh_status= false) as lvs where lvs.date=curdate()+1  ", nativeQuery = true)
    List<VaccinationForEmail> getAllVaccinationForEmailToSend();


    @Query(value = "select email from (select vc.vaccination_id as vaccinationId,vaccine.name as nameVaccine,vcs.email, vcs.name, lc.name as location      " +
                    "from (  select vh.status as vh_status , vh.delete_flag as dlte, vh.vaccination_id as vc_id, patient.email, patient.name    " +
                    "from  vaccination_history as vh join patient on patient.patient_id = vh.patient_id where   patient.delete_flag=false ) as vcs      " +
                    "join vaccination as vc on vc.vaccination_id = vcs.vc_id  join vaccine on vc.vaccine_id=vaccine.vaccine_id  join location as lc on lc.location_id=vc.location_id     " +
                    "where vcs.dlte=false and  vcs.vh_status  is null or vcs.vh_status= false) as lvs  where lvs.vaccinationId= ?1 "
                    , nativeQuery = true)
    List<String> getEmailForUpdateVaccination(Integer vaccinationId);
    
    @Query(value = "select email from ( " +
            "select  vc.date, patient.email " +
            "from vaccination as vc " +
            "join vaccination_history as vh  " +
            "on vc.vaccination_id = vh.vaccination_id  " +
            "join patient on patient.patient_id = vh.patient_id  " +
            "where   vh.delete_flag=false   " +
            "and  patient.delete_flag=false  " +
            "and  vh.status  is null or vh.status= false) as vcn  " +
            "where vcn.date = curdate()+1  ", nativeQuery = true)
    List<String> getAllEmailToSend();

//    @Query(value = "select email from vaccination join vaccination_history " +
//            "on vaccination.vaccination_id = vaccination_history.vaccination_id " +
//            "join patient on patient.patient_id = vaccination_history.patient_id where DATE_ADD(vaccination.date, INTERVAL vaccination.duration DAY) = curdate()+1", nativeQuery = true)
//    List<String> getEmailToSendOfVaccinationMore();


    @Query(value = "select distinct vcs.email from ( select vc.date, vaccine.duration, patient.email from vaccination as vc  " +
            "join vaccination_history as vh on vc.vaccination_id = vh.vaccination_id  " +
            "join patient on patient.patient_id = vh.patient_id  " +
            "join vaccine on vc.vaccine_id=vaccine.vaccine_id  " +
            "where   vh.delete_flag=false  and  patient.delete_flag=false  " +
            "and  vh.status  is null or vh.status= false) as vcs  " +
            " where DATE_ADD(vcs.date, INTERVAL vcs.duration DAY) = curdate()+1   "
            , nativeQuery = true)
    List<String> getEmailToSendOfVaccinationMore();

    Page<VaccinationHistory> findAllByPatient_NameContainingAndVaccination_VaccinationType_VaccinationTypeIdAndStatusIs(String name, Integer id, Boolean status, Pageable pageable);

    @Query(value = "select patient.patient_id as patientId, patient.name as patientName, patient.date_of_birth as patientDob, patient.gender as patientGender, \n" +
            " patient.guardian as patientGuardian, patient.phone as patientPhone, patient.address as patientAddress, vaccination.start_time as startTime, \n" +
            " vaccine.name as vaccineName, vaccine_type.name as vaccineTypeName, vaccination.end_time as endTime, vaccination_history.status as vaccinationHistoryStatus, vaccination_history.vaccination_times as vaccinationTimes, \n" +
            " vaccine.dosage as dosage,vaccine.expired as expired, vaccination_history.pre_status as preStatus, vaccination_history.after_status as afterStatus, vaccination_history.vaccination_history_id as vaccinationHistoryId \n" +
            " from vaccination_history \n" +
            " inner join vaccination on vaccination_history.vaccination_id = vaccination.vaccination_id \n" +
            " inner join patient on vaccination_history.patient_id = patient.patient_id \n" +
            " inner join vaccine on vaccination.vaccine_id = vaccine.vaccine_id \n" +
            " inner join vaccine_type on vaccine.vaccine_type_id = vaccine_type.vaccine_type_id" +
            " where patient.patient_id = ?1 ", nativeQuery = true)
    List<VaccinationHistoryRegisteredDTO> findId(Integer id);

    @Modifying
    @Query(
            value = "update vaccination_history set " +
                    "`status` = ?1, `pre_status` = ?2 where vaccination_history.vaccination_history_id = ?3",
            nativeQuery = true)
    void updateVaccinationHistoryStatus(Boolean status, String preStatus, Integer id);

    @Modifying
    @org.springframework.transaction.annotation.Transactional
    @Query(value = "update vaccination_history set vaccination_history.delete_flag = true where vaccination_id in  ?1 and patient_id = ?2", nativeQuery = true)
    void cancelRegister(List<Integer> vaccinationId, int patientId);

    Page<VaccinationHistory> findAllByPatient_PatientIdAndDeleteFlag(Integer patient_patientId, Boolean deleteFlag, Pageable pageable);

    Page<VaccinationHistory> findAllByPatient_PatientIdAndVaccination_Vaccine_NameContainingAndVaccination_DateAndDeleteFlag(Integer patient_patientId, String vaccination_vaccine_name, String vaccination_date, Boolean deleteFlag, Pageable pageable);

    Page<VaccinationHistory> findAllByPatient_PatientIdAndVaccination_Vaccine_NameContainingAndDeleteFlag(Integer patient_patientId, String vaccination_vaccine_name, Boolean deleteFlag, Pageable pageable);

    Page<VaccinationHistory> findAllByVaccination_Vaccine_NameContaining(String vaccination_vaccine_name, Pageable pageable);

    List<VaccinationHistory> findAllByVaccinationTransactionIsNull();

    Integer countAllByVaccination_DateAndDeleteFlag(String date, boolean b);

    Integer countAllByVaccination_DateAndStartTimeAndDeleteFlag(String date, String time, boolean b);

    List<VaccinationHistory> findAllByVaccination_VaccinationIdIs(Integer vaccinationId);

    List<VaccinationHistory> findAllByVaccination_VaccinationIdIsAndStartTimeContainsAndEndTimeContains(Integer vaccinationId, String startTime, String endTime);


    List<VaccinationHistory> findAllByVaccination_VaccinationIdIsAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(Integer vaccinationId, String startTime, String endTime);


    @Query(value = "  select vc.vaccine_id from vaccination_history vh   " +
            "join vaccination vc on vh.vaccination_id = vc.vaccination_id   " +
            "where vh.vaccination_history_id= ?1 " , nativeQuery = true)
    Integer getVaccineId(int vaccinationId);

    List<VaccinationHistory> findAllByPatient_PatientIdAndVaccination_Vaccine_NameIsAndDeleteFlagIs(Integer patientId, String vaccineName, boolean status);
}
