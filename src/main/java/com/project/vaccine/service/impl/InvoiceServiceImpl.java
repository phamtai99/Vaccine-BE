package com.project.vaccine.service.impl;

import com.project.vaccine.repository.InvoiceRepository;
import com.project.vaccine.service.InvoiceService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class InvoiceServiceImpl implements InvoiceService {

    private static Logger logger= LogManager.getLogger(InvoiceServiceImpl.class);
    @Autowired
    InvoiceRepository invoiceRepository;


    /**
     * create invoice
     * @return
     */
    @Override
    public void createInvoice(String expired, int price, int quantity, String transactionDate, int provideId, int vaccineId) {
        invoiceRepository.createInvoice(expired, price, quantity, transactionDate, provideId, vaccineId);
    }

    /**
     * eidt  invoice 20220528
     * @return
     */

    @Override
    public void editInvoice(int price,int id) {
            try{
                invoiceRepository.editInvoice(price,id);
            }catch (Exception ex){
                logger.error("Lỗi cập nhật giá vaccine : "+ ex);
            }
    }
}
