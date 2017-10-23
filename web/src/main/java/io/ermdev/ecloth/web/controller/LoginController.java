package io.ermdev.ecloth.web.controller;

import io.ermdev.ecloth.data.exception.EntityNotFoundException;
import io.ermdev.ecloth.data.service.UserService;
import io.ermdev.ecloth.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    private UserService userService;

    @Autowired
    private LoginController(UserService userService) {
        this.userService=userService;
    }

    @GetMapping("login")
    public String showLogin() {
        return "login";
    }

    @PostMapping("login")
    public String doLogin(@RequestParam("username") String username, @RequestParam("password") String password,
                          ModelMap model) {
        try {
            boolean authenticate = false;
            if (username == null || username.trim().equals("")) {
                return "redirect:/login?error=true";
            }
            if (password == null || password.trim().equals("")) {
                return "redirect:/login?error=true";
            }
            for (User user : userService.getAll()) {
                if(username.trim().equals(user.getUsername()) && password.trim().equals(user.getPassword()))
                    authenticate = true;
            }
            if(authenticate)
                return "index";
            else
                return "redirect:/login?error=true";
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return "error";
        }
    }
}