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
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.UUID;

@Component
public class ResendListener implements ApplicationListener<ResendEvent> {

    private ResendEvent.OnResendFinished onResendFinished;

    private UserService userService;
    private TokenService tokenService;
    private TokenUserService tokenUserService;
    private DateHelper dateHelper;

    @Autowired
    public ResendListener(UserService userService, TokenService tokenService, TokenUserService tokenUserService,
                          DateHelper dateHelper) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.tokenUserService = tokenUserService;
        this.dateHelper = dateHelper;
    }

    @Override
    public void onApplicationEvent(ResendEvent event) {
        final RegisterSource registerSource = (RegisterSource) event.getSource();
        final String url = registerSource.getUrl();
        final Locale locale = registerSource.getLocale();
        final Long userId = registerSource.getUser().getId();

        onResendFinished = event.getOnResendFinished();

        deleteOldToken(userId);
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
                onResendFinished.onFinish(false);
            } else {
                onResendFinished.onFinish(true);
            }
        } catch (EntityException e) {
            e.printStackTrace();
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
}
