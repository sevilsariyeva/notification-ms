package com.example.notification_ms.controller;

import com.example.notification_ms.entity.NotFoundException;
import com.example.notification_ms.entity.Notification;
import com.example.notification_ms.entity.dto.EmailRequestDTO;
import com.example.notification_ms.service.EmailService;
import jakarta.validation.constraints.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/email")
public class EmailController {
    @Autowired
    private EmailService emailService;
    @PostMapping("/send")
    public String sendEmail(@RequestBody EmailRequestDTO emailRequest) {
        emailService.sendSimpleEmail(emailRequest.getToEmail(), emailRequest.getSubject(), emailRequest.getBody());
        return "Email sent successfully";
    }

    @GetMapping("/getAll")
    public List<Notification> getAllNotifications() {
        return emailService.getAllNotifications();
    }

    @GetMapping("/getByEmail")
    public ResponseEntity<List<Notification>> getAllByEmail(@RequestParam String email) {
        try {
            List<Notification> notifications = emailService.getAllByMail(email);
            return new ResponseEntity<>(notifications, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
