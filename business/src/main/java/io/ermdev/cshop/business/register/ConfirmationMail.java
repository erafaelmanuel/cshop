package io.ermdev.cshop.business.register;

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

    private final String EMAIL = "cshop.email";
    private final String TITLE = "cshop.title";
    private final String MAIL_ACTIVATION = "register.mail.activation";

    private MessageSource messageSource;
    private JavaMailSender mailSender;

    @Autowired
    public ConfirmationMail(MessageSource messageSource, JavaMailSender mailSender) {
        this.messageSource = messageSource;
        this.mailSender = mailSender;
    }

    public MimeMailMessage constructMail(RegisterSource source) {

        try {
            final String address = messageSource.getMessage(EMAIL, null, source.getLocale());
            final String recipientAddress = source.getToken().getUser().getEmail();
            final String title = messageSource.getMessage(TITLE, null, source.getLocale());
            final Object objects[] = new Object[]{source.getToken().getUser().getName(), title};
            final String subject = messageSource.getMessage(MAIL_ACTIVATION, objects, source.getLocale());
            final String confirmationUrl = String.format(Locale.ENGLISH, "%s/register/confirmation?token=%s",
                    source.getUrl(), source.getToken().getKey());

            MimeMailMessage mailMessage = new MimeMailMessage(mailSender.createMimeMessage());
            mailMessage.setTo(recipientAddress);
            mailMessage.setSubject(subject);
            mailMessage.getMimeMessage().setFrom(new InternetAddress(address, title));
            mailMessage.getMimeMessage().setContent(confirmationUrl, "text/html");
            return mailMessage;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public JavaMailSender getMailSender() {
        return mailSender;
    }
}
