package io.ermdev.cshop.business.register;

import io.ermdev.cshop.data.entity.Token;
import io.ermdev.cshop.data.service.TokenService;
import io.ermdev.cshop.data.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ResendListener implements ApplicationListener<ResendEvent> {

    private Token token;
    private String url;
    private String locale;

    private UserService userService;
    private TokenService tokenService;

    @Autowired
    public ResendListener(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @Override
    public void onApplicationEvent(ResendEvent resendEvent) {

    }

    public void deleteAllUserToken() {
        tokenService.delete(token);
    }
}
