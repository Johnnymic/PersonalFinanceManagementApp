package com.michael.Personal.Finance.email;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {


    private final JavaMailSender mailSender;

    private final SpringTemplateEngine templateEngine;
    @Async
    public void sendEmail(String to,String username, String confirmationUrl, String activationCode,String subject,EmailServiceTemplate template) {
     String templateName;

     if(template == null){
         templateName = "confirm-email";

     }else{
         templateName =  ;

     }
    // MimeMessage message = mailSender.




    }
}
