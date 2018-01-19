package io.ermdev.cshop.business.register;

import io.ermdev.cshop.data.service.UserService;
import io.ermdev.cshop.data.service.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class VerificationListener implements ApplicationListener<VerificationEvent> {

    private UserService userService;
    private VerificationTokenService verificationTokenService;
    private JavaMailSender mailSender;
    private VerificationMail verificationMail;

    @Autowired
    public VerificationListener(UserService userService, VerificationTokenService verificationTokenService,
                                JavaMailSender mailSender, VerificationMail verificationMail) {
        this.userService = userService;
        this.verificationTokenService = verificationTokenService;
        this.mailSender = mailSender;
        this.verificationMail = verificationMail;
    }

    @Override
    public void onApplicationEvent(VerificationEvent event) {
        try {
            removeExistingVerificationToken(event);
            confirmRegistration(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void removeExistingVerificationToken(VerificationEvent event) {
        final Long userId = ((VerificationSource) event.getSource()).getVerificationToken().getUserId();
        verificationTokenService.deleteByUserId(userId);
    }

    private void confirmRegistration(VerificationEvent event) throws Exception {
        final VerificationSource source = (VerificationSource) event.getSource();
        verificationTokenService.add(source.getVerificationToken());
        mailSender.send(verificationMail.constructVerificationMail(source).getMimeMessage());
    }
}
