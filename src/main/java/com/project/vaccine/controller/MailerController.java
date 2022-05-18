package com.project.vaccine.controller;

import com.project.vaccine.dto.VaccinationForEmail;
import com.project.vaccine.service.VaccinationHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@EnableScheduling
public class MailerController {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private VaccinationHistoryService vaccinationHistoryService;

    @Scheduled(cron = "0 59 23 * * ?")
    public void sendEmail() throws MessagingException, UnsupportedEncodingException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY");
        // plusDay(1): cộng thêm 1 ngày để gửi mail lịch nhắc tiêm ngày hôm sau
        LocalDate dayPlusAWeek = LocalDate.now().plusDays(1);
        String day = formatter.format(dayPlusAWeek);
        List<VaccinationForEmail> ListvaccinationForEmail= vaccinationHistoryService.getAllVaccinationForEmail();

        List<String> listEmailOneTime=new ArrayList<String>();;

        String location =ListvaccinationForEmail.get(0).getLocation();
        String vaccineName=ListvaccinationForEmail.get(0).getNameVaccine();
        String vaccineOrigin=ListvaccinationForEmail.get(0).getOrigin();
        String startTime=ListvaccinationForEmail.get(0).getStartTime();
        String endTime=ListvaccinationForEmail.get(0).getEndTime();
        for (int i=0;i<ListvaccinationForEmail.size();i++){
            listEmailOneTime.add(ListvaccinationForEmail.get(i).getEmail());
        }


//        List<String> listEmailOneTime = vaccinationHistoryService.getAllEmailToSend();
//        List<String> listEmailOneMoreTime = vaccinationHistoryService.getEmailToSendOfVaccinationMore();
        Set<String> listEmail = new HashSet<>();
        listEmail.addAll(listEmailOneTime);
//        listEmail.addAll(listEmailOneMoreTime);

        if (!(listEmail.size() == 0)) {
            String[] array = listEmail.toArray(new String[0]);

            MimeMessage message =  this.emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
            helper.setTo(array);
            helper.setFrom("taipt3351@gmail.com", "TRUNG TÂM Y TẾ DỰ PHÒNG HÀ NỘI");
            helper.setSubject("[Quan trọng] Nhắc nhở lịch tiêm chủng ");
            String mailContent = "";
            mailContent ="Chào bạn \n"
                    +"TRUNG TÂM Y TẾ DỰ PHÒNG HÀ NỘI xin thông báo, vào ngày " + day + "  trung tâm tổ chức tiêm chủng mở rộng với thông tin như sau: \n"

                    +   "<br>\n"
                    +"Tên vaccine : "+ vaccineName +  "<br>\n"
                    +"Xuất xứ : "+vaccineOrigin +  "<br>\n"
                    +"Thời gian "+ startTime+"am - "+endTime+"pm" +  "<br>\n"
                    +"Tại : "+location +"<br>\n" +
                    " Bạn vui lòng đến đúng thời gian đã đăng kí tiêm để tiêm chủng \n" +   "<br>\n"+
                    " Trân trọng ";
            helper.setText(mailContent, true);
                    // Create a Simple MailMessage.
//            SimpleMailMessage message = new SimpleMailMessage();
//            message.setTo(array);
//            message.setSubject("Nhắc nhở lịch tiêm chủng ");
//            message.setText("Chào bạn \n"
//                    +"TRUNG TÂM Y TẾ DỰ PHÒNG HÀ NỘI xin thông báo, vào ngày " + day + "  trung tâm tổ chức tiêm chủng mở rộng với thông tin như sau: \n"
//                    +"Tên vaccine : "+ vaccineName +"\n"
//                    +"Xuất xứ : "+vaccineOrigin +"\n"
//                    +"Thời gian "+ startTime+"am - "+endTime+"pm" +"\n"
//                    +"Tại : "+location +"\n" +
//                    " Bạn vui lòng đến đúng thời gian đã đăng kí tiêm để tiêm chủng \n" +
//                    " Trân trọng ");




            // Send Message!
            this.emailSender.send(message);
        } else {
            System.out.println(day + " Have not email to send!");
        }
    }
}
