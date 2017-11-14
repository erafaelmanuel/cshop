package io.ermdev.cshop.web.controller;

import io.ermdev.cshop.data.exception.EmailExistsException;
import io.ermdev.cshop.data.exception.UnsatisfiedEntityException;
import io.ermdev.cshop.data.service.MailService;
import io.ermdev.cshop.data.service.UserService;
import io.ermdev.cshop.model.entity.User;
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
    private UserService userService;

    @Autowired
    public RegisterController(UserService userService, MailService mailService) {
        this.userService = userService;
        this.mailService = mailService;
    }

    @GetMapping("register")
    public String showRegister(Model model, UserDto userDto) {
        model.addAttribute("user", userDto);
        return "v2/register";
    }

    @GetMapping("register-complete")
    public String showRegisterComplete(){
        return "v2/register-complete";
    }

    @PostMapping("register")
    public String registerUser(@ModelAttribute("user") @Valid UserDto userDto, BindingResult result, Model model) {
        if(!result.hasErrors()) {
            try {
                User user = new User();
                user.setName(userDto.getName());
                user.setPassword(userDto.getPassword());
                user.setEmail(userDto.getEmail());
                user.setUsername(userDto.getEmail());
                userService.add(user);
            } catch (UnsatisfiedEntityException | EmailExistsException e) {
                result.rejectValue("email", "message.regError");
            }
        }
        if(result.hasErrors()) {
            result.rejectValue("email","message.regError");
            return showRegister(model, userDto);
        }
        return "redirect:/register-complete";
    }
}
