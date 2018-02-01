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

    private UserService userService;
    private TokenService tokenService;
    private TokenUserService tokenUserService;
    private ConfirmationMail confirmationMail;
    private DateHelper dateHelper;

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
    public void onApplicationEvent(RegisterEvent registerEvent) {

    }

    private void registerUser(RegisterSource registerSource) {
        try {
            final Token token = new Token();
            final User user = registerSource.getUser();
            final String generatedUsername = user.getEmail().split("@")[0];
            final String generatedTokenKey = UUID.randomUUID().toString();
            final String url = registerSource.getUrl();
            final Locale locale = registerSource.getLocale();

            user.setUsername(generatedUsername);
            user.setId(userService.save(user).getId());

            token.setKey(generatedTokenKey);
            token.setExpiryDate(dateHelper.setTimeNow().addTimeInMinute(DateHelper.DAY_IN_MINUTE).getDate());
            token.setUser(user);

            tokenService.save(token);

        } catch (EntityException e) {

        }
    }

    private void sendConfirmRegistration(Token token, String url, Locale locale) {
        MimeMailMessage mimeMailMessage = confirmationMail.constructMail(token, url, locale);
        confirmationMail.getMailSender().send(mimeMailMessage.getMimeMessage());
    }
}
