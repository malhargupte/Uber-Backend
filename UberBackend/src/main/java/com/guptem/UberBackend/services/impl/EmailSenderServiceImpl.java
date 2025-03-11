package com.guptem.UberBackend.services.impl;

import com.guptem.UberBackend.services.EmailSenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderServiceImpl implements EmailSenderService {

    private static final Logger log = LoggerFactory.getLogger(EmailSenderServiceImpl.class);
    private final JavaMailSender javaMailSender;

    public EmailSenderServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendEmail(String toEmail, String subject, String body) {

        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

            simpleMailMessage.setTo(toEmail);
            simpleMailMessage.setSubject(subject);
            simpleMailMessage.setText(subject);

            javaMailSender.send(simpleMailMessage);
            log.info("Email sent successfully!");
        } catch (Exception ex) {
            log.info("Cannot send email!" + ex.getMessage());
        }

    }

    @Override
    public void sendEmail(String[] toEmail, String subject, String body) {

        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

            simpleMailMessage.setTo(toEmail);
            simpleMailMessage.setSubject(subject);
            simpleMailMessage.setText(subject);

            javaMailSender.send(simpleMailMessage);
            log.info("Email sent successfully!");
        } catch (Exception ex) {
            log.info("Cannot send email!" + ex.getMessage());
        }

    }
}
