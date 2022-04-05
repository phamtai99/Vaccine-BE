package com.project.vaccine.service.impl;

import com.project.vaccine.controller.SecurityController;
import com.project.vaccine.dto.SearchVaccineDTO;
import com.project.vaccine.dto.VaccinationManagerDto;
import com.project.vaccine.entity.Vaccination;
import com.project.vaccine.repository.VaccinationManagerRepository;
import com.project.vaccine.service.VaccinationManagerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VaccinationManagerServiceImpl implements VaccinationManagerService {

    private static Logger logger= LogManager.getLogger(SecurityController.class);
    @Autowired
    private VaccinationManagerRepository vaccinationManagerRepository;

    /**
     *  Lấy danh sách vaccination phân trang
     */
    @Override
    public Page<Vaccination> findAllVaccination(Pageable pageable, int type) {
        return vaccinationManagerRepository.findAllByDeleteFlagIsFalseAndVaccinationType_VaccinationTypeIdIs(pageable, type);
    }

    /**
     * tìm kiếm và phân trang
     */
//    @Override
//    public Page<SearchVaccineDTO> searchAllVaccinationManager(String Date,  String name, String status, int pageable, int type) {
//        List<SearchVaccineDTO> vaccinationList=new ArrayList<SearchVaccineDTO>();
//        try{
//          vaccinationList = vaccinationManagerRepository.searchAllList('%' +Date+ '%', '%' + name + '%','%' + status+ '%');
//
//        }catch( Exception ex){
//           logger.error("loi search :"+ ex);
//        }
//
//        Pageable pageableContent = PageRequest.of(pageable, 5);
//        int startPage = (int) pageableContent.getOffset();
//        int endPage = Math.min((startPage + pageableContent.getPageSize()), vaccinationList.size());
//        Page<SearchVaccineDTO> vaccinationPage = new PageImpl<SearchVaccineDTO>(vaccinationList.subList(startPage, endPage), pageableContent, vaccinationList.size());
//        return vaccinationPage;
//    }

    @Override
    public List<SearchVaccineDTO> searchAllVaccinationManager(String Date,  String name, String status, int pageable, int type) {
        List<SearchVaccineDTO> vaccinationList=new ArrayList<SearchVaccineDTO>();
        try{
            if(status.isEmpty()|| status.equals("")){
                vaccinationList = vaccinationManagerRepository.searchAllListStatusNull('%' +Date+ '%', '%' + name + '%','%' +status+ '%');
            }else{
                vaccinationList = vaccinationManagerRepository.searchAllList('%' +Date+ '%', '%' + name + '%',status);

            }

        }catch( Exception ex){
            logger.error("loi search :"+ ex);
        }

//        Pageable pageableContent = PageRequest.of(pageable, 5);
//        int startPage = (int) pageableContent.getOffset();
//        int endPage = Math.min((startPage + pageableContent.getPageSize()), vaccinationList.size());
//        Page<SearchVaccineDTO> vaccinationPage = new PageImpl<SearchVaccineDTO>(vaccinationList.subList(startPage, endPage), pageableContent, vaccinationList.size());
        return vaccinationList;
    }


    /**
     * Lấy vaccination theo ID
     */
    @Override
    public Vaccination findByIdVaccinationManager(Integer id) {
        return vaccinationManagerRepository.findByIdVaccination(id, 0, 1);
    }

    /**
     * Thêm mới vaccination
     */
    @Override
    public void saveVaccinationManager(VaccinationManagerDto vaccinationManagerDto) {
        vaccinationManagerRepository.saveVaccinationManager(vaccinationManagerDto.getDate(), false, vaccinationManagerDto.getDescription(), vaccinationManagerDto.getStartTime(), vaccinationManagerDto.getEndTime(), false, 1, 1, vaccinationManagerDto.getVaccineId());
    }

    /**
     * cập nhật vaccination
     */
    @Override
    public void updateVaccinationManager(VaccinationManagerDto vaccinationManagerDto) {
        try{
             vaccinationManagerRepository.updateVaccinationManager(vaccinationManagerDto.getDate(), vaccinationManagerDto.getDescription(),
                    vaccinationManagerDto.getStartTime(), vaccinationManagerDto.getEndTime(),
                    vaccinationManagerDto.getVaccineId(), vaccinationManagerDto.getVaccinationId());

        }catch(Exception ex){
            logger.error("Lỗi cập nhật lịch tiêm chủng định kì : "+ ex);
        }
    }


    /**
     * cập nhật vaccination trạng thái đã hoàn thành hay chưa
     */
    @Override
    public void updateStatusVaccinationManager(Integer id) {
        vaccinationManagerRepository.updateVaccinationById(true, id);
    }

    /**
     *  xóa vaccination qua biến delete_flag
     */
    @Override
    public void removeVaccinationManager(Integer id) {
        vaccinationManagerRepository.deleteVaccinationById(true, id);
    }
}
