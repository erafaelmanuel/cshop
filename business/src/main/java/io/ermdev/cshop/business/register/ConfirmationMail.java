package io.ermdev.cshop.business.register;

import io.ermdev.cshop.business.verification.VerificationSource;
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
public class ConfirmationMail {

    private MessageSource messageSource;
    private JavaMailSender mailSender;

    @Autowired
    public ConfirmationMail(MessageSource messageSource, JavaMailSender mailSender) {
        this.messageSource = messageSource;
        this.mailSender = mailSender;
    }

    public MimeMailMessage constructConfirmationMail(VerificationSource source) throws UnsupportedEncodingException,
            MessagingException {

        String address = messageSource.getMessage("cshop.email", null, source.getLocale());
        String recipientAddress = source.getVerificationToken().getUser().getEmail();
        String title = messageSource.getMessage("cshop.title", null, source.getLocale());
        String subject = messageSource.getMessage("register.mail.activation", new Object[] {
                source.getVerificationToken().getUser().getName(), title}, source.getLocale());
        String confirmationUrl = String.format(Locale.ENGLISH, "%s/register/confirmation?token=%s",
                source.getUrl(), source.getVerificationToken().getToken());

        MimeMailMessage mailMessage = new MimeMailMessage(mailSender.createMimeMessage());
        mailMessage.setTo(recipientAddress);
        mailMessage.setSubject(subject);
        mailMessage.getMimeMessage().setFrom(new InternetAddress(address, title));
        mailMessage.getMimeMessage().setContent(confirmationUrl, "text/html");
        return mailMessage;
    }
}
