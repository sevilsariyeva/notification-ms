package com.example.notification_ms.controller;

import com.example.notification_ms.exception.NotFoundException;
import com.example.notification_ms.entity.Notification;
import com.example.notification_ms.entity.dto.EmailRequestDTO;
import com.example.notification_ms.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/notifications")
    public List<Notification> getAllNotifications() {
        return emailService.getAllNotifications();
    }

    @GetMapping("/notificationsByEmail")
    public ResponseEntity<List<Notification>> getAllByEmail(@RequestParam String email) {
        List<Notification> notifications = emailService.getAllByMail(email);
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }
    @GetMapping("/search")
    public ResponseEntity<List<Notification>> searchNotifications(@RequestParam String message){
        List<Notification> notifications = emailService.search(message);
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }
}
