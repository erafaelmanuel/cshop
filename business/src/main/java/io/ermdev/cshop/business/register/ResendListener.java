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
public class ResendListener implements ApplicationListener<ResendEvent> {

    private ResendEvent.OnResendFinished onResendFinished;

    private UserService userService;
    private TokenService tokenService;
    private TokenUserService tokenUserService;
    private ConfirmationMail confirmationMail;
    private DateHelper dateHelper;

    @Autowired
    public ResendListener(UserService userService, TokenService tokenService, TokenUserService tokenUserService,
                          ConfirmationMail confirmationMail, DateHelper dateHelper) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.tokenUserService = tokenUserService;
        this.confirmationMail = confirmationMail;
        this.dateHelper = dateHelper;
    }

    @Override
    public void onApplicationEvent(ResendEvent event) {
        final ResendSource resendSource = (ResendSource) event.getSource();
        final String url = resendSource.getUrl();
        final Locale locale = resendSource.getLocale();
        final Long userId = resendSource.getUserId();

        onResendFinished = event.getOnResendFinished();
        createNewToken(userId, url, locale);
    }

    private void createNewToken(Long userId, String url, Locale locale) {
        try {
            final User user = userService.findById(userId);
            final String generatedTokenKey = UUID.randomUUID().toString();
            final Token token = new Token();

            token.setKey(generatedTokenKey);
            token.setExpiryDate(dateHelper.setTimeNow().addTimeInMinute(DateHelper.DAY_IN_MINUTE).getDate());
            token.setUser(user);
            if (!user.getEnabled()) {
                deleteOldToken(userId);
                long tokenId = tokenService.save(token).getId();
                tokenUserService.addUserToToken(tokenId, userId);
                onResendFinished.onFinish(false);
                ConfirmRegistrationThread confirmRegistrationThread = new ConfirmRegistrationThread(token, url, locale);
                confirmRegistrationThread.start();
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            onResendFinished.onFinish(true);
        }
    }

    private void deleteOldToken(Long userId) {
        try {
            Token token = tokenUserService.findTokenByUserId(userId);
            tokenService.delete(token);
        } catch (EntityException e) {
            e.printStackTrace();
        }
    }

    class ConfirmRegistrationThread extends Thread {

        private Token token;
        private String url;
        private Locale locale;

        public ConfirmRegistrationThread(Token token, String url, Locale locale) {
            this.token = token;
            this.url = url;
            this.locale = locale;
        }

        @Override
        public void run() {
            super.run();
            sendConfirmRegistration(token, url, locale);
        }
    }

    private void sendConfirmRegistration(Token token, String url, Locale locale) {
        MimeMailMessage mimeMailMessage = confirmationMail.constructMail(token, url, locale);
        confirmationMail.getMailSender().send(mimeMailMessage.getMimeMessage());
    }
}
