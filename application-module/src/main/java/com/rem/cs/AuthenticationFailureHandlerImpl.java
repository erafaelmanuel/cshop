package com.rem.cs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rem.cs.data.jpa.user.User;
import com.rem.cs.data.jpa.user.UserService;
import com.rem.cs.exception.EntityException;
import com.rem.cs.web.dto.Notification;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class AuthenticationFailureHandlerImpl implements AuthenticationFailureHandler {

    final private UserService userService;

    public AuthenticationFailureHandlerImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
        final String username = request.getParameter("username");
        final User user;
        final Notification notification = new Notification();

        notification.setTitle("Bad credentials");
        notification.setMessage("Incorrect email or password.");
        try {
            user = userService.findByEmail(username);
            if (!user.isActivated()) {
                final StringBuilder builder = new StringBuilder();
                builder.append("<form class='notif-link' action='register/resend-confirmation' method='post'>");
                builder.append("<input name='email' type='hidden' value='");
                builder.append(username).append("'/>");
                builder.append("<input type='submit' value='here'/>");
                builder.append("</form>");

                notification.setTitle("Account hasn't been activated");
                notification.setMessage("Please activate your account " + builder.toString() + ".");
            }
        } catch (EntityException e) {
        }

        response.setContentType("application/json; charset=utf-8");
        response.setStatus(500);
        response.getWriter().write(new ObjectMapper().writeValueAsString(notification));
        response.getWriter().flush();

        clearAuthenticationAttributes(request);
    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }

}
