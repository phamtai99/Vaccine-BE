package com.project.vaccine.repository;

import com.project.vaccine.dto.SearchVaccineDTO;
import com.project.vaccine.entity.Vaccination;
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
public interface VaccinationManagerRepository extends JpaRepository<Vaccination, Integer> {


    /**
     * Phân trang có điều kiện về biến delete và vaccinationType và hiển thị danh sách
     */
    Page<Vaccination> findAllByDeleteFlagIsFalseAndVaccinationType_VaccinationTypeIdIs(Pageable pageable, int type);

    /**
     *  Query tìm id
     */

    @Query(
            value = "select e.* from Vaccination e where e.vaccination_id = ?1 and e.delete_flag = ?2 and e.vaccination_type_id = ?3",
            nativeQuery = true)
    Vaccination findByIdVaccination(int vaccinationId, int delete, int type);

    /**
     * Query tạo mới
     */

    @Modifying
    @Query(
            value = "insert into vaccination(`date`, delete_flag, description, start_time, end_time, status, location_id, vaccination_type_id, vaccine_id) " +
                    "value (?1, ?2, ?3,?4, ?5, ?6, ?7, ?8, ?9)",
            nativeQuery = true)
    void saveVaccinationManager(String date, boolean delete, String description, String startTime, String endTime, boolean status, int location, int type, int vaccine);

    /**
     * Query tạo cập nhập
     */

    @Modifying
    @Query(
            value = "update vaccination set " +
                    "`date` = ?1, description = ?2, start_time = ?3, end_time = ?4," +
                    " location_id = ?5 " +
                    "where vaccination_id = ?6",
            nativeQuery = true)
    void updateVaccinationManager(String date, String description, String startTime, String endTime, String locationId, int vaccinationId);

    /**
     *  Query xóa tạm thời thông tin một sự kiện tiêm chủng dự phòng
     */
    @Modifying
    @Query(
            value = "update vaccination set delete_flag = ?1 where vaccination_id = ?2", nativeQuery = true)
    void deleteVaccinationById(boolean delete, int id);

    /**
     * * Query cập nhập một sự kiện tiêm chủng dự phòng đã hoàn thành
     */
    @Modifying
    @Query(
            value = "update vaccination set status = ?1 where vaccination_id = ?2", nativeQuery = true)
    void updateVaccinationById(boolean status, int id);

    /**
     *  Tìm kiếm cùng với phân trang ;
     */
//    @Query(
//            value = "select vc.* from vaccine_management.vaccination as vc " +
//                    "join vaccine on vc.vaccine_id = vaccine.vaccine_id " +
//                    "where vc.delete_flag = 0 " +
//                    "and vc.vaccination_type_id = 1 " +
//                    "and vc.date like ?1 " +
//                    "and vaccine.name like ?2 " +
//                    "and vc.status like ?3 " +
//                    "order by vc.vaccination_id", nativeQuery = true)
//    List<SearchVaccineDTO> searchAllList(String startDate, String name, String status);
    @Query(
            value = "SELECT vc.vaccination_id as vaccinationId, vc.start_time as startTime,vc.status, vc.date, vc.end_time as endTime,vc.description,  " +
                    "location.name as location, vaccine.name ,vaccine.times, vaccine.age  , vaccine_type.name as type, vc.location_id as locationId  " +
                    "FROM new_vaccine_maneger.vaccination as vc  " +
                    " join vaccine on vc.vaccine_id = vaccine.vaccine_id  " +
                    " join location on vc.location_id= location.location_id  "+
                    " join vaccine_type on vaccine.vaccine_type_id=vaccine_type.vaccine_type_id   "+
                    "where vc.delete_flag = 0   " +
                    "and vc.vaccination_type_id = 1  "+
                    "and vc.date like ?1   " +
                    "and vaccine.name like ?2 "  +
                    "and vc.status = ?3   " +
                    "order by vc.vaccination_id  ", nativeQuery = true)
    List<SearchVaccineDTO> searchAllList(String startDate, String name, String status);


    @Query(
            value = "SELECT vc.vaccination_id as vaccinationId, vc.start_time as startTime,vc.status, vc.date, vc.end_time as endTime,vc.description,  " +
                    "location.name as location, vaccine.name ,vaccine.times, vaccine.age  , vaccine_type.name as type,  vc.location_id as locationId   " +
                    "FROM new_vaccine_maneger.vaccination as vc  " +
                    " join vaccine on vc.vaccine_id = vaccine.vaccine_id  " +
                    " join location on vc.location_id= location.location_id  "+
                    " join vaccine_type on vaccine.vaccine_type_id=vaccine_type.vaccine_type_id   "+
                    "where vc.delete_flag = 0   " +
                    "and vc.vaccination_type_id = 1  "+
                    "and vc.vaccination_id = ?1   ", nativeQuery = true)
    SearchVaccineDTO findByIdVaccination(Integer id);





    @Query(
            value = "SELECT vc.vaccination_id as vaccinationId, vc.start_time as startTime,vc.status, vc.date, vc.end_time as endTime,vc.description,  " +
                    "location.name as location, vaccine.name ,vaccine.times, vaccine.age  , vaccine_type.name as type,  vc.location_id as locationId  " +
                    "FROM new_vaccine_maneger.vaccination as vc  " +
                    " join vaccine on vc.vaccine_id = vaccine.vaccine_id  " +
                    " join location on vc.location_id= location.location_id  "+
                    " join vaccine_type on vaccine.vaccine_type_id=vaccine_type.vaccine_type_id   "+
                    "where vc.delete_flag = 0   " +
                    "and vc.vaccination_type_id = 1  "+
                    "and vc.date like ?1   " +
                    "and vaccine.name like ?2 "  +
                    "and vc.status like ?3   " +
                    "order by vc.vaccination_id  ", nativeQuery = true)
    List<SearchVaccineDTO> searchAllListStatusNull(String startDate, String name, String status);


}
