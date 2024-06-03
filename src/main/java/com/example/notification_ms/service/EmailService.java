package com.example.notification_ms.service;

import com.example.notification_ms.entity.NotFoundException;
import com.example.notification_ms.entity.Notification;
import com.example.notification_ms.entity.dto.EmailRequestDTO;
import com.example.notification_ms.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailService {
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender mailSender;

    private final NotificationRepository notificationRepository;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendSimpleEmail(String toEmail, String subject, String body){
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject(subject);
            message.setText(body);
            message.setFrom(fromEmail);

            Notification notification = Notification.builder()
                    .toEmail(toEmail)
                    .fromEmail(fromEmail)
                    .subject(subject)
                    .body(body)
                    .build();

            mailSender.send(message);
            notificationRepository.save(notification);

            logger.info("Email sent to {}", toEmail);
        } catch (Exception e) {
            logger.error("Failed to send email to {}: {}", toEmail, e.getMessage());
            throw new RuntimeException("Failed to send email", e);
        }
    }

    public List<Notification>getAllNotifications(){
        return notificationRepository.findAll();
    }
    public List<Notification> getAllByMail(String email) {
        List<Notification> notifications = notificationRepository.findByFromEmail(email);
        if (notifications.isEmpty()) {
            throw new NotFoundException("No notifications found for email: " + email);
        }
        return notifications;
    }
}
