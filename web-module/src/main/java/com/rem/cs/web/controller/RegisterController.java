package com.rem.cs.web.controller;

import com.rem.cs.data.jpa.token.Token;
import com.rem.cs.data.jpa.token.TokenRepository;
import com.rem.cs.data.jpa.user.User;
import com.rem.cs.data.jpa.user.UserRepository;
import com.rem.cs.web.dto.UserDto;
import io.ermdev.cshop.commons.IdGenerator;
import mapfierj.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class RegisterController {

    private UserRepository userRepository;
    private TokenRepository tokenRepository;
    private MessageSource messageSource;
    private HttpServletRequest request;

    @Autowired
    public RegisterController(UserRepository userRepository, TokenRepository tokenRepository, MessageSource
            messageSource, HttpServletRequest request) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.messageSource = messageSource;
        this.request = request;
    }

    @ModelAttribute
    public void init(Model model) {
        model.addAttribute("user", new UserDto());
        model.addAttribute("title", messageSource.getMessage("reg.title", null, null));
        model.addAttribute("subtitle", messageSource.getMessage("reg.subtitle", null, null));
    }

    @GetMapping("/account/register")
    public String getRegisterPage() {
        return "register";
    }

    @PostMapping("/register")
    public String registerNewUser(@ModelAttribute("user") @Valid UserDto userDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "register";
        }
        if (userRepository.findByEmail(userDto.getEmail()) != null) {
            result.reject("Email already exist!");
        }
        User user = new Mapper().set(userDto).mapTo(User.class);
        user.setId(String.valueOf(IdGenerator.randomUUID()));
        user.setActivated(false);

        Token token = new Token();
        token.setKey(String.valueOf(IdGenerator.randomUUID()));

        System.out.print(request.getContextPath());


        return "register-success";
    }
}
