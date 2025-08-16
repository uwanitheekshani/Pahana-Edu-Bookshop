package com.example.pahanabillingsystem.service;

import com.example.pahanabillingsystem.dto.BillItem;
import com.example.pahanabillingsystem.model.Bill;
import com.example.pahanabillingsystem.model.Item;

import java.util.List;

public interface BillService {
    boolean createBill(Bill bill, List<BillItem> items);

    Bill getBillById(int billId);
    List<Bill> getAllBills();
}