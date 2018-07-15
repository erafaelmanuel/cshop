package com.rem.cs.web.controller;

import com.rem.cs.data.jpa.token.Token;
import com.rem.cs.data.jpa.token.TokenRepository;
import com.rem.cs.data.jpa.user.User;
import com.rem.cs.data.jpa.user.UserRepository;
import com.rem.cs.web.dto.UserDto;
import com.rem.cs.web.event.UserEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Calendar;
import java.util.HashMap;

@SessionAttributes({"verifiedUser", "cartItems"})
@Controller
public class AccountController {

    private UserRepository userRepository;
    private TokenRepository tokenRepository;
    private MessageSource messageSource;
    private HttpServletRequest request;
    private ApplicationEventPublisher publisher;

    @Autowired
    public AccountController(UserRepository userRepository, TokenRepository tokenRepository, MessageSource
            messageSource, HttpServletRequest request, ApplicationEventPublisher publisher) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.messageSource = messageSource;
        this.request = request;
        this.publisher = publisher;
    }

    @ModelAttribute
    public void init(Model model) {
        model.addAttribute("user", new UserDto());
        model.addAttribute("token", null);
        model.addAttribute("title", messageSource.getMessage("reg.title", null, null));
        model.addAttribute("subtitle", messageSource.getMessage("reg.subtitle", null, null));
    }

    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }

    @GetMapping("/register")
    public String getRegister() {
        return "reg-sign-up";
    }

    @PostMapping("/register")
    public String onRegister(@ModelAttribute("user") @Valid UserDto userDto, BindingResult result, Model model) {
        final HashMap<String, Object> hashMap = new HashMap<>();

        if (result.hasErrors() || userRepository.findByEmail(userDto.getEmail()).orElse(null) != null) {
            result.rejectValue("email", "message.error");
            return "reg-sign-up";
        } else {
            hashMap.put("do", UserEvent.CREATE_USER);
            hashMap.put("user", userDto);
            hashMap.put("request", request);
            publisher.publishEvent(new UserEvent(hashMap));
            return "reg-validating";
        }
    }

    @GetMapping("register/activate")
    public String onActivate(@RequestParam(value = "uid", required = false) String userId,
                             @RequestParam(value = "tid", required = false) String tokenId) {
        final HashMap<String, Object> hashMap = new HashMap<>();
        final User user = userRepository.findById(userId).orElse(null);
        final Token token = tokenRepository.findById(tokenId).orElse(null);
        final Calendar calendar = Calendar.getInstance();

        if (user == null || user.isActivated()) {
            return "error/500";
        }
        if (token == null || !(token.getExpiryDate().getTime() - calendar.getTime().getTime() > 0)) {
            return "error/500";
        }
        hashMap.put("do", UserEvent.ACTIVATE_USER);
        hashMap.put("user", user);
        hashMap.put("token", token);
        publisher.publishEvent(new UserEvent(hashMap));
        return "1";
    }


}
