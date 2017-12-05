package io.ermdev.cshop.web.controller;

import io.ermdev.cshop.data.exception.EntityNotFoundException;
import io.ermdev.cshop.data.service.UserService;
import io.ermdev.cshop.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes({"hasUser", "userName", "cartItems"})
public class LoginController {

    private UserService userService;

    @Autowired
    private LoginController(UserService userService) {
        this.userService=userService;
    }

    @GetMapping("login")
    public String showLogin() {
        return "v2/login";
    }

    @PostMapping("login/success")
    public String redirectAfterLoginSuccess(Authentication authentication, Model model) {
        try {
            User user = userService.findByUsername(authentication.getName());
            model.addAttribute("userName", user.getName().trim().toUpperCase().charAt(0));
        } catch (EntityNotFoundException e) {
            model.addAttribute("message", e.getMessage());
            return "v2/error/500";
        }
        model.addAttribute("hasUser", true);
        return "redirect:/catalog";
    }
}