package com.rem.cs;

import com.rem.cs.data.jpa.user.User;
import com.rem.cs.data.jpa.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    final private UserRepository userRepository;
    final private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    public AuthenticationSuccessHandlerImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        final String username = request.getParameter("username");
        final User user = userRepository.findByEmail(username).orElse(null);

        if (user != null) {
            final boolean CREATE_NEW_SESSION = true;
            final HttpSession session = request.getSession(CREATE_NEW_SESSION);

            if (session != null) {
                session.setAttribute("verifiedUser", user);
            }
            redirectStrategy.sendRedirect(request, response, "/catalog");
        } else {
            redirectStrategy.sendRedirect(request, response, "/");
        }
    }
}
