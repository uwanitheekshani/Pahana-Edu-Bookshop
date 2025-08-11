package com.example.pahanabillingsystem.servlet;

import com.example.pahanabillingsystem.model.User;
import com.example.pahanabillingsystem.service.UserService;
import com.example.pahanabillingsystem.service.impl.UserServiceImpl;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private final UserService userService = new UserServiceImpl();
//    private WhatsAppMessageService messageService = new WhatsAppMessageServiceImpl();
//    SmsServiceImpl smsService = new SmsServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Read the JSON data from the request body
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        BufferedReader reader = request.getReader();
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }

        // Convert the string to a JSON object
        String jsonData = stringBuilder.toString();
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(jsonData, JsonObject.class);

        // Extract the data from the JSON object
        String username = jsonObject.get("username").getAsString();
        String password = jsonObject.get("password").getAsString();
        String roles = jsonObject.get("role").getAsString();
        String email = jsonObject.get("email").getAsString();


        System.out.println("Received Username: " + username);
        System.out.println("Received Password: " + password);
        System.out.println("Received Email: " + email);
        System.out.println("Received Role: " + roles);

        // Create the User object
        User user = new User(0, username, password, roles,email);
        System.out.println(user.getRole());

        boolean isRegistered = userService.registerUser(user);

        // Send response
        PrintWriter out = response.getWriter();

        if (isRegistered) {
            // Optionally, send an SMS or WhatsApp message
            // smsService.sendSms(user.getPhone(), "Congratulations " + user.getUsername() + " on successfully registering!");
//            messageService.sendRegistrationWhatsAppMessage(phone, username);

            response.setStatus(HttpServletResponse.SC_OK); // 200 OK
            out.print("{ \"message\": \"Registration successful!\" }");
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request
            out.print("{ \"error\": \"Failed to register.\" }");
        }

        out.flush();
    }
}
