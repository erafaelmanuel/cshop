package com.rem.cs;

import com.rem.cs.data.jpa.user.User;
import com.rem.cs.data.jpa.user.UserService;
import com.rem.cs.exception.EntityException;
import com.rem.cs.web.dto.UserDto;
import com.rem.mappyfy.Mapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    final private UserService userService;

    public AuthenticationSuccessHandlerImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        try {
            final String username = request.getParameter("username");
            final User user = userService.findByEmail(username);

            final boolean CREATE_NEW_SESSION = true;
            final HttpSession session = request.getSession(CREATE_NEW_SESSION);

            if (session != null) {
                session.setAttribute("signedInUser", new Mapper().set(user)
                        .ignore("roles")
                        .mapTo(UserDto.class));
            }
            response.setStatus(200);
        } catch (EntityException e) {
            e.printStackTrace();
        }
    }
}
