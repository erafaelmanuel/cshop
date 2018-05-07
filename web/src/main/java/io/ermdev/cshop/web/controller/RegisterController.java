package io.ermdev.cshop.web.controller;

import io.ermdev.cshop.business.register.*;
import io.ermdev.cshop.commons.ReturnValue;
import io.ermdev.cshop.data.entity.User;
import io.ermdev.cshop.exception.ResourceException;
import io.ermdev.cshop.web.dto.UserDto;
import mapfierj.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@SessionAttributes({"cartItems"})
public class RegisterController {

    private ApplicationEventPublisher publisher;

    private MessageSource messageSource;

    private Mapper mapper;

    @Autowired
    public RegisterController(ApplicationEventPublisher publisher, MessageSource messageSource, Mapper mapper) {
        this.publisher = publisher;
        this.messageSource = messageSource;
        this.mapper = mapper;
    }

    @GetMapping("register")
    public String getRegister(UserDto userDto, Model model) {
        model.addAttribute("userDto", userDto);
        return "register";
    }

    @PostMapping("register")
    public String register(@ModelAttribute("userDto") @Valid UserDto userDto, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            final User user = mapper.set(userDto).mapTo(User.class);
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
            return getRegister(userDto, model);
        }
        return showRegisterComplete(model);
    }

    @GetMapping("register/complete")
    public String showRegisterComplete(Model model, @RequestParam(value = "userId", required = false) Long userId) {
        if (userId == null) {
            return getRegister(new UserDto(), model);
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
    public String confirmUser(@RequestParam("token") String key, Model model) {
        try {
            if (key != null) {
                final ReturnValue returnValue = new ReturnValue();
                final ConfirmSource confirmSource = new ConfirmSource(key);
                final ConfirmEvent confirmEvent = new ConfirmEvent(confirmSource);

                confirmEvent.setOnConfirmCompleted(returnValue::setHasError);
                publisher.publishEvent(confirmEvent);
                if (!returnValue.hasError()) {
                    model.addAttribute("activation", true);
                    return "login";
                } else {
                    throw new ResourceException("Invalid Request");
                }
            } else {
                return getRegister(new UserDto(), model);
            }
        } catch (ResourceException e) {
            model.addAttribute("message", e.getMessage());
            return "error/403";
        }
    }

    @PostMapping("register/resend-verification")
    public String resendConfirm(@RequestParam("userId") Long userId, Model model) {
        try {
            if (userId != null) {
                final String url = messageSource.getMessage("cshop.url", null, null);
                final ResendSource resendSource = new ResendSource();

                resendSource.setUserId(userId);
                resendSource.setUrl(url);
                resendSource.setLocale(null);

                ResendEvent resendEvent = new ResendEvent(resendSource);
                ReturnValue returnValue = new ReturnValue();
                resendEvent.setOnResendCompleted(returnValue::setHasError);
                publisher.publishEvent(resendEvent);
                if (!returnValue.hasError()) {
                    model.addAttribute("userId", userId);
                    return showRegisterComplete(model);
                } else {
                    throw new ResourceException("Invalid request");
                }
            } else {
                return getRegister(new UserDto(), model);
            }
        } catch (ResourceException e) {
            model.addAttribute("message", e.getMessage());
            return "error/403";
        }
    }
}