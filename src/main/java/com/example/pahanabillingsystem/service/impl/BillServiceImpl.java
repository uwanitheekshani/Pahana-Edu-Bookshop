package com.example.pahanabillingsystem.service.impl;

import com.example.pahanabillingsystem.dao.BillDAO;
import com.example.pahanabillingsystem.dto.BillItem;
import com.example.pahanabillingsystem.model.Bill;
import com.example.pahanabillingsystem.service.BillService;

import java.util.List;

public class BillServiceImpl implements BillService {

    private final BillDAO billDAO = new BillDAO();

    @Override
    public boolean createBill(Bill bill, List<BillItem> items) {
        return billDAO.createBill(bill, items);
    }
}