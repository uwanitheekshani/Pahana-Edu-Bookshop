package com.example.pahanabillingsystem.dao;

import com.example.pahanabillingsystem.dto.BillItem;
import com.example.pahanabillingsystem.model.Bill;
import com.example.pahanabillingsystem.model.Item;
import com.example.pahanabillingsystem.utill.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BillDAO {

    public boolean createBill(Bill bill, List<BillItem> items) {
        String insertBillSQL = "INSERT INTO bill (customer_id,total_amount,bill_date, items, total_qty) VALUES (?, ?, ?, ?, ?)";
        String updateStockSQL = "UPDATE item SET stock_quantity = stock_quantity - ? WHERE id = ?";
        String updateCustomerSQL = "UPDATE customer SET unit_consumed = unit_consumed + ? WHERE id = ?";

        try (Connection connection = DBUtil.getConnection()) {
            connection.setAutoCommit(false);

            // Insert bill record
            try (PreparedStatement billStmt = connection.prepareStatement(insertBillSQL)) {
                billStmt.setInt(1, bill.getCustomerId());
                billStmt.setDouble(2, bill.getTotalAmount());
                billStmt.setTimestamp(3, Timestamp.valueOf(bill.getBillDate()));
                billStmt.setString(4, bill.getItems());
                billStmt.setInt(5, bill.getTotalQty());
                billStmt.executeUpdate();
            }

            // Update stock quantities
            try (PreparedStatement stockStmt = connection.prepareStatement(updateStockSQL)) {
                for (BillItem item : items) {
                    stockStmt.setInt(1, item.getQuantity());
                    stockStmt.setInt(2, item.getId());
                    stockStmt.executeUpdate();
                }
            }

            try (PreparedStatement customerStmt = connection.prepareStatement(updateCustomerSQL)) {
                customerStmt.setInt(1, bill.getTotalQty()); // add total qty of bill
                customerStmt.setInt(2, bill.getCustomerId());
                customerStmt.executeUpdate();
            }

            connection.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Bill getBillById(int billId) {
        String sql = "SELECT * FROM bill WHERE id = ?";
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, billId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Bill(
                        rs.getInt("id"),
                        rs.getInt("customer_id"),
                        rs.getString("items"),
                        rs.getInt("total_qty"),
                        rs.getDouble("total_amount"),
                        rs.getTimestamp("bill_date").toLocalDateTime()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Bill> getAllBills() {
        List<Bill> bills = new ArrayList<>();
        String sql = "SELECT * FROM bill ORDER BY bill_date DESC";

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                bills.add(new Bill(
                        rs.getInt("id"),
                        rs.getInt("customer_id"),
                        rs.getString("items"),
                        rs.getInt("total_qty"),
                        rs.getDouble("total_amount"),
                        rs.getTimestamp("bill_date").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bills;
    }
}