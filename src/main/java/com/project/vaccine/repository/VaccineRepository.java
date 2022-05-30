package com.project.vaccine.repository;

import com.project.vaccine.dto.VaccineDTO;
import com.project.vaccine.dto.VaccineFindIdDTO;
import com.project.vaccine.entity.Vaccine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
@Transactional
public interface VaccineRepository extends JpaRepository<Vaccine, Integer> {

    @Query(value = "select * from vaccine where vaccine_id = ?", nativeQuery = true)
    Vaccine findByVaccineId(Integer id);

    Page<Vaccine> findAllByNameContainingAndVaccineType_NameContainingAndOriginContaining(String name, String vaccineType_name, String origin, Pageable pageable);

    Page<Vaccine> findAllByNameContainingAndVaccineType_NameContainingAndOriginContainingAndQuantityGreaterThan(String name, String vaccineType_name, String origin, Long quantity, Pageable pageable);

    Page<Vaccine> findAllByNameContainingAndVaccineType_NameContainingAndOriginContainingAndQuantityLessThan(String name, String vaccineType_name, String origin, Long quantity, Pageable pageable);

    @Query(value = "select vaccine.vaccine_id as vaccineId, vaccine.name as name, vaccine_type.name as vaccineType, vaccine.origin as origin from vaccine " +
            "join vaccine_type on vaccine.vaccine_type_id = vaccine_type.vaccine_type_id " +
            "where vaccine.vaccine_id = ?1", nativeQuery = true)
    VaccineDTO getVaccineById(Integer id);



    @Query(value = "SELECT vaccine.vaccine_id as id,vaccine.name as name,vt.name as vaccineType,vaccine.license_code as licenseCode,vaccine.origin as origin,   " +
            "  vaccine.dosage,invoice.price,  vaccine.expired , vaccine.maintenance as maintenance, vaccine.age , storage.quantity ,   " +
            " vaccine.times, vaccine.duration ,vaccine.vaccine_type_id as vaccineTypeId " +
            " FROM vaccine join vaccine_type as vt on vaccine.vaccine_type_id = vt.vaccine_type_id   "+
            " join storage on storage.vaccine_id = vaccine.vaccine_id join invoice on vaccine.vaccine_id=invoice.vaccine_id   "+
            " WHERE vaccine.delete_flag = 0 and vaccine.vaccine_id= ?1", nativeQuery = true)
    VaccineFindIdDTO getInfoVaccineById(Integer id);

    @Query(value = "select vaccine.vaccine_id as id,vaccine.name as name, vaccine_type.name as vaccineType,invoice.transaction_date as dayReceive, " +
            "vaccine.license_code as licenseCode, vaccine.origin as origin, vaccine.dosage as dosage, " +
            "vaccine.expired as expired, vaccine.maintenance as maintenance, vaccine.age as age, vaccine.duration as duration, vaccine.times as times, storage.quantity as quantity " +
            "from vaccine " +
            "left join vaccine_type on vaccine.vaccine_type_id = vaccine_type.vaccine_type_id " +
            "left join storage on storage.vaccine_id = vaccine.vaccine_id " +
            "left join invoice on invoice.vaccine_id = vaccine.vaccine_id " +
            "where vaccine.delete_flag = 0 " +
            "group by vaccine.vaccine_id limit ?1,5;", nativeQuery = true)
    List<VaccineDTO> getAllVaccineDTO(int index);

    @Query(value = "SELECT vaccine.vaccine_id as id,vaccine.name as name, vaccine_type.name as vaccineType,vaccine.times , " +
            "vaccine.license_code as licenseCode, vaccine.origin as origin, vaccine.dosage as dosage," +
            "vaccine.expired as expired, vaccine.maintenance as maintenance, vaccine.age as age, storage.quantity as quantity" +
            " FROM vaccine " +
            " join vaccine_type on vaccine.vaccine_type_id = vaccine_type.vaccine_type_id " +
            " join storage on storage.vaccine_id = vaccine.vaccine_id " +
            "WHERE vaccine.delete_flag = 0 " +
            "group by vaccine.vaccine_id", nativeQuery = true)
    List<VaccineDTO> getAllVaccineDTONotPagination();

//    @Query(value = "SELECT vaccine.vaccine_id as id,vaccine.name as name, vaccine_type.name as vaccineType,invoice.transaction_date as dayReceive, " +
//            "vaccine.license_code as licenseCode, vaccine.origin as origin, vaccine.dosage as dosage," +
//            "vaccine.expired as expired, vaccine.maintenance as maintenance, vaccine.age as age, storage.quantity as quantity " +
//            "FROM vaccine   " +
//            "left join vaccine_type on vaccine.vaccine_type_id = vaccine_type.vaccine_type_id " +
//            "left join storage on storage.vaccine_id = vaccine.vaccine_id " +
//            "left join invoice on invoice.vaccine_id = vaccine.vaccine_id " +
//            "where vaccine.name like ?1 and vaccine_type.name like ?2 and vaccine.origin like ?3 " +
//            "and vaccine.delete_flag = 0 " +
//            "group by vaccine.vaccine_id", nativeQuery = true)
//    List<VaccineDTO> search(String name, String vaccineType, String origin);


    @Query(value = "select * from ( " +
            "SELECT vaccine.vaccine_id as id,vaccine.name as name, vaccine_type.name as vaccineType,vaccine.times , " +
            "vaccine.license_code as licenseCode, vaccine.origin as origin, vaccine.dosage as dosage," +
            "vaccine.expired as expired, vaccine.maintenance as maintenance, vaccine.age as age, storage.quantity as quantity" +
            " FROM vaccine " +
            " join vaccine_type on vaccine.vaccine_type_id = vaccine_type.vaccine_type_id " +
            " join storage on storage.vaccine_id = vaccine.vaccine_id " +
            "WHERE vaccine.delete_flag = 0 " +
            "group by vaccine.vaccine_id) as vc "+
            "where vc.name like ?1  " +
            "and vc.vaccineType like ?2 " +
            " and vc.origin like ?3  ; ", nativeQuery = true)
    List<VaccineDTO> search(String name, String vaccineType, String origin);




    @Modifying
    @Query(value = "insert into vaccine(vaccine.name, vaccine.dosage, vaccine.license_code , vaccine.maintenance, " +
            "vaccine.origin, vaccine.expired, vaccine.age, vaccine.quantity, vaccine.vaccine_type_id,vaccine.duration, " +
            "vaccine.times, vaccine.delete_flag, vaccine.image)\n" +
            "value(?1, ?2, ?3, ?4, ?5,?6, ?7, ?8, ?9,?10,?11,0,?12);", nativeQuery = true)
    void createVaccine(String nameVaccine, double dosageVaccine, String licenseCode, String maintenance,
                       String origin, String expired, String age, long quantity, int vaccineTypeId,
                       int vaccineDuration, int vaccineTimes, String imgVaccine);

    @Query(value = "select * from vaccine where vaccine.name = ?1", nativeQuery = true)
    Vaccine searchName(String name);

    @Query(value = "select * from vaccine", nativeQuery = true)
    List<Vaccine> getAllVaccine();

    Page<Vaccine> findAllByDurationIsNotNull(Pageable pageable);

    @Query(value = "select * from vaccine  "+
            "join storage on vaccine.vaccine_id=storage.vaccine_id  "+
            "where storage.quantity >=1 "+
            "and vaccine.delete_flag = 0 and vaccine.expired > now() and vaccine.name like ?1  " +
            "LIMIT 12  ", nativeQuery = true)
    List<Vaccine> getAllVaccineBySuggesssion(String name);



    @Transactional
    @Modifying
    @Query(value = "update vaccine as v set v.name = ?1, v.age=?2, v.expired = ?3, v.license_code = ?4, v.maintenance = ?5, " +
            "v.origin = ?6, v.duration = ?7, v.times = ?8, v.dosage=?9 where v.vaccine_id = ?10", nativeQuery = true)
    void editVaccine(String name,String age, String expired, String licenseCode, String maintenance,
                     String origin, Integer duration, Integer times,Double dosage, Integer id);
}

