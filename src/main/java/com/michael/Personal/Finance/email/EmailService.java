package com.michael.Personal.Finance.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.mail.javamail.MimeMessageHelper.MULTIPART_MODE_MIXED;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {


    private final JavaMailSender mailSender;

    private final SpringTemplateEngine templateEngine;
    @Async
    public void sendEmail(String to, String username, String confirmationUrl, String activationCode, EmailServiceTemplate template, String subject) throws MessagingException {
     String templateName;

     if(template == null){
         templateName = "confirm-email";

     }else{
         templateName = template.getName() ;

     }

     MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper( message,MULTIPART_MODE_MIXED,
                UTF_8.name());
        Map<String, Object> properties = new HashMap<>();

        properties.put("username", username);
        properties.put("confirmationUrl", confirmationUrl);
        properties.put("activation-code", activationCode);

        Context context = new Context();

        context.setVariables(properties);
        mimeMessageHelper.setFrom("johnny25580@gmail.com");
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSentDate(new Date());

        String temp = templateEngine.process(templateName,context);

        mimeMessageHelper.setText(temp,true);

        mailSender.send(message);




    }
}
