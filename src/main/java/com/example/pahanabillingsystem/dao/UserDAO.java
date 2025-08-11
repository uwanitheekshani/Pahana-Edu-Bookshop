package com.example.pahanabillingsystem.dao;

import com.example.pahanabillingsystem.model.User;
import com.example.pahanabillingsystem.utill.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt;

public class UserDAO {

    public boolean save(User user) {
        String sql = "INSERT INTO users (username, password, role, email) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Hash the password before saving
            String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());

            stmt.setString(1, user.getUsername());
            stmt.setString(2, hashedPassword);  // Save hashed password
            stmt.setString(3, user.getRole());
            stmt.setString(4, user.getEmail());
            System.out.println(stmt);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Find user by username
    // Find user by email
    public User findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        System.out.println("sql: "+sql);
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("role"),
                        rs.getString("email")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Validate password using BCrypt
    public boolean checkPassword(User user, String password) {
        System.out.println("checkPassword BCrypt "+user.getPassword());
        return BCrypt.checkpw(password, user.getPassword()); // Verifies password against the hashed one
    }

    // Add method to update the password in the database
    public boolean updatePassword(String email, String newPassword) {
        String sql = "UPDATE users SET password = ? WHERE email = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Hash the new password before saving
            String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());

            stmt.setString(1, hashedPassword);  // Update password
            stmt.setString(2, email);  // Set email where the password should be updated

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }











    public User getUserById(int id) throws SQLException {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                user.setEmail(rs.getString("email"));
                return user;
            }
        }
        return null;
    }
}
