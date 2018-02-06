package io.ermdev.cshop.web.interceptor;

import io.ermdev.cshop.data.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) {
        try {
            final HttpSession session = request.getSession();
            if (request.getMethod().equalsIgnoreCase("GET")) {
                final User user = (User) session.getAttribute("user");
                if(user != null) {
                    response.sendRedirect("/");
                    return false;
                }
            }
            throw new Exception();
        } catch (Exception e) {
            return true;
        }
    }
}
