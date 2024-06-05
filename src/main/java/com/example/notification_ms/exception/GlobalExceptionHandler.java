package com.example.notification_ms.exception;

import com.example.notification_ms.util.HttpResponseConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static net.bytebuddy.description.annotation.AnnotationDescription.Builder.ofType;


@RestControllerAdvice
public class GlobalExceptionHandler extends DefaultErrorAttributes {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler(EmailSendException.class)
    public ResponseEntity<Map<String,Object>> handleException(EmailSendException ex,
                                                              WebRequest request){
        logger.info("EmailSendException: {}",ex.getMessage());
        return ofType(request,HttpStatus.BAD_REQUEST,ex.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String,Object>> handleException(NotFoundException ex,
                                                              WebRequest request){
        logger.info("NotFoundException: {}",ex.getMessage());
        return ofType(request,HttpStatus.NOT_FOUND,ex.getMessage());
    }

    private ResponseEntity<Map<String, Object>> ofType(WebRequest request, HttpStatus status, String message) {
        return ofType(request,status,message, Collections.emptyList());
    }

    private ResponseEntity<Map<String, Object>> ofType(WebRequest request, HttpStatus status, String message, List<?> validationErrors) {
        Map<String,Object> attributes=getErrorAttributes(request, ErrorAttributeOptions.defaults());
        attributes.put(HttpResponseConstants.STATUS,status.value());
        attributes.put(HttpResponseConstants.ERROR,status.getReasonPhrase());
        attributes.put(HttpResponseConstants.MESSAGE,message);
        attributes.put(HttpResponseConstants.ERRORS,validationErrors);
        attributes.put(HttpResponseConstants.PATH,((ServletWebRequest) request).getRequest().getRequestURI());
        return new ResponseEntity<>(attributes,status);
    }

    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<String> handleEmailSendException(NotFoundException ex,
                                                                 WebRequest request){
        logger.error("NotFoundException: {}",ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
    }
}
