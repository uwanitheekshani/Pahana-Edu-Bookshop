package com.example.pahanabillingsystem.model;

import java.time.LocalDateTime;

public class Bill {
    private int id;
    private int customerId;
    private String items;
    private int totalQty;
    private double totalAmount;
    private LocalDateTime billDate;

    public Bill() {
    }

    public Bill(int id, int customerId, String items, int totalQty, double totalAmount, LocalDateTime billDate) {
        this.id = id;
        this.customerId = customerId;
        this.items = items;
        this.totalQty = totalQty;
        this.totalAmount = totalAmount;
        this.billDate = billDate;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public int getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(int totalQty) {
        this.totalQty = totalQty;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDateTime getBillDate() {
        return billDate;
    }

    public void setBillDate(LocalDateTime billDate) {
        this.billDate = billDate;
    }
}