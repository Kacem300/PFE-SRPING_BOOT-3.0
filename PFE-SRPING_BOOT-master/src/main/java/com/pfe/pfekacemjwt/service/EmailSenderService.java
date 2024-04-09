package com.pfe.pfekacemjwt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender javaMailSender;
    public void sendEmail(String to, String subject, String text) {
         SimpleMailMessage message = new SimpleMailMessage();
         message.setFrom("kacem.benbrahim07@gmail.com");
         message.setTo(to);
         message.setSubject(subject);
         message.setText(text);
        javaMailSender.send(message);
        System.out.println("Email sent successfully");
    }
}
