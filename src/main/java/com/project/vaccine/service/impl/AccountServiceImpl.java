package com.project.vaccine.service.impl;

import com.project.vaccine.dto.PeriodicalVaccinationTempRegisterDTO;
import com.project.vaccine.entity.Account;
import com.project.vaccine.entity.Patient;
import com.project.vaccine.entity.Vaccination;
import com.project.vaccine.entity.VaccinationHistory;
import com.project.vaccine.repository.AccountRepository;
import com.project.vaccine.repository.PatientRepository;
import com.project.vaccine.repository.VaccinationHistoryRepository;
import com.project.vaccine.repository.VaccinationRepository;
import com.project.vaccine.service.AccountService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.List;


@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    private VaccinationRepository vaccinationRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private VaccinationHistoryRepository vaccinationHistoryRepository;

    @Override
    public Account findAccountByUserName(String username) {
        return accountRepository.findAccountByUserName(username);
    }

    @Override
    public Integer findIdUserByUserName(String username) {
        return accountRepository.findIdUserByUserName(username);
    }

    @Override
    public String existsByUserName(String username) {
        return accountRepository.existsByUserName(username);
    }


    @Override
    public Boolean existsById(Integer bookId) {
        return accountRepository.existsById(bookId);
    }


    @Override
    public void addNew(String username, String password) {
//        accountRepository.addNewAccount(username, password);
        String randomCode = RandomString.make(64);
        accountRepository.addNew(username, password, true, randomCode, "admin@gmail.com");
    }

    @Override
    public void saveNewPassword(String password,String code) {
        accountRepository.saveNewPassword(password,code);
    }

    @Override
    public void addNew(String username, String password, String email) throws MessagingException, UnsupportedEncodingException {
        String randomCode = RandomString.make(64);
        accountRepository.addNew(username, password, true, randomCode, email);
        sendVerificationEmail(username, randomCode, email);
    }

    @Override
    public Boolean findAccountByVerificationCode(String code) {
        Account account = accountRepository.findAccountByVerificationCode(code);
        if (account == null || account.getEnabled()) {
            return false;
        } else {
            account.setEnabled(true);
            account.setVerificationCode(null);
            accountRepository.save(account);
            return true;
        }
    }

    @Override
    public Boolean findAccountByVerificationCodeToResetPassword(String code) {
        Account account = accountRepository.findAccountByVerificationCode(code);
        return account != null;
    }

    @Override
    public void addVerificationCode(String username) throws MessagingException, UnsupportedEncodingException {
        String code = RandomString.make(64);
        accountRepository.addVerificationCode(code, username);
        Account account = accountRepository.findAccountByVerificationCode(code);
        this.sendVerificationEmailForResetPassWord(account.getUserName(), code, account.getEmail());
    }

    public void sendVerificationEmail(String userName, String randomCode, String email) throws MessagingException, UnsupportedEncodingException {
        String subject = "Hãy xác thực email của bạn";
        String mailContent = "";
        String confirmUrl = "http://localhost:4200/verification?code=" + randomCode;


        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
        helper.setTo(email);
        helper.setFrom("taipt3351@gmail.com","TRUNG TÂM Y TẾ DỰ PHÒNG HÀ NỘI");
        helper.setSubject(subject);
        mailContent = "<p sytle='color:red;'>Xin chào " + userName + " ,<p>" + "<p> Nhấn vào link sau để xác thực email của bạn:</p>" +
                "<h3><a href='" + confirmUrl + "'>Link Xác thực( nhấn vào đây)!</a></h3>" +
                "<p>TRUNG TÂM Y TẾ DỰ PHÒNG HÀ NỘI</p>";
        helper.setText(mailContent, true);
        javaMailSender.send(message);
    }

    public void sendVerificationEmailForResetPassWord(String userName, String randomCode, String email) throws MessagingException, UnsupportedEncodingException {
        String subject = "Hãy xác thực email của bạn";
        String mailContent = "";
        String confirmUrl = "http://localhost:4200/verify-reset-password?code=" + randomCode;


        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
        helper.setFrom("taipt3351@gmail.com","TRUNG TÂM Y TẾ DỰ PHÒNG HÀ NỘI");
        helper.setTo(email);
        helper.setSubject(subject);
        mailContent = "<p sytle='color:red;'>Xin chào " + userName + " ,<p>" + "<p> Nhấn vào link sau để xác thực email của bạn:</p>" +
                "<h3><a href='" + confirmUrl + "'>Link Xác thực( nhấn vào đây)!</a></h3>" +
                "<p>TRUNG TÂM Y TẾ DỰ PHÒNG HÀ NỘI</p>";
        helper.setText(mailContent, true);
        javaMailSender.send(message);
    }


    /**
     * hien thi list
     */
    @Override
    public List<Account> getAllAccount() {
        return accountRepository.getAllAccount();
    }

    /**
     *
     * Send info email to patient after register for a vaccination
     */
    @Override
    public void sendInfoEmail(PeriodicalVaccinationTempRegisterDTO register, VaccinationHistory vaccinationHistory) throws MessagingException, UnsupportedEncodingException {
        Patient patient = this.patientRepository.getOne(register.getPatientId());
        Vaccination vaccination = this.vaccinationRepository.getOne(register.getVaccinationId());
        StringBuilder randomCode = new StringBuilder();
        randomCode.append(register.getVaccinationId()).append("|").append(register.getPatientId());
        String subject = "Thông tin đăng ký tiêm chủng của bạn";
        String mailContent = "";
        String cancelRegisterUrl = "http://localhost:4200/cancel-register?code=" + randomCode;

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
        helper.setTo(patient.getEmail());
        helper.setFrom("taipt3351@gmail.com","TRUNG TÂM Y TẾ DỰ PHÒNG HÀ NỘI");
        helper.setSubject(subject);
        mailContent = "<span style=\"font-weight: bold\">Xin chào<span> "+patient.getGuardian()+",</span></span>\n" +
                "<br><br>\n" +
                "<span style=\"font-weight: bold\"> "+patient.getName()+"</span> <span>vừa được đăng ký tiêm chủng định kỳ với thông tin sau:</span>\n" +
                "<br><br>\n" +
                "<span style=\"font-weight: bold\"> Ngày tiêm chủng: </span><span>"+vaccination.getDate()+"</span>\n" +
                "<br><br>\n" +
                "<span style=\"font-weight: bold\"> Giờ tiêm chủng: </span><span>"+vaccinationHistory.getStartTime()+"  - "+vaccinationHistory.getEndTime()+"</span>\n" +
                "<br><br>\n" +
                "<span style=\"font-weight: bold\"> Địa điểm: </span><span> Trung Tâm y tế dự phòng - 70 Nguyễn Chí Thanh, Láng Thượng, Ba Đình, Hà Nội </span>\n" +
                "<br><br>\n" +
                "<span style=\"font-weight: bold\"> Tên Vắc xin: </span><span>"+vaccination.getVaccine().getName()+" </span>\n" +
                "<br><br>\n" +
                "<span style=\"font-weight: bold\"> Xuất xứ: </span><span>"+vaccination.getVaccine().getOrigin()+" </span>\n" +
                "<br><br>\n" +
                "<p style=\"font-style: italic; color: red\">Trong trường hợp bạn không thể tham gia vì lý do nào đó, bạn có thể hủy đăng ký bằng link bên dưới:</p>\n" +
                "<h3><a href='" + cancelRegisterUrl + "'>Link hủy đăng ký!</a></h3>" +
                "<p>TRUNG TÂM Y TẾ DỰ PHÒNG HÀ NỘI KÍNH BÁO</p>";
        helper.setText(mailContent, true);
        javaMailSender.send(message);
    }
}
