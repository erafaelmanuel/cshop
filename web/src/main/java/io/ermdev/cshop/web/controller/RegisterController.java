package io.ermdev.cshop.web.controller;

import io.ermdev.cshop.business.event.RegisterEvent;
import io.ermdev.cshop.data.exception.EmailExistsException;
import io.ermdev.cshop.data.exception.EntityNotFoundException;
import io.ermdev.cshop.data.exception.UnsatisfiedEntityException;
import io.ermdev.cshop.data.service.UserService;
import io.ermdev.cshop.data.service.VerificationTokenService;
import io.ermdev.cshop.model.entity.User;
import io.ermdev.cshop.model.entity.VerificationToken;
import io.ermdev.cshop.web.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Calendar;

@Controller
@SessionAttributes({"cartItems"})
public class RegisterController {

    private UserService userService;
    private VerificationTokenService verificationTokenService;
    private ApplicationEventPublisher publisher;

    @Autowired
    public RegisterController(UserService userService, VerificationTokenService verificationTokenService,
                              ApplicationEventPublisher publisher) {
        this.userService = userService;
        this.verificationTokenService = verificationTokenService;
        this.publisher = publisher;
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
    public String registerUser(@ModelAttribute("user") @Valid UserDto userDto, BindingResult result,
                               HttpServletRequest request, Model model) {
        if(!result.hasErrors()) {
            try {
                User user = new User();
                user.setName(userDto.getName());
                user.setPassword(userDto.getPassword());
                user.setEmail(userDto.getEmail());
                user.setUsername(userDto.getEmail());
                userService.add(user);

                publisher.publishEvent(new RegisterEvent(user, request.getContextPath(), null));
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

    @GetMapping("register/confirmation")
    public String registerConfirmation(@RequestParam("token") String token, Model model) {
        if(token == null) {
            model.addAttribute("message", "Bad request");
            return "v2/error";
        }
        final VerificationToken verificationToken = verificationTokenService.findByToken(token);
        final Calendar calendar = Calendar.getInstance();

        if(verificationToken==null || (verificationToken.getExpiryDate().getTime() - calendar.getTime().getTime()) <= 0) {
            model.addAttribute("message", "Invalid token");
            return "v2/error";
        } else {
            try {
                final long userId = verificationToken.getUserId();
                final long verificationId = verificationToken.getId();

                User user = userService.findById(userId);
                user.setEnabled(true);

                userService.updateById(user.getId(), user);
                verificationTokenService.deleteById(verificationId);
            } catch (EntityNotFoundException e) {
                e.printStackTrace();
                model.addAttribute("message", "Bad request");
                return "v2/error";
            }
        }
        model.addAttribute("activation", true);
        return "v2/login";
    }
}
