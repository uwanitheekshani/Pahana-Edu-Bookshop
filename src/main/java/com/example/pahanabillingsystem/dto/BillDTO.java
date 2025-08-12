package com.example.pahanabillingsystem.dto;

import java.time.LocalDateTime;
import java.util.List;

public class BillDTO {
    private int customerId;
    private List<BillItem> items;
    private int totalQty;
    private double totalAmount;
    private LocalDateTime billDate;

    public BillDTO() {
    }

    public BillDTO(int customerId, List<BillItem> items, int totalQty, double totalAmount, LocalDateTime billDate) {
        this.customerId = customerId;
        this.items = items;
        this.totalQty = totalQty;
        this.totalAmount = totalAmount;
        this.billDate = billDate;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public List<BillItem> getItems() {
        return items;
    }

    public void setItems(List<BillItem> items) {
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
