package io.ermdev.cshop.data.service;

import io.ermdev.cshop.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.UnsupportedEncodingException;

@Service
public class MailService {

    private JavaMailSender mailSender;

    private final String SUBJECT_REGISTRATION="Your Account Details at ClothShop";

    private final String ADDRESS_SENDER="ClothShop";

    @Autowired
    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendRegisterNotification(User user) throws MailException, MessagingException, UnsupportedEncodingException {
        MimeMailMessage mailMessage = new MimeMailMessage(mailSender.createMimeMessage());
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject(SUBJECT_REGISTRATION);
        mailMessage.getMimeMessage().setFrom(new InternetAddress("ermdev.io@gmail.com", ADDRESS_SENDER));
        mailMessage.getMimeMessage().setContent("<button>hey</button>","text/html");
        mailSender.send(mailMessage.getMimeMessage());
    }

    public boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress internetAddress = new InternetAddress(email);
            internetAddress.validate();
        } catch (AddressException e) {
            result = false;
        }
        return result;
    }
}
