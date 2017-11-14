package io.ermdev.cshop.web.interceptor;

import io.ermdev.cshop.data.exception.EntityNotFoundException;
import io.ermdev.cshop.data.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    private UserService userService;

    @Autowired
    public LoginInterceptor(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        try {
            final HttpSession session = request.getSession();
            if (request.getMethod().equalsIgnoreCase("GET")) {
                String email = request.getUserPrincipal().getName();

                //Not exist will throw an EntityNotFoundException
                userService.findByEmail(email);

                response.sendRedirect("/");
                return false;
            }
            return true;
        } catch (EntityNotFoundException | NullPointerException e) {
            return true;
        }
    }
}
