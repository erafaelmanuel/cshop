package io.ermdev.cshop.web.controller;

import io.ermdev.cshop.web.exception.TokenException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes({"cartItems", "hasUser", "userName"})
public class AccountController {

    @GetMapping("account/dashboard")
    public String showDashboard() {
        return "v2/dashboard";
    }

    @GetMapping("logout")
    public String doLogout() {
        return "redirect:/login";
    }
}
