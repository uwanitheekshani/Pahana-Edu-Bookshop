package com.example.pahanabillingsystem.dao;

import com.example.pahanabillingsystem.model.OTP;
import com.example.pahanabillingsystem.utill.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class OtpDAO {

    public String generateOtp(String email, int userId) {
        String sql = "SELECT * FROM otp WHERE email = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            // Generate a new OTP code
            String otpCode = String.format("%06d", new Random().nextInt(1000000));

            if (rs.next()) {
                // Update existing OTP
                String updateSql = "UPDATE otp SET code = ? WHERE email = ?";
                try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                    updateStmt.setString(1, otpCode);
                    updateStmt.setString(2, email);
                    updateStmt.executeUpdate();
                }
            } else {
                // Insert new OTP
                String insertSql = "INSERT INTO otp (user_id, email, code) VALUES (?, ?, ?)";
                try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                    insertStmt.setInt(1, userId);
                    insertStmt.setString(2, email);
                    insertStmt.setString(3, otpCode);
                    insertStmt.executeUpdate();
                }
            }

            // Return the generated OTP code
            return otpCode;

        } catch (SQLException e) {
            e.printStackTrace();
            return null; // You can return null or handle it as needed
        }
    }


    // Find OTP by email
    public OTP findOtpByEmail(String email) {
        System.out.println("Email :" + email);
        String sql = "SELECT * FROM otp WHERE email = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new OTP(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("email"),
                        rs.getString("code")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Validate OTP for a specific email
    public boolean validateOtp(String email, String otpCode) {
        OTP otp = findOtpByEmail(email);
        return otp != null && otp.getCode().equals(otpCode);
    }

    public boolean deleteOtpByEmail(String email) {
        String sql = "DELETE FROM otp WHERE email = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
