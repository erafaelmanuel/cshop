package io.ermdev.cshop.web.controller;

import io.ermdev.cshop.data.entity.User;
import io.ermdev.cshop.exception.EntityException;
import io.ermdev.cshop.data.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes({"cartItems", "user"})
public class LoginController {

    private UserService userService;

    private LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("login")
    public String getLogin() {
        return "login";
    }

    @PostMapping("login/success")
    public String getLoginSuccess(Authentication authentication, Model model) {
        try {
            final User user = userService.findByUsername(authentication.getName());
            model.addAttribute("user", user);
            return "redirect:/catalog";
        } catch (EntityException e) {
            model.addAttribute("message", e.getMessage());
            return "error/500";
        }
    }
}