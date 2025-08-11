package com.example.pahanabillingsystem.service.impl;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class EmailService {

    private static final String SMTP_HOST = "smtp.gmail.com"; // Change for other providers
    private static final String SMTP_PORT = "587"; // 465 for SSL, 587 for TLS
    //    private static final String SMTP_USERNAME = "nimeshdenuwan2000@gmail.com";
//    private static final String SMTP_PASSWORD = "sifc anwx xvae btat"; // Use App Passwords for security
    private static final String SMTP_USERNAME = "uwanitheekshani@gmail.com";
    private static final String SMTP_PASSWORD = "krsr rjxv ljgz arah";


    public static void sendEmail(String recipient, String subject, String messageText) throws MessagingException {
        // Set mail properties
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); // Enable TLS
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);

        // Create a session with authentication
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SMTP_USERNAME, SMTP_PASSWORD);
            }
        });

        // Create the message
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(SMTP_USERNAME));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
        message.setSubject(subject);
        message.setText(messageText);

        // Send the email
        Transport.send(message);

        System.out.println("Email sent successfully to " + recipient);
    }
}
