package io.ermdev.cshop.business.register;

import io.ermdev.cshop.commons.DateHelper;
import io.ermdev.cshop.data.entity.Token;
import io.ermdev.cshop.data.entity.User;
import io.ermdev.cshop.data.service.TokenService;
import io.ermdev.cshop.data.service.TokenUserService;
import io.ermdev.cshop.data.service.UserService;
import io.ermdev.cshop.exception.EntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.UUID;

@Component
public class RegisterListener implements ApplicationListener<RegisterEvent> {

    private OnRegisterSuccess onRegisterSuccess;

    private OnRegisterFailure onRegisterFailure;

    private UserService userService;

    private TokenService tokenService;

    private TokenUserService tokenUserService;

    private ConfirmationMail confirmationMail;

    private DateHelper dateHelper;

    private Sender sender;

    @Autowired
    public RegisterListener(UserService userService, TokenService tokenService, TokenUserService tokenUserService,
                            ConfirmationMail confirmationMail, DateHelper dateHelper) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.tokenUserService = tokenUserService;
        this.confirmationMail = confirmationMail;
        this.dateHelper = dateHelper;
    }

    @Override
    public void onApplicationEvent(RegisterEvent event) {
        onRegisterSuccess = event.getOnRegisterSuccess();
        onRegisterFailure = event.getOnRegisterFailure();
        register((RegisterSource) event.getSource());
    }

    private void register(RegisterSource registerSource) {
        final String generatedTokenKey = UUID.randomUUID().toString();
        final String url = registerSource.getUrl();
        final Locale locale = registerSource.getLocale();

        Token token = new Token();
        User user = registerSource.getUser();

        try {
            user.setUsername(user.getEmail());
            user.setEnabled(false);
            user = userService.save(user);

            token.setKey(generatedTokenKey);
            token.setExpiryDate(dateHelper.setTimeNow().addTimeInMinute(DateHelper.DAY_IN_MINUTE).getDate());
            token.setUser(user);
            token = tokenService.save(token);

            tokenUserService.save(token.getId(), user.getId());
            if (onRegisterSuccess != null) {
                onRegisterSuccess.onSuccess();
            }
            sender = new Sender(token, url, locale);
            sender.start();
        } catch (EntityException e) {
            if (onRegisterFailure != null) {
                onRegisterFailure.onFailure();
            }
        }
    }

    private void sendConfirmRegistration(Token token, String url, Locale locale) {
        MimeMailMessage mimeMailMessage = confirmationMail.constructMail(token, url, locale);
        confirmationMail.getMailSender().send(mimeMailMessage.getMimeMessage());
    }

    class Sender extends Thread {

        private Token token;

        private String url;

        private Locale locale;

        Sender(Token token, String url, Locale locale) {
            this.token = token;
            this.url = url;
            this.locale = locale;
        }

        @Override
        public void run() {
            sendConfirmRegistration(token, url, locale);
        }
    }
}
