package com.project.vaccine.service;

import com.project.vaccine.dto.SearchVaccineDTO;
import com.project.vaccine.dto.VaccinationManagerDto;
import com.project.vaccine.dto.VaccinationUpdateDTO;
import com.project.vaccine.entity.Vaccination;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VaccinationManagerService {
    /**
     *  lấy danh sách + phân trang + tìm kiếm
     */
//    Page<SearchVaccineDTO> searchAllVaccinationManager(String startDate, String name, String status, int pageable, int type);

    List<SearchVaccineDTO> searchAllVaccinationManager(String startDate, String name, String status, int pageable, int type);

    /**
     *  lấy danh sách + phân trang
     */
    Page<Vaccination> findAllVaccination(Pageable pageable, int type);

    /**
     * tìm id
     */
    Vaccination findByIdVaccinationManager(Integer id);



    SearchVaccineDTO findByIdVaccinationManagerEdit(Integer id);
    /**
     *  thêm mới
     */
    void saveVaccinationManager(VaccinationManagerDto vaccinationManagerDto);

    /**
     *  Cập nhật lịch tiêm chủng định kỳ
     */
    void updateVaccinationManager(VaccinationUpdateDTO vaccinationManagerDto);

    /**
     *  Cập nhật trạng thái đã tiêm hay chưa
     */
    void updateStatusVaccinationManager(Integer id);

    /**
     *  Xóa lịch tiêm chủng định kỳ
     */
    void removeVaccinationManager(Integer id);
}
