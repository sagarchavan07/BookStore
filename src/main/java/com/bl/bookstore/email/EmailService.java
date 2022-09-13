package com.bl.bookstore.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    JavaMailSender javaMailSender;

    public void sendEmail(String toMail, String subject, String body){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("$(EMAIL)");
        simpleMailMessage.setTo(toMail);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(body);

        javaMailSender.send(simpleMailMessage);
        System.out.println("Mail Sent Successfully!");
    }
}
