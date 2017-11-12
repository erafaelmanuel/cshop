package io.ermdev.cshop.web.controller;

import io.ermdev.cshop.data.exception.EntityNotFoundException;
import io.ermdev.cshop.data.service.UserService;
import io.ermdev.cshop.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpSession;

@Controller
@SessionAttributes({"user", "authenticate", "cartItems"})
public class LoginController {

    private UserService userService;

    @Autowired
    private LoginController(UserService userService) {
        this.userService=userService;
    }

    @GetMapping("login")
    public String showLoginPage() {
        return "v2/login";
    }

    @PostMapping("login")
    public String performLogin(@RequestParam("username") String username, @RequestParam("password") String password,
                          ModelMap model) {
        try {
            boolean authenticate = false;
            User user = null;
            if (username == null || username.trim().equals("")) {
                return "redirect:/login?error";
            }
            if (password == null || password.trim().equals("")) {
                return "redirect:/login?error";
            }
            for (User _user : userService.findAll()) {
                if(username.trim().equals(_user.getUsername()) && password.trim().equals(_user.getPassword())) {
                    authenticate=true;
                    user=_user;
                }
            }
            if(authenticate) {
                model.put("authenticate", true);
                model.put("user", user);
                return "redirect:/";
            } else
                return "redirect:/login?error";
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return "error";
        }
    }

    @GetMapping("logout")
    public String performLogout(ModelMap model, HttpSession session) {
        model.remove("authenticate");
        model.remove("user");

        session.removeAttribute("authenticate");
        session.removeAttribute("user");

        return "redirect:/login";
    }
}