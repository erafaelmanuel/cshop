package io.ermdev.cshop.business.register;

import io.ermdev.cshop.data.service.TokenUserService;
import io.ermdev.cshop.data.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.stereotype.Component;

@Component
public class RegisterListener implements ApplicationListener<RegisterEvent> {

    private UserService userService;
    private TokenUserService tokenUserService;
    private ConfirmationMail confirmationMail;

    @Autowired
    public RegisterListener(UserService userService, TokenUserService tokenUserService,
                            ConfirmationMail confirmationMail) {
        this.userService = userService;
        this.tokenUserService = tokenUserService;
        this.confirmationMail = confirmationMail;
    }

    @Override
    public void onApplicationEvent(RegisterEvent registerEvent) {
        sendConfirmRegistration((RegisterSource) registerEvent.getSource());
    }

    private void sendConfirmRegistration(RegisterSource registerSource) {
        MimeMailMessage mimeMailMessage = confirmationMail.constructMail(registerSource);
        confirmationMail.getMailSender().send(mimeMailMessage.getMimeMessage());
    }
}
