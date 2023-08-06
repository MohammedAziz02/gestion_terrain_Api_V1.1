package com.ensah.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;

@Component
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    ResourceLoader resourceLoader;

    public void sendSimpleMessage(
            String to, String subject, String text) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@baeldung.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }


    public void sendMessageWithAttachment(
            String to, String subject, String text) throws MessagingException, IOException {


        MimeMessage message = emailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom("noreply@baeldung.com");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text);

        Resource resource = resourceLoader.getResource("classpath:/attachments/attachement.png" );
        File fileimg = resource.getFile();

        FileSystemResource file
                = new FileSystemResource(fileimg);
        helper.addAttachment("Invoice", file);

        emailSender.send(message);

    }
}
