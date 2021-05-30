package com.project.service;

public interface InvoiceService {

    void createInvoice(String expired, int price, int quantity,String transactionDate, int provideId, int vaccineId);

}
