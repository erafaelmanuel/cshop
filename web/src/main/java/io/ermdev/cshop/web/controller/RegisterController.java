package io.ermdev.cshop.web.controller;

import io.ermdev.cshop.business.register.RegisterEvent;
import io.ermdev.cshop.business.register.RegisterSource;
import io.ermdev.cshop.business.register.ResendEvent;
import io.ermdev.cshop.business.register.ResendSource;
import io.ermdev.cshop.commons.ReturnValue;
import io.ermdev.cshop.data.entity.Token;
import io.ermdev.cshop.data.entity.User;
import io.ermdev.cshop.data.service.TokenService;
import io.ermdev.cshop.data.service.UserService;
import io.ermdev.cshop.exception.EntityException;
import io.ermdev.cshop.exception.ResourceException;
import io.ermdev.cshop.web.dto.UserDto;
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

@Controller
@SessionAttributes({"cartItems"})
public class RegisterController {

    private UserService userService;
    private TokenService tokenService;
    private ApplicationEventPublisher publisher;
    private MessageSource messageSource;
    private SimpleMapper simpleMapper;

    @Autowired
    public RegisterController(UserService userService, TokenService tokenService, ApplicationEventPublisher publisher,
                              MessageSource messageSource, SimpleMapper simpleMapper) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.publisher = publisher;
        this.messageSource = messageSource;
        this.simpleMapper = simpleMapper;
    }

    @GetMapping("register")
    public String showRegister(UserDto userDto, Model model) {
        model.addAttribute("user", userDto);
        return "register";
    }

    @PostMapping("register")
    public String registerUser(@ModelAttribute("user") @Valid UserDto userDto, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            final User user = simpleMapper.set(userDto).mapTo(User.class);
            final String url = messageSource.getMessage("cshop.url", null, null);
            final RegisterSource registerSource = new RegisterSource();

            registerSource.setUser(user);
            registerSource.setUrl(url);
            registerSource.setLocale(null);

            RegisterEvent registerEvent = new RegisterEvent(registerSource);
            registerEvent.setOnRegisterFailure(() -> result.reject("email"));

            publisher.publishEvent(registerEvent);
            model.addAttribute("userId", user.getId());
        }
        if (result.hasErrors()) {
            result.rejectValue("email", "message.error");
            return showRegister(userDto, model);
        }
        return showRegisterComplete(model);
    }

    @GetMapping("register/complete")
    public String showRegisterComplete(Model model, @RequestParam(value = "userId", required = false) Long userId) {
        if (userId == null) {
            return showRegister(new UserDto(), model);
        } else {
            model.addAttribute("userId", userId);
            return "register-complete";
        }
    }

    @PostMapping("register/complete")
    public String showRegisterComplete(Model model) {
        return "register-complete";
    }

    @GetMapping("register/confirmation")
    public String registerConfirmation(@RequestParam("token") String key, Model model) {
        try {
            final Token token = tokenService.findByKey(key);
            final Calendar calendar = Calendar.getInstance();
            final long remainingTime = token.getExpiryDate().getTime() - calendar.getTime().getTime();

            if (remainingTime > 0) {
                User user = token.getUser();
                user.setEnabled(true);
                userService.save(user);
                tokenService.delete(token.getId());
            } else {
                throw new ResourceException("Token is expired");
            }
            model.addAttribute("activation", true);
            return "login";
        } catch (EntityException | ResourceException e) {
            model.addAttribute("message", e.getMessage());
            return "error/403";
        }
    }

    @PostMapping("register/resend-verification")
    public String resendConfirmationMail(@RequestParam("userId") Long userId, Model model) {
        try {
            if (userId != null) {
                final String url = messageSource.getMessage("cshop.url", null, null);
                final ResendSource resendSource = new ResendSource();

                resendSource.setUserId(userId);
                resendSource.setUrl(url);
                resendSource.setLocale(null);

                ResendEvent resendEvent = new ResendEvent(resendSource);
                ReturnValue returnValue = new ReturnValue();
                resendEvent.setOnResendFinished(returnValue::setHasError);
                publisher.publishEvent(resendEvent);
                if (!returnValue.hasError()) {
                    model.addAttribute("userId", userId);
                    return showRegisterComplete(model);
                } else {
                    throw new ResourceException("Invalid request");
                }
            } else {
                return "register";
            }
        } catch (ResourceException e) {
            model.addAttribute("message", e.getMessage());
            return "error/403";
        }
    }
}