package com.rem.cs.web.controller;

import com.rem.cs.data.jpa.token.Token;
import com.rem.cs.data.jpa.token.TokenRepository;
import com.rem.cs.data.jpa.user.User;
import com.rem.cs.data.jpa.user.UserRepository;
import com.rem.cs.web.dto.UserDto;
import io.ermdev.cshop.commons.DateHelper;
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
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Calendar;

@Controller
public class AccountController {

    private UserRepository userRepository;
    private TokenRepository tokenRepository;
    private MessageSource messageSource;
    private HttpServletRequest request;

    @Autowired
    public AccountController(UserRepository userRepository, TokenRepository tokenRepository, MessageSource
            messageSource, HttpServletRequest request) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.messageSource = messageSource;
        this.request = request;
    }

    @ModelAttribute
    public void init(Model model) {
        model.addAttribute("user", new UserDto());
        model.addAttribute("token", null);
        model.addAttribute("title", messageSource.getMessage("reg.title", null, null));
        model.addAttribute("subtitle", messageSource.getMessage("reg.subtitle", null, null));
    }

    @GetMapping("/register")
    public String getRegister() {
        return "reg-sign-up";
    }

    @PostMapping("/register")
    public String postRegister(@ModelAttribute("user") @Valid UserDto userDto, BindingResult result, Model model) {
        final Mapper mapper = new Mapper();
        if (result.hasErrors()) {
            return "reg-sign-up";
        }
        final User tempUser = userRepository.findByEmail(userDto.getEmail());
        if (tempUser != null) {
            result.rejectValue("email", "message.error");
            return "reg-sign-up";
        }
        final User user = mapper.set(userDto).mapTo(User.class);
        user.setId(String.valueOf(IdGenerator.randomUUID()));
        user.setActivated(false);

        final Token token = new Token();
        token.setKey(String.valueOf(IdGenerator.randomUUID()));
        token.setExpiryDate(new DateHelper().setTimeNow().addTimeInMinute(DateHelper.DAY_IN_MINUTE).getDate());

        final StringBuilder builder = new StringBuilder();
        builder.append(request.getRequestURL().toString().replace(request.getRequestURI(), "/"));
        builder.append("register/activate");
        builder.append("?uid=");
        builder.append(user.getId());
        builder.append("&tid=");
        builder.append(token.getKey());

        userRepository.save(user);
        tokenRepository.save(token);

        model.addAttribute("user", mapper.set(user).mapTo(UserDto.class));
        model.addAttribute("token", token);

        System.out.println(builder.toString());

        return "reg-validating";
    }

    @GetMapping("register/activate")
    public String getActivation(@RequestParam(value = "uid", required = false) String userId,
                                @RequestParam(value = "tid", required = false) String tokenId) {
        final User user = userRepository.findOne(userId);
        final Token token = tokenRepository.findOne(tokenId);
        final Calendar calendar = Calendar.getInstance();

        if (user == null || user.isActivated()) {
            System.out.println("user ang problem");
            return "reg-error";
        }
        if (token == null || !(token.getExpiryDate().getTime() - calendar.getTime().getTime() > 0)) {
            System.out.println("token ang problem");
            return "reg-error";
        }
        user.setActivated(true);
        userRepository.save(user);
        tokenRepository.delete(token);

        return "";
    }


}
