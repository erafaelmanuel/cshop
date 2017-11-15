package io.ermdev.cshop.business.listener;

import io.ermdev.cshop.business.event.RegisterEvent;
import io.ermdev.cshop.data.service.VerificationTokenService;
import io.ermdev.cshop.model.entity.User;
import io.ermdev.cshop.model.entity.VerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

@Component
public class RegistrationListener implements ApplicationListener<RegisterEvent> {

    private VerificationTokenService verificationTokenService;
    private JavaMailSender mailSender;

    @Autowired
    public RegistrationListener(VerificationTokenService verificationTokenService, JavaMailSender mailSender) {
        this.verificationTokenService = verificationTokenService;
        this.mailSender = mailSender;
    }

    @Override
    public void onApplicationEvent(RegisterEvent registerEvent) {
        try {
            confirmRegistration(registerEvent);
        } catch (UnsupportedEncodingException | MessagingException e) {
            e.printStackTrace();
        }
    }

    private void confirmRegistration(RegisterEvent event) throws UnsupportedEncodingException, MessagingException {
        User user = event.getUser();
        user.setId(13L);
        String token = UUID.randomUUID().toString();
        verificationTokenService.add(new VerificationToken(token, user));

        String recipientAddress = user.getEmail();
        String subject = "Registration Confirmation";
        String confirmationUrl = event.getApplicationContextUrl() + "/register/confirmation?token=" + token;
//        String message = messages.getMessage("message.regSucc", null, event.getLocale());

        MimeMailMessage mailMessage = new MimeMailMessage(mailSender.createMimeMessage());
        mailMessage.setTo(recipientAddress);
        mailMessage.setSubject(subject);
        mailMessage.getMimeMessage().setFrom(new InternetAddress("ermdev.io@gmail.com", "Cloth Shop"));
        mailMessage.getMimeMessage().setContent("http://localhost:8080" + confirmationUrl, "text/html");
        mailSender.send(mailMessage.getMimeMessage());
    }
}
