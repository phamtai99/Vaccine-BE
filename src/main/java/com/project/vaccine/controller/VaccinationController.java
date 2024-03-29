package com.project.vaccine.controller;

import com.project.vaccine.dto.*;
import com.project.vaccine.entity.VaccinationHistory;
import com.project.vaccine.payload.request.VerifyRequest;
import com.project.vaccine.service.AccountService;
import com.project.vaccine.service.VaccinationHistoryService;
import com.project.vaccine.service.VaccinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/public/vaccination")
public class VaccinationController {
    @Autowired
    private VaccinationService vaccinationService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private VaccinationHistoryService vaccinationHistoryService;


    /**
     *display list of registrable periodical vaccinations
     */
    @GetMapping("/register-list/{id}")
    public ResponseEntity<RegistrablePeriodicalVaccinationDTO> findAllRegistrablePeriodicalVaccination(@PathVariable Integer id) {
        RegistrablePeriodicalVaccinationDTO registrableVaccination = this.vaccinationService.findRegistrableVaccinationById(id);
        if (registrableVaccination == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(registrableVaccination, HttpStatus.OK);
    }
    /**
     * Method for saving patient and register for periodical vaccination
     */
    @PostMapping("/register-patient")
    public ResponseEntity<Boolean> savePeriodicalVaccinationRegister(@RequestBody PeriodicalVaccinationTempRegisterDTO register) throws UnsupportedEncodingException, MessagingException {
        VaccinationHistory vaccinationHistory = this.vaccinationHistoryService.createNewRegister(register);
//        this.vaccinationHistoryService.createNewRegister(register);
                this.accountService.sendInfoEmail(register,vaccinationHistory);
                return new ResponseEntity<>(true, HttpStatus.CREATED);
    }
    /**
     *get the list of all vaccine's ages
     */
    @GetMapping("/age-list")
    public ResponseEntity<List<String>> findAllVaccineAge() {
        List<String> ageList = this.vaccinationService.findAllVaccineAge();
        return new ResponseEntity<>(ageList, HttpStatus.OK);
    }

    /**
     *get the list of all vaccination's time
     */
    @GetMapping("/time-list")
    public ResponseEntity<List<TimeDTO>> findAllVaccinationTime() {
        List<TimeDTO> timeList = this.vaccinationService.findAllVaccinationTime();
        return new ResponseEntity<>(timeList, HttpStatus.OK);
    }
    /**
     *get the total page of search
     */
    @PostMapping("/get-total-page")
    public ResponseEntity<Integer> findTotalPage(@RequestBody PeriodicalSearchDataDTO searchData) {
        return new ResponseEntity<>((int) this.vaccinationService.getTotalPage(searchData), HttpStatus.OK);
    }
    /**
     *get the search periodical vaccination result
     */
    @PostMapping("/get-custom-list")
    public ResponseEntity<List<RegistrablePeriodicalVaccinationDTO>> findCustomList(@RequestBody PeriodicalSearchDataDTO searchData) {
        List<RegistrablePeriodicalVaccinationDTO> registrableVaccinationList = this.vaccinationService.findCustomVaccination(searchData);
        if (registrableVaccinationList.size() == 0) {
            return new ResponseEntity<>(registrableVaccinationList,HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(registrableVaccinationList, HttpStatus.OK);
    }
    /**
    *
    */
    @PostMapping("/cancel")
    public void VerifyCancel(@RequestBody VerifyRequest code) {
        System.out.println(code.getCode());
        int vaccinationId = Integer.parseInt(code.getCode().substring(0,code.getCode().indexOf("|")));
        System.out.println(vaccinationId);
        int patientId = Integer.parseInt(code.getCode().substring(code.getCode().indexOf("|")+1));
        System.out.println(patientId);
        this.vaccinationHistoryService.cancelRegister(vaccinationId, patientId);
    }

    /**
     * Method for saving patient and register for periodical vaccination
     */
    @PostMapping("/check-register")
    public ResponseEntity<PeriodicalVaccinationTempRegisterDTO> checkPeriodicalVaccinationRegister(@RequestBody PeriodicalVaccinationTempRegisterDTO register) {
        System.out.println(register.getPatientId());
        System.out.println(register.getVaccinationId());
        return new ResponseEntity<>(this.vaccinationService.checkRegister(register), HttpStatus.OK);
    }
}
