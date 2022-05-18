package com.project.vaccine.controller;

import com.project.vaccine.dto.*;
import com.project.vaccine.entity.Storage;
import com.project.vaccine.entity.VaccinationHistory;
import com.project.vaccine.service.StorageService;
import com.project.vaccine.service.VaccinationHistoryService;
import com.project.vaccine.service.VaccinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")

@RequestMapping("/api/public")
public class VaccinationHistoryController {

    @Autowired
    private VaccinationHistoryService vaccinationHistoryService;

    @Autowired
    private StorageService storageService;

    @Autowired
    private VaccinationService vaccinationService;


//    @RequestMapping(value = "/vaccination-historyssss", method = RequestMethod.GET)
//    public ResponseEntity<List<VaccinationForEmail>> findAllVaccinationHistoryforer() {
//
//        List<VaccinationForEmail> vaccinationForEmailList= vaccinationHistoryService.getAllVaccinationForEmail();
//        List<String> listEmail=new ArrayList<String>();
//
//        String location =vaccinationForEmailList.get(0).getLocation();
//        String vaccineName=vaccinationForEmailList.get(0).getNameVaccine();
//        String vaccineOrigin=vaccinationForEmailList.get(0).getOrigin();
//        String startTime=vaccinationForEmailList.get(0).getStartTime();
//        String endTime=vaccinationForEmailList.get(0).getEndTime();
//        for (int i=0;i<vaccinationForEmailList.size();i++){
//                listEmail.add(vaccinationForEmailList.get(i).getEmail());
//        }
//
//        return new ResponseEntity<List<VaccinationForEmail>>(vaccinationForEmailList, HttpStatus.OK);
//    }

    /**
     *
     * lấy danh lịch sử tiêm chủng, phân trang , tìm kiếm
     **/
    @RequestMapping(value = "/vaccination-history", method = RequestMethod.GET)
    public ResponseEntity<Page<VaccinationHistory>> findAllVaccinationHistory(@PageableDefault(size =10) Pageable pageable,
                                                                              @RequestParam(defaultValue = "") String vaccineName,
                                                                              @RequestParam(defaultValue = "") String vaccinationDate,
                                                                              @RequestParam(defaultValue = "") Integer patientId) {
        Page<VaccinationHistory> vaccinationHistories;

        if (vaccineName.isEmpty() && vaccinationDate.isEmpty()) {
            vaccinationHistories = this.vaccinationHistoryService.getAllVaccinationHistory(vaccineName, vaccinationDate, patientId, pageable);
        }
        vaccinationHistories = this.vaccinationHistoryService.getAllVaccinationHistory(vaccineName, vaccinationDate, patientId, pageable);
        if (vaccinationHistories == null) {
            return new ResponseEntity<Page<VaccinationHistory>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Page<VaccinationHistory>>(vaccinationHistories, HttpStatus.OK);
    }

    /**
     *
     *Lấy danh sách bệnh nhân đăng kí tiêm chủng định kỳ của chung tâm
     * @return
     */
    @RequestMapping(value = "/periodic-vaccination/list", method = RequestMethod.GET)
    public ResponseEntity<Page<VaccinationHistory>> findAllPeriodicVaccination(@PageableDefault(size = 10) Pageable
                                                                                       pageable) {
        Page<VaccinationHistory> list = vaccinationHistoryService.finAllPeriodicVaccination(pageable);
        if (list.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }


    @RequestMapping(value = "/periodic-vaccination/listPatientRegistered", method = RequestMethod.GET)
    public ResponseEntity< List<VacHistoryRegisteredDTO>> findAllPeriodicVaccinations() {
        List<VacHistoryRegisteredDTO> list = vaccinationHistoryService.finAllPeriodicVaccinations();
        if (list.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }


    @RequestMapping(value = "/periodic-vaccination/search", method = RequestMethod.GET)
    public ResponseEntity<Page<VaccinationHistory>> searchPeriodicVaccination(@PageableDefault(size = 5) Pageable
                                                                                      pageable,
                                                                              @RequestParam(defaultValue = "") String name,
                                                                              @RequestParam(defaultValue = "") String status) {
        Page<VaccinationHistory> list = null;
        Boolean statusNew;
        if (status.equals("")) {
            list = vaccinationHistoryService.searchNoStatusPeriodicVaccination(name, pageable);
        } else if (status.equals("true")) {
            statusNew = true;
            list = vaccinationHistoryService.searchPeriodicVaccination(name, statusNew, pageable);
        } else if (status.equals("false")) {
            statusNew = false;
            list = vaccinationHistoryService.searchPeriodicVaccination(name, statusNew, pageable);
        }
        if (list.isEmpty()) {
            return new ResponseEntity<>(list, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }


    @RequestMapping(value = "/periodic-vaccination/searchPatientRegistered", method = RequestMethod.GET)
    public ResponseEntity<List<VacHistoryRegisteredDTO>> searchPeriodicVaccinationRegistered(
                                                                              @RequestParam(defaultValue = "") String name,
                                                                              @RequestParam(defaultValue = "") String status) {
        List<VacHistoryRegisteredDTO> list = null;
        Boolean statusNew;
        if (status.equals("")) {
            list = vaccinationHistoryService.searchNoStatusPeriodicVaccinationNotStatus(name );
        } else if (status.equals("true")) {
            statusNew = true;
            list = vaccinationHistoryService.searchPeriodicVaccinationRegisteredWithStatusTrue(name, statusNew );
        } else if (status.equals("false")) {
            statusNew = false;
            list = vaccinationHistoryService.searchPeriodicVaccinationRegisteredWithStatusFalse(name, statusNew);
        }
        if (list.isEmpty()) {
            return new ResponseEntity<>(list, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }



    /**
     *
     * findById lịch sử tiêm chủng
     **/
    @RequestMapping(value = "/vaccination-history/feedback/{vaccinationHistoryId}", method = RequestMethod.GET)
    public ResponseEntity<VaccinationHistoryFeedbackDTO> findByIdVaccinationHistory(@PathVariable Integer vaccinationHistoryId) {
        VaccinationHistoryFeedbackDTO list = this.vaccinationHistoryService.findByIdVaccinationHistory(vaccinationHistoryId);
        if (list == null) {
            return new ResponseEntity<VaccinationHistoryFeedbackDTO>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<VaccinationHistoryFeedbackDTO>(list, HttpStatus.OK);
    }

    /**
     *
     * lấy trạng thái sau khi tiêm chủng lịch sử tiêm chủng
     **/
    @RequestMapping(value = "/vaccination-history/feedback/getAfterStatus/{vaccinationHistoryId}", method = RequestMethod.GET)
    public ResponseEntity<VaccinationHistoryGetAfterStatusDTO> getAfterStatusVaccinationHistory(@PathVariable Integer vaccinationHistoryId) {
        VaccinationHistoryGetAfterStatusDTO afterStatus = this.vaccinationHistoryService.getAfterStatusVaccinationHistory(vaccinationHistoryId);
        if (afterStatus == null) {
            return new ResponseEntity<VaccinationHistoryGetAfterStatusDTO>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<VaccinationHistoryGetAfterStatusDTO>(afterStatus, HttpStatus.OK);
    }

    /**
     *
     * phản hồi sau khi tiêm chủng
     **/
    @RequestMapping(value = "/vaccination-history/feedback/sendFeedback/{vaccinationHistoryId}", method = RequestMethod.PUT)
    public ResponseEntity<Void> feedbackVaccinationHistory(
            @RequestBody VaccinationHistorySendFeedbackDTO vaccinationHistorySendFeedbackDTO,
            @PathVariable Integer vaccinationHistoryId) {

        this.vaccinationHistoryService.updateVaccinationHistory(vaccinationHistoryId, vaccinationHistorySendFeedbackDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    /**
     *  lấy list vaccine history
     */
    @RequestMapping(value = "/vaccination-history-list", method = RequestMethod.GET)
    public ResponseEntity<?> getListVaccinationHistory() {
        List<VaccinationHistory> list = this.vaccinationHistoryService.findAllByVaccinationTransactionIsNull();
        if (list == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }



    @RequestMapping(value = "/registered-for-vaccination/list", method = RequestMethod.GET)
    public ResponseEntity<Page<VaccinationHistory>> getAllRegisteredVaccination(@PageableDefault(size = 8) Pageable pageable,
                                                                                @RequestParam(defaultValue = "") String name,
                                                                                @RequestParam Integer id) {
        Page<VaccinationHistory> list = vaccinationHistoryService.getAllRegisteredRequired(name, id, pageable);

        if (list.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
//    Lấy danh sách bệnh nhân đăng kí tiêm chủng theo yêu cầu
    @RequestMapping(value = "/registered-for-vaccination/AllRegisteredVaccinationHisTry", method = RequestMethod.GET)
    public ResponseEntity<List<VacHistoryRegisteredDTO>> getAllRegisteredVaccinationHistory( ) {
        List<VacHistoryRegisteredDTO> list = vaccinationHistoryService.getAllRegisteredRequiredHistory();

        if (list.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }



    /**
     * search and paging
     **/
    @RequestMapping(value = "/registered-for-vaccination/search", method = RequestMethod.GET)
    public ResponseEntity<Page<VaccinationHistory>> searchRegisteredVaccination(@PageableDefault(size = 5) Pageable pageable,
                                                                                @RequestParam(defaultValue = "") String name,
                                                                                @RequestParam(defaultValue = "") String status,
                                                                                @RequestParam Integer id) {
        Page<VaccinationHistory> list = null;
        Boolean statusNew;
        if (status.equals("")) {
            list = vaccinationHistoryService.getAllRegisteredRequired(name,id, pageable);
        } else if (status.equals("true")) {
            statusNew = true;
            list = vaccinationHistoryService.searchNameAndInjected(name,id, statusNew, pageable);
        } else if (status.equals("false")) {
            statusNew = false;
            list = vaccinationHistoryService.searchNameAndInjected(name,id, statusNew, pageable);
        }
        if (list.isEmpty()) {
            return new ResponseEntity<>(list, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }



//  Tìm kiếm danh sách bênh nhân đăng kí theo yêu cầu ko phân trang
    @RequestMapping(value = "/registered-for-vaccination/searchRegisteredVaccination", method = RequestMethod.GET)
    public ResponseEntity<List<VacHistoryRegisteredDTO>> searchRegisteredVaccination(  @RequestParam(defaultValue = "") String name,
                                                                                @RequestParam(defaultValue = "") String status ) {
        List<VacHistoryRegisteredDTO> list = null;
        Boolean statusNew;
        if (status.equals("")) {
            list = vaccinationHistoryService.searchVaccinationRegisteredNotStatus(name);
        } else if (status.equals("true")) {
            statusNew = true;
            list = vaccinationHistoryService.searchVaccinationRegisteredWithStatusTrue(name, statusNew);
        } else if (status.equals("false")) {
            statusNew = false;
            list = vaccinationHistoryService.searchVaccinationRegisteredWithStatusFalse(name, statusNew );
        }
        if (list.isEmpty()) {
            return new ResponseEntity<>(list, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }


    /**
     * find by id
     **/
    @GetMapping("/registered-for-vaccination/view/{id}")
    public ResponseEntity<List<VaccinationHistoryRegisteredDTO>> viewVaccinationHistory(@PathVariable Integer id) {
        List<VaccinationHistoryRegisteredDTO> vaccinationHistoryRegisteredDTO = vaccinationHistoryService.findId(id);
        if (vaccinationHistoryRegisteredDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(vaccinationHistoryRegisteredDTO, HttpStatus.OK);
    }

    /**

     * edit by id
     **/
    @RequestMapping(value = "/registered-for-vaccination/edit", method = RequestMethod.GET)
    public ResponseEntity<VaccinationHistoryRegisteredDTO> updateVaccination(@RequestParam("id") Integer id,
                                                                             @RequestParam String preStatus) {
        List<VaccinationHistoryRegisteredDTO> vaccinationHistory = vaccinationHistoryService.findId(id);
        if (vaccinationHistory == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        int vaccineId= this.vaccinationHistoryService.getVaccineId(id);
        Storage storageList = storageService.getStorage(vaccineId);
        if (storageList.getQuantity() > 1){
            storageList.setQuantity(storageList.getQuantity() - 1);
            storageService.saveStorage(storageList);
        }else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        vaccinationHistoryService.updateStatusVaccinationHistory(true,preStatus,id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * sendmail feedback for admin
     **/
    @RequestMapping(value = "/sendMailFeedbackForAdmin", method = RequestMethod.POST)
    public ResponseEntity<Void> sendMailCo(@RequestParam(defaultValue = "") String value,
                                           @RequestParam(defaultValue = "") String accountEmail) throws UnsupportedEncodingException, MessagingException {
        this.vaccinationHistoryService.sendMailFeedbackForAdmin(value, accountEmail);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
