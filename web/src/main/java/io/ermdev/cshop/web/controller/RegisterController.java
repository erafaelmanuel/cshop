package io.ermdev.cshop.web.controller;

import io.ermdev.cshop.business.register.RegisterEvent;
import io.ermdev.cshop.business.register.RegisterSource;
import io.ermdev.cshop.business.verification.VerificationEvent;
import io.ermdev.cshop.business.verification.VerificationSource;
import io.ermdev.cshop.commons.DateHelper;
import io.ermdev.cshop.data.entity.Token;
import io.ermdev.cshop.data.entity.User;
import io.ermdev.cshop.data.entity.VerificationToken;
import io.ermdev.cshop.data.service.TokenService;
import io.ermdev.cshop.data.service.UserService;
import io.ermdev.cshop.exception.EntityException;
import io.ermdev.cshop.web.dto.UserDto;
import io.ermdev.cshop.web.exception.TokenException;
import io.ermdev.mapfierj.SimpleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Calendar;
import java.util.UUID;

@Controller
@SessionAttributes({"cartItems"})
public class RegisterController {

    private final String URL = "cshop.url";

    private UserService userService;
    private TokenService tokenService;
    private ApplicationEventPublisher publisher;
    private MessageSource messageSource;
    private SimpleMapper simpleMapper;
    private DateHelper dateHelper;

    @Autowired
    public RegisterController(UserService userService, TokenService tokenService, ApplicationEventPublisher publisher,
                              MessageSource messageSource, SimpleMapper simpleMapper, DateHelper dateHelper) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.publisher = publisher;
        this.messageSource = messageSource;
        this.simpleMapper = simpleMapper;
        this.dateHelper = dateHelper;
    }

    @GetMapping("register")
    public String showRegister(UserDto userDto, Model model) {
        model.addAttribute("user", userDto);
        return "register";
    }

    @PostMapping("register")
    public String registerUser(@ModelAttribute("user") @Valid UserDto userDto, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            try {
                final String generatedUsername = userDto.getEmail().split("@")[0];
                final String generatedTokenKey = UUID.randomUUID().toString();
                final String url = messageSource.getMessage(URL, null, null);

                User user = simpleMapper.set(userDto).mapAllTo(User.class);
                user.setUsername(generatedUsername);
                user = userService.save(user);

                Token token = new Token();
                token.setKey(generatedTokenKey);
                token.setExpiryDate(dateHelper.setTimeNow().addTimeInMinute(DateHelper.DAY_IN_MINUTE).getDate());
                token.setUser(user);

                RegisterSource registerSource = new RegisterSource(token, url, null);
                publisher.publishEvent(new RegisterEvent(registerSource));
                model.addAttribute("userId", user.getId());
            } catch (EntityException e) {
                result.rejectValue("email", "message.error");
            }
        }
        if (result.hasErrors()) {
            result.rejectValue("email", "message.error");
            return showRegister(userDto, model);
        }
        return showRegisterComplete(model);
    }

    @PostMapping("register/complete")
    public String showRegisterComplete(Model model) {
        return "register-complete";
    }

    @GetMapping("register/complete")
    public String showRegisterComplete(Model model, @RequestParam(value = "userId", required = false) Long userId) {
        if (userId == null) {
            UserDto userDto = new UserDto();
            model.addAttribute("user", userDto);
            return "register";
        } else {
            model.addAttribute("userId", userId);
            return "register-complete";
        }
    }

    @GetMapping("register/confirmation")
    public String registerConfirmation(@RequestParam("token") String key, Model model) {
        try {
            if (key != null) {
                final Token token = tokenService.findByKey(key);
                final Calendar calendar = Calendar.getInstance();
                final long remainingTime = token.getExpiryDate().getTime() - calendar.getTime().getTime();

                if (remainingTime <= 0) {
                    throw new TokenException("Token is expired");
                } else {
                    Long tokenId = token.getId();
                    User user = token.getUser();
                    user.setEnabled(true);

                    userService.save(user);
                    tokenService.delete(tokenId);
                }
                model.addAttribute("activation", true);
                return "login";
            } else {
                throw new TokenException("No token found.");
            }
        } catch (EntityException | TokenException e) {
            model.addAttribute("message", e.getMessage());
            return "error/403";
        }
    }

    @PostMapping("register/resend-verification")
    public String resendVerificationToken(@RequestParam("userId") Long userId, Model model) {
        try {
            if (userId != null) {
                final User user = userService.findById(userId);
                final String newToken = UUID.randomUUID().toString();
                final String url = messageSource.getMessage("cshop.url", null, null);
                final VerificationSource verificationSource = new VerificationSource();

                verificationSource.setVerificationToken(new VerificationToken(newToken, user));
                verificationSource.setUrl(url);
                if (!user.getEnabled()) {
                    publisher.publishEvent(new VerificationEvent(verificationSource));
                    model.addAttribute("userId", userId);
                    return showRegisterComplete(model);
                }
                throw new TokenException("Your email already registered");
            } else {
                return "register";
            }
        } catch (EntityException | TokenException e) {
            model.addAttribute("message", e.getMessage());
            return "error/403";
        }
    }
}