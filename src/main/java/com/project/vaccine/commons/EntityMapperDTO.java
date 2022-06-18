package com.project.vaccine.commons;


import com.project.vaccine.dto.VaccineFindDTO;
import com.project.vaccine.dto.VaccineFindIdDTO;
import com.project.vaccine.entity.Invoice;
import com.project.vaccine.entity.Vaccine;
import org.springframework.stereotype.Component;

@Component
public class EntityMapperDTO {

    public Vaccine mappFromVaccineDTO(VaccineFindDTO vaccineFindIdDTO){

        Vaccine vaccine=new Vaccine();

        vaccine.setVaccineId(vaccineFindIdDTO.getVaccineId());
        vaccine.setName(vaccineFindIdDTO.getName());
        vaccine.setAge(vaccineFindIdDTO.getAge());
        vaccine.setDosage(vaccineFindIdDTO.getDosage());
        vaccine.setDuration(vaccineFindIdDTO.getDuration());
        vaccine.setExpired(vaccineFindIdDTO.getExpired());
        vaccine.setLicenseCode(vaccineFindIdDTO.getLicenseCode());
        vaccine.setMaintenance(vaccineFindIdDTO.getMaintenance());
        vaccine.setOrigin(vaccineFindIdDTO.getOrigin());
        vaccine.setTimes(vaccineFindIdDTO.getTimes());
        return vaccine;
    }
    public Invoice mapFromInvoiceDTO(VaccineFindIdDTO vaccineFindIdDTO){
        Invoice invoice=new Invoice();
        return invoice;
    }


}
