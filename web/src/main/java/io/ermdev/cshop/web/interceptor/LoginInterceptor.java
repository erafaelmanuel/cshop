package io.ermdev.cshop.web.interceptor;

import io.ermdev.cshop.model.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.logging.Logger;

@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    private static Logger logger = Logger.getLogger(LoginInterceptor.class.getSimpleName());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        logger.info("Login Interceptor");
        logger.info(request.getMethod());
        try {
            final HttpSession session = request.getSession();
            if (request.getMethod().equalsIgnoreCase("GET") && session != null) {
                final boolean auth = session.getAttribute("authenticate") != null && (boolean) session.getAttribute("authenticate");
                final User user = (User) session.getAttribute("user");

                if (auth && user != null) {
                    response.sendRedirect("/");
                    return false;
                }
            }
            return true;
        } catch (NullPointerException e) {
            return true;
        }
    }
}
