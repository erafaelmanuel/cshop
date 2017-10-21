package io.ermdev.ecloth.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("login")
    public String login(Model model) {
        model.addAttribute("name","Rafael");
        return "login";
    }
}