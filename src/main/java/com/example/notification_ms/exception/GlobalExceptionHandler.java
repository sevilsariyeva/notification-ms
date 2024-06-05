package com.example.notification_ms.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

public class GlobalExceptionHandler extends DefaultErrorAttributes {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler(EmailSendException.class)
    public final ResponseEntity<String> handleNotFoundException(EmailSendException ex,
                                                                WebRequest request){
        logger.error("EmailSendException: {}",ex.getMessage());
        return new ResponseEntity<>("Failed to send email: "+ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<String> handleEmailSendException(NotFoundException ex,
                                                                 WebRequest request){
        logger.error("NotFoundException: {}",ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
    }
}
