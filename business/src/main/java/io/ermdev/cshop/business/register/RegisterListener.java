package io.ermdev.cshop.business.register;

import io.ermdev.cshop.data.service.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.MailParseException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

@Component
public class RegisterListener implements ApplicationListener<RegisterEvent> {

    private VerificationTokenService verificationTokenService;
    private JavaMailSender mailSender;
    private RegisterMail registerMail;

    @Autowired
    public RegisterListener(VerificationTokenService verificationTokenService, JavaMailSender mailSender,
                            RegisterMail registerMail) {
        this.verificationTokenService = verificationTokenService;
        this.mailSender = mailSender;
        this.registerMail = registerMail;
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

        verificationTokenService.add(source.getVerificationToken());
        mailSender.send(registerMail.constructVerificationMail(source).getMimeMessage());
    }
}
