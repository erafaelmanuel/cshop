package com.rem.cs.web.controller;

import com.rem.cs.data.jpa.entity.Token;
import com.rem.cs.data.jpa.repository.TokenRepository;
import com.rem.cs.exception.EntityException;
import com.rem.cs.rest.client.resource.user.User;
import com.rem.cs.rest.client.resource.user.UserService;
import com.rem.cs.web.dto.UserDto;
import com.rem.cs.web.event.RegistrationEvent;
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

@Controller("registerWebController")
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
    public UserDto initUser() {
        return new UserDto();
    }

    @ModelAttribute("token")
    public Token initToken() {
        return null;
    }

    @ModelAttribute("token")
    public String initTitle() {
        return messageSource.getMessage("reg.title", null, null);
    }

    @ModelAttribute("token")
    public String initSubtitle() {
        return messageSource.getMessage("reg.subtitle", null, null);
    }

    @GetMapping("/register")
    public String viewRegister() {
        return "register/sign-up";
    }

    @PostMapping("/register")
    public String doRegister(@ModelAttribute("user") @Valid UserDto userDto, BindingResult result, Model model) {
        final HashMap<String, Object> parameters = new HashMap<>();

        if (!result.hasErrors()) {
            parameters.put("do", RegistrationEvent.CREATE_USER);
            parameters.put("user", userDto);
            parameters.put("baseUrl", request.getRequestURL().toString().replace(request.getRequestURI(), "/"));

            publisher.publishEvent(new RegistrationEvent(parameters));
            model.addAttribute("email", userDto.getEmail());
            return "register/validating";
        } else {
            return "register/sign-up";
        }
    }

    @GetMapping("register/activate")
    public String doActivate(@RequestParam(value = "uid", required = false) String userId,
                             @RequestParam(value = "tid", required = false) String tokenId) {
        try {
            final HashMap<String, Object> parameters = new HashMap<>();
            final User user = userService.findById(userId).getContent();
            final Token token = tokenRepository.findById(tokenId).orElse(null);
            final Calendar calendar = Calendar.getInstance();

            if (user == null) {
                throw new EntityException("No user found");
            }
            if (user.isActivated()) {
                throw new EntityException("User is already activated");
            }
            if (token == null || !(token.getExpiryDate().getTime() - calendar.getTime().getTime() > 0)) {
                throw new EntityException("Invalid token");
            }
            parameters.put("do", RegistrationEvent.ACTIVATE_USER);
            parameters.put("user", user);
            parameters.put("token", token);

            publisher.publishEvent(new RegistrationEvent(parameters));
            return "redirect:/catalog";
        } catch (EntityException e) {
            return "error/500";
        }
    }

    @PostMapping("register/resend-confirmation")
    public String doResendConfirmationEmail(@RequestParam(value = "email") String email, Model model) {
        try {
            final HashMap<String, Object> parameters = new HashMap<>();
            final User user = userService.findByEmail(email).getContent();

            if (user == null) {
                throw new EntityException("No user found");
            }
            if (!user.isActivated()) {
                parameters.put("do", RegistrationEvent.RESEND_CONFIRMATION_EMAIL);
                parameters.put("user", user);
                parameters.put("baseUrl", request.getRequestURL().toString().replace(request.getRequestURI(), "/"));

                publisher.publishEvent(new RegistrationEvent(parameters));
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
    public String doChangeEmailAddress(@RequestParam(value = "email") String email,
                                       @RequestParam(value = "new_email") String newEmail, Model model) {
        try {
            final HashMap<String, Object> parameters = new HashMap<>();
            final User user = userService.findByEmail(email).getContent();

            if (user == null) {
                throw new EntityException("No user found");
            }
            parameters.put("do", RegistrationEvent.CHANGE_EMAIL_ADDRESS);
            parameters.put("user", user);
            parameters.put("email", newEmail);
            parameters.put("baseUrl", request.getRequestURL().toString().replace(request.getRequestURI(), "/"));

            publisher.publishEvent(new RegistrationEvent(parameters));
            model.addAttribute("email", newEmail);
            return "register/validating";
        } catch (EntityException e) {
            e.printStackTrace();
            return "error/500";
        }
    }
}
