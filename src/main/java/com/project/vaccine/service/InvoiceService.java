package com.project.vaccine.service;

public interface InvoiceService {

    void createInvoice(String expired, int price, int quantity,String transactionDate, int provideId, int vaccineId);

    void editInvoice(int price,int id);

}
