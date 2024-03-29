package com.project.vaccine.controller;

import com.project.vaccine.dto.SearchCriteria;
import com.project.vaccine.entity.VaccinationTransaction;
import com.project.vaccine.repository.VaccinationHistoryRepository;
import com.project.vaccine.service.VaccinationTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/public")
@CrossOrigin(origins = "http://localhost:4200")
public class VaccinationTransactionController {

    @Autowired
    private VaccinationTransactionService vaccinationTransactionService;

    @Autowired
    private VaccinationHistoryRepository vaccinationHistoryRepository;

    /**
     *phân trang, tìm kiếm , hiển thị list giao dịch
     */
    @GetMapping({"/vaccine-transaction-list"})
    public ResponseEntity<?> listVaccineTransaction(@PageableDefault(size = 5) Pageable pageable) {
        return new ResponseEntity<>(vaccinationTransactionService.findAll(pageable), HttpStatus.OK);
    }

    /**
     * tìm kiếm , phân trang, hiển thị list tìm kiếm giao dịch
     */
    @PostMapping("/vaccine-transaction-search")
    public ResponseEntity<?> searchVaccineTransaction(@PageableDefault(size = 5) Pageable pageable,
                                                      @RequestBody SearchCriteria searchCriteria) {
        String keyWordForSearchNamePatient = "";
        String keyWordForSearchVaccineType = "";
        keyWordForSearchNamePatient = searchCriteria.getKeyword2();
        keyWordForSearchVaccineType = searchCriteria.getKeyword3();
        Page<VaccinationTransaction> vaccinationTransactions;
        vaccinationTransactions = vaccinationTransactionService.search(keyWordForSearchNamePatient, keyWordForSearchVaccineType, pageable);
        return new ResponseEntity<>(vaccinationTransactions, HttpStatus.OK);
    }

    /**
     *  tạo mới giao dịch
     */
    @PostMapping("/vaccine-transaction-create")
    public ResponseEntity<?> createVaccineTransaction(@RequestParam Integer idVaccineHistory,
                                                      @RequestParam double price,
                                                      @RequestParam Long quantity) {
        this.vaccinationTransactionService.save(idVaccineHistory, price, quantity);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * sửa giao dịch
     */
    @PostMapping("/vaccine-transaction-edit-by-id")
    public ResponseEntity<?> editVaccineTransaction(@RequestParam Integer id,
                                                    @RequestParam double price,
                                                    @RequestParam Long quantity) {
        this.vaccinationTransactionService.edit(id, price, quantity);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     *lấy văc xin bằng id
     */
    @GetMapping("/get-vaccine-transaction-id/{id}")
    public ResponseEntity<?> getId(@PathVariable Integer id) {
        return new ResponseEntity<>(this.vaccinationTransactionService.findById(id), HttpStatus.OK);
    }

    /**
     * xóa giao dịch bằng id
     */
    @DeleteMapping("/vaccine-transaction-delete/{id}")
    public ResponseEntity<?> deleteVaccineTransaction(@PathVariable Integer id) {
        this.vaccinationTransactionService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    /**
     * lấy 1 đối tượng vaccine history bằng id
     */
    @GetMapping("/vaccine-history-patient/{id}")
    public ResponseEntity<?> findByIdNamePatient(@PathVariable Integer id) {
        return new ResponseEntity<>(this.vaccinationHistoryRepository.findById(id), HttpStatus.OK);
    }
}
