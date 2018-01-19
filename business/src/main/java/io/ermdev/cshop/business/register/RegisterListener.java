package io.ermdev.cshop.business.register;

import io.ermdev.cshop.business.util.MailConstructor;
import io.ermdev.cshop.data.entity.VerificationToken;
import io.ermdev.cshop.data.service.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.MailParseException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

@Component
public class RegisterListener implements ApplicationListener<RegisterEvent> {

    private VerificationTokenService verificationTokenService;
    private JavaMailSender mailSender;
    private MessageSource messageSource;
    private MailConstructor mailConstructor;

    @Autowired
    public RegisterListener(VerificationTokenService verificationTokenService, JavaMailSender mailSender,
                                MessageSource messageSource, MailConstructor mailConstructor) {
        this.verificationTokenService = verificationTokenService;
        this.mailSender = mailSender;
        this.messageSource = messageSource;
        this.mailConstructor = mailConstructor;
    }

    @Override
    public void onApplicationEvent(RegisterEvent registerEvent) {
        try {
            confirmRegistration(registerEvent);
        } catch (UnsupportedEncodingException | MessagingException | MailParseException e) {
            e.printStackTrace();
        }
    }

    private void confirmRegistration(RegisterEvent event) throws UnsupportedEncodingException, MessagingException {
        final RegisterSource source = (RegisterSource) event.getSource();
        final VerificationToken verificationToken = source.getVerificationToken();
        final String url = source.getUrl();
        final Locale locale = source.getLocale();

        verificationTokenService.add(verificationToken);

        MimeMailMessage mailMessage = mailConstructor.constructVerificationMail(verificationToken, url, locale);
        mailSender.send(mailMessage.getMimeMessage());
    }
}
