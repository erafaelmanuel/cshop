package io.ermdev.cshop.business.listener;

import io.ermdev.cshop.business.event.RegisterEvent;
import io.ermdev.cshop.data.service.VerificationTokenService;
import io.ermdev.cshop.model.entity.User;
import io.ermdev.cshop.model.entity.VerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
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
    private MessageSource messageSource;

    @Autowired
    public RegistrationListener(VerificationTokenService verificationTokenService, JavaMailSender mailSender,
                                MessageSource messageSource) {
        this.verificationTokenService = verificationTokenService;
        this.mailSender = mailSender;
        this.messageSource = messageSource;
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
        final User user = event.getUser();
        String token = UUID.randomUUID().toString();
        String recipientAddress = user.getEmail();
        String title = messageSource.getMessage("application.title", null, null);
        String subject = messageSource.getMessage("message.mail.subject.account.activation",
                new Object[]{user.getName(), title}, null);
        String confirmationUrl = event.getApplicationContextUrl() + "/register/confirmation?token=" + token;

        verificationTokenService.add(new VerificationToken(token, user));

        MimeMailMessage mailMessage = new MimeMailMessage(mailSender.createMimeMessage());
        mailMessage.setTo(recipientAddress);
        mailMessage.setSubject(subject);
        mailMessage.getMimeMessage().setFrom(new InternetAddress("ermdev.io@gmail.com", title));
        mailMessage.getMimeMessage().setContent(confirmationUrl, "text/html");
        mailSender.send(mailMessage.getMimeMessage());
    }
}
