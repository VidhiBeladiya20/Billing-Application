package com.example.Bill_System.service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class EmailSendService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}") // Inject value from application.properties
    private String fromEmail;
    public void sendEmail(String toEmail, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage(); // simple text send through mail
            message.setFrom(fromEmail);
            message.setTo(toEmail);
            message.setSubject(subject);
            message.setText(body);

            javaMailSender.send(message); // used to
            System.out.println("Email sent to: " + toEmail);
        } catch (Exception e) {
            System.err.println("Email failed to send: " + e.getMessage());
        }
    }

    public void sendCsvToAdmin(String filePath, String toEmail) {
        try {

            //To create email with attachments
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject("Daily Bill Report");
            helper.setText("Please find attached the daily billing report.");


            //FileSystemResource is a Spring class that represents a file from the local file system as a resource.
            FileSystemResource file = new FileSystemResource(new File(filePath));
            helper.addAttachment("bills_report.csv", file);

            javaMailSender.send(message);
            System.out.println("Email sent successfully to " + toEmail);
        } catch (Exception e) {
            System.err.println("Failed to send email: " + e.getMessage());
            throw new RuntimeException("Email sending failed", e);
        }

    }
}
