package io.ermdev.cshop.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
public class MailService {

    private JavaMailSender mailSender;

    @Autowired
    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendRegisterNotification() throws MailException, MessagingException {
        MimeMailMessage mailMessage = new MimeMailMessage(mailSender.createMimeMessage());
        mailMessage.setTo("erafaelmanuel@gmail.com");
        mailMessage.setSubject("erafaelmanuel@gmail.com");
        mailMessage.getMimeMessage().setContent("<button>ongoy</button>","text/html");
        mailSender.send(mailMessage.getMimeMessage());
    }
}
