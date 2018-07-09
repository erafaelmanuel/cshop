//package io.ermdev.cshop.security.config;
//
//import io.ermdev.cshop.data.entity.User;
//import io.ermdev.cshop.data.service.UserService;
//import io.ermdev.cshop.exception.EntityException;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.DefaultRedirectStrategy;
//import org.springframework.security.web.RedirectStrategy;
//import org.springframework.security.web.WebAttributes;
//import org.springframework.security.web.authentication.AuthenticationFailureHandler;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
//
//@Component
//public class AuthenticationFailureHandlerImpl implements AuthenticationFailureHandler {
//
//    final private UserService userService;
//
//    final private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
//
//    public AuthenticationFailureHandlerImpl(UserService userService) {
//        this.userService = userService;
//    }
//
//    @Override
//    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
//                                        AuthenticationException exception) throws IOException {
//        final String username = request.getParameter("username");
//        String urlToRedirect = "/login?error";
//        try {
//            final User user = userService.findByUsername(username);
//            if (!user.getEnabled())
//                urlToRedirect = "/register/complete?userId=" + user.getId();
//        } catch (EntityException e) {
//            e.printStackTrace();
//        }
//        redirectStrategy.sendRedirect(request, response, urlToRedirect);
//        clearAuthenticationAttributes(request);
//    }
//
//    private void clearAuthenticationAttributes(HttpServletRequest request) {
//        HttpSession session = request.getSession(false);
//        if (session == null) {
//            return;
//        }
//        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
//    }
//}
