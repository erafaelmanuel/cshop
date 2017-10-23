package io.ermdev.ecloth.web.controller;

import io.ermdev.ecloth.data.exception.EntityNotFoundException;
import io.ermdev.ecloth.data.service.UserService;
import io.ermdev.ecloth.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@SessionAttributes({"user", "authenticate"})
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

    @GetMapping("index")
    public String index() {
        return "index";
    }

    @PostMapping("login")
    public String doLogin(@RequestParam("username") String username, @RequestParam("password") String password,
                          ModelMap model) {
        try {
            boolean authenticate = false;
            User user = null;
            if (username == null || username.trim().equals("")) {
                return "redirect:/login?error=true";
            }
            if (password == null || password.trim().equals("")) {
                return "redirect:/login?error=true";
            }
            for (User _user : userService.getAll()) {
                if(username.trim().equals(_user.getUsername()) && password.trim().equals(_user.getPassword())) {
                    authenticate=true;
                    user=_user;
                }
            }
            if(authenticate) {
                model.put("authenticate", true);
                model.put("user", user);
                return "index";
            } else
                return "redirect:/login?error=true";
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return "error";
        }
    }
}