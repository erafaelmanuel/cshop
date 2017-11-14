package io.ermdev.cshop.web.controller;

import io.ermdev.cshop.data.service.MailService;
import io.ermdev.cshop.web.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.validation.Valid;

@Controller
@SessionAttributes({"cartItems"})
public class RegisterController {

    private MailService mailService;

    @Autowired
    public RegisterController(MailService mailService) {
        this.mailService = mailService;
    }

    @GetMapping("join")
    public String showRegister(Model model, UserDto userDto) {
        model.addAttribute("user", userDto);
        return "v2/register";
    }

    @PostMapping("join")
    public String registerUser(@ModelAttribute("user") @Valid UserDto userDto, BindingResult result, Model model) {
        if(!result.hasErrors()) {
            return "v2/login";
        } else {
            result.rejectValue("email","message.regError");
            return showRegister(model, userDto);
        }
    }
}
