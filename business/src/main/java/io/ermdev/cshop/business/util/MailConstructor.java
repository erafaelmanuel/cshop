package io.ermdev.cshop.business.util;

import io.ermdev.cshop.data.entity.User;
import io.ermdev.cshop.data.entity.VerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

@Component
public class MailConstructor {

    private MessageSource messageSource;
    private JavaMailSender mailSender;

    @Autowired
    public MailConstructor(MessageSource messageSource, JavaMailSender mailSender) {
        this.messageSource = messageSource;
        this.mailSender = mailSender;
    }

    public MimeMailMessage constructVerificationMail(VerificationToken verificationToken, String url, Locale locale)
            throws UnsupportedEncodingException, MessagingException {
        User user = verificationToken.getUser();
        String recipientAddress = user.getEmail();
        String title = messageSource.getMessage("application.title", null, null);
        String subject = messageSource.getMessage("mail.sub.accntActivation", new Object[]{user.getName(), title}, null);
        String confirmationUrl = url + "/register/confirmation?token=" + verificationToken.getToken();

        MimeMailMessage mailMessage = new MimeMailMessage(mailSender.createMimeMessage());
        mailMessage.setTo(recipientAddress);
        mailMessage.setSubject(subject);
        mailMessage.getMimeMessage().setFrom(new InternetAddress("ermdev.io@gmail.com", title));
        mailMessage.getMimeMessage().setContent(confirmationUrl, "text/html");
        return mailMessage;
    }
}
