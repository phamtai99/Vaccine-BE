package com.project.vaccine.service;

import com.project.vaccine.dto.PeriodicalVaccinationTempRegisterDTO;
import com.project.vaccine.dto.VaccinationUpdateDTO;
import com.project.vaccine.entity.Account;
import com.project.vaccine.entity.VaccinationHistory;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;


public interface AccountService {

    Account findAccountByUserName(String username);

    Integer findIdUserByUserName(String username);

    String existsByUserName(String username);

    Boolean existsById(Integer bookId);

    void addNew(String username, String password, String email) throws MessagingException, UnsupportedEncodingException;

    Boolean findAccountByVerificationCode(String code);


    Boolean findAccountByVerificationCodeToResetPassword(String code);

    void addVerificationCode(String username) throws MessagingException, UnsupportedEncodingException;


    List<Account> getAllAccount();


    void addNew(String username, String password);


    void saveNewPassword(String password,String code);

    /**
     *
     * Send info email to patient after register for a vaccination
     */
    void sendInfoEmail(PeriodicalVaccinationTempRegisterDTO register, VaccinationHistory vaccinationHistory) throws MessagingException, UnsupportedEncodingException;

    /**
     *
     * Send info email to patient after register for a vaccination
     */
    void sendInfoUpdateEmail(VaccinationUpdateDTO updateVaccination) throws MessagingException, UnsupportedEncodingException;



}
