package com.rem.cs.web.controller;

import com.rem.cs.data.jpa.entity.Token;
import com.rem.cs.data.jpa.repository.TokenRepository;
import com.rem.cs.data.jpa.entity.User;
import com.rem.cs.data.jpa.service.UserService;
import com.rem.cs.exception.EntityException;
import com.rem.cs.web.dto.UserDto;
import com.rem.cs.web.event.UserEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Calendar;
import java.util.HashMap;

@Controller
public class RegisterController {

    private UserService userService;
    private TokenRepository tokenRepository;
    private MessageSource messageSource;
    private HttpServletRequest request;
    private ApplicationEventPublisher publisher;

    @Autowired
    public RegisterController(UserService userService, TokenRepository tokenRepository, MessageSource
            messageSource, HttpServletRequest request, ApplicationEventPublisher publisher) {
        this.userService = userService;
        this.tokenRepository = tokenRepository;
        this.messageSource = messageSource;
        this.request = request;
        this.publisher = publisher;
    }

    @ModelAttribute("user")
    public UserDto setUpUser() {
        return new UserDto();
    }

    @ModelAttribute("token")
    public Token setUpToken() {
        return null;
    }

    @ModelAttribute("token")
    public String setUpTitle() {
        return messageSource.getMessage("reg.title", null, null);
    }

    @ModelAttribute("token")
    public String setUpSubtitle() {
        return messageSource.getMessage("reg.subtitle", null, null);
    }

    @GetMapping("/register")
    public String getRegister() {
        return "register/sign-up";
    }

    @PostMapping("/register")
    public String onRegister(@ModelAttribute("user") @Valid UserDto userDto, BindingResult result, Model model) {
        final HashMap<String, Object> hashMap = new HashMap<>();

        if (!result.hasErrors()) {
            hashMap.put("do", UserEvent.CREATE_USER);
            hashMap.put("user", userDto);
            hashMap.put("baseUrl", request.getRequestURL().toString().replace(request.getRequestURI(), "/"));

            publisher.publishEvent(new UserEvent(hashMap));
            model.addAttribute("email", userDto.getEmail());
            return "register/validating";
        } else {
            return "register/sign-up";
        }
    }

    @GetMapping("register/activate")
    public String onActivate(@RequestParam(value = "uid", required = false) String userId,
                             @RequestParam(value = "tid", required = false) String tokenId) {
        try {
            final HashMap<String, Object> hashMap = new HashMap<>();
            final User user = userService.findById(userId);
            final Token token = tokenRepository.findById(tokenId).orElse(null);
            final Calendar calendar = Calendar.getInstance();

            if (user.isActivated()) {
                return "error/500";
            }
            if (token == null || !(token.getExpiryDate().getTime() - calendar.getTime().getTime() > 0)) {
                return "error/500";
            }
            hashMap.put("do", UserEvent.ACTIVATE_USER);
            hashMap.put("user", user);
            hashMap.put("token", token);
            publisher.publishEvent(new UserEvent(hashMap));
            return "redirect:/catalog";
        } catch (EntityException e) {
            return "error/500";
        }
    }

    @PostMapping("register/resend-confirmation")
    public String onResendConfirmationEmail(@RequestParam(value = "email") String email, Model model) {
        try {
            final HashMap<String, Object> hashMap = new HashMap<>();
            final User user = userService.findByEmail(email);

            if (!user.isActivated()) {
                hashMap.put("do", UserEvent.RESEND_CONFIRMATION_EMAIL);
                hashMap.put("user", user);
                hashMap.put("baseUrl", request.getRequestURL().toString().replace(request.getRequestURI(), "/"));

                publisher.publishEvent(new UserEvent(hashMap));
                model.addAttribute("email", email);
                return "register/validating";
            } else {
                return "redirect:/login";
            }
        } catch (EntityException e) {
            return "error/500";
        }
    }

    @PostMapping("register/change-email-address")
    public String onChangeEmailAddress(@RequestParam(value = "email") String email,
                                       @RequestParam(value = "new_email") String newEmail, Model model) {
        try {
            final HashMap<String, Object> hashMap = new HashMap<>();
            final User user = userService.findByEmail(email);

            hashMap.put("do", UserEvent.CHANGE_EMAIL_ADDRESS);
            hashMap.put("user", user);
            hashMap.put("email", newEmail);
            hashMap.put("baseUrl", request.getRequestURL().toString().replace(request.getRequestURI(), "/"));

            publisher.publishEvent(new UserEvent(hashMap));
            model.addAttribute("email", newEmail);
            return "register/validating";
        } catch (EntityException e) {
            e.printStackTrace();
            return "error/500";
        }
    }
}
