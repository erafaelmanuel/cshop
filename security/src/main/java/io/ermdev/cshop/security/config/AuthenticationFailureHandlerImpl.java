package io.ermdev.cshop.security.config;

import io.ermdev.cshop.data.exception.EntityNotFoundException;
import io.ermdev.cshop.data.service.UserService;
import io.ermdev.cshop.data.entity.User;
import io.ermdev.cshop.security.validator.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class AuthenticationFailureHandlerImpl implements AuthenticationFailureHandler {

    private UserService userService;

    @Autowired
    AuthenticationFailureHandlerImpl(UserService userService) {
        this.userService = userService;
    }

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {

        String emailOrUsername = request.getParameter("username");
        String url="/login?error";
        User user;
        try {
            final boolean isEmail = new EmailValidator().validateEmail(emailOrUsername);
            if(isEmail)
                user = userService.findByEmail(emailOrUsername);
            else
                user = userService.findByUsername(emailOrUsername);
        }catch (EntityNotFoundException e) {
            user=null;
        }

        if(user != null && !user.getEnabled()) {
            url = "/register/complete?userId=" + user.getId();
        }
        redirectStrategy.sendRedirect(request, response, url);
        clearAuthenticationAttributes(request);
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
}
