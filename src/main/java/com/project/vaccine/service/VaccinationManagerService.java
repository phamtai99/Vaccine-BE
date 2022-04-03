package com.project.vaccine.service;

import com.project.vaccine.dto.VaccinationManagerDto;
import com.project.vaccine.entity.Vaccination;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VaccinationManagerService {
    /**
     *  lấy danh sách + phân trang + tìm kiếm
     */
    Page<Vaccination> searchAllVaccinationManager(String startDate, String name, String status, int pageable, int type);

    /**
     *  lấy danh sách + phân trang
     */
    Page<Vaccination> findAllVaccination(Pageable pageable, int type);

    /**
     * tìm id
     */
    Vaccination findByIdVaccinationManager(Integer id);

    /**
     *  thêm mới
     */
    void saveVaccinationManager(VaccinationManagerDto vaccinationManagerDto);

    /**
     *  Cập nhật lịch tiêm chủng định kỳ
     */
    void updateVaccinationManager(VaccinationManagerDto vaccinationManagerDto);

    /**
     *  Cập nhật trạng thái đã tiêm hay chưa
     */
    void updateStatusVaccinationManager(Integer id);

    /**
     *  Xóa lịch tiêm chủng định kỳ
     */
    void removeVaccinationManager(Integer id);
}
