package com.rem.cs.web.controller;

import com.rem.cs.web.dto.UserDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes({"signedInUser"})
public class LoginController {

    @ModelAttribute("signedInUser")
    public UserDto setUpUser() {
        return new UserDto();
    }

    @GetMapping("/login")
    public String getLogin(@SessionAttribute(name = "signedInUser", required = false) UserDto user) {
        if (user != null) {
            return "redirect:/catalog";
        } else {
            return "login";
        }
    }
}
