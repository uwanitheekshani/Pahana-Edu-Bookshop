package com.example.pahanabillingsystem.service;

import com.example.pahanabillingsystem.model.User;
import jakarta.mail.MessagingException;

import java.sql.SQLException;

public interface UserService {

    boolean registerUser(User user);

    User authenticateUser(String username, String password);
    User getUserByUsername(String username);
    User getUserByEmail(String email);


    String forgotPassword(String email) throws MessagingException;

    boolean verifyOtp(String email, String otpCode);

    boolean changePassword(String email, String newPassword);


    User getUserById(int id ) throws SQLException;
    }

