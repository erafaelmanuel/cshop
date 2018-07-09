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
        model.addAttribute("title", messageSource.getMessage("reg.title", null, null));
        model.addAttribute("subtitle", messageSource.getMessage("reg.subtitle", null, null));
    }

    @GetMapping("/account/register")
    public String getRegisterPage() {
        return "accnt-reg-form";
    }

    @PostMapping("/register")
    public String registerNewUser(@ModelAttribute("user") @Valid UserDto userDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "register";
        }
        final User tempUser = userRepository.findByEmail(userDto.getEmail());
        if (tempUser != null) {
            if (!tempUser.isActivated()) {
                /* Todo Confirmation Page */
                return "";
            }
        }
        final User user = new Mapper().set(userDto).mapTo(User.class);
        user.setId(String.valueOf(IdGenerator.randomUUID()));
        user.setActivated(false);

        final Token token = new Token();
        token.setKey(String.valueOf(IdGenerator.randomUUID()));
        token.setExpiryDate(new DateHelper().setTimeNow().addTimeInMinute(DateHelper.DAY_IN_MINUTE).getDate());

        final StringBuilder builder = new StringBuilder();
        builder.append(request.getRequestURL().toString().replace(request.getRequestURI(), "/"));
        builder.append("account/activate");
        builder.append("?userId=");
        builder.append(user.getId());
        builder.append("&token=");
        builder.append(token.getKey());

        userRepository.save(user);
        tokenRepository.save(token);

        System.out.println(builder.toString());

        return "accnt-reg-help";
    }

    @GetMapping("account/activate")
    public String activateUser(@RequestParam("userId") String userId, @RequestParam("tokenId") String tokenId) {
        final User user = userRepository.findOne(userId);
        final Token token = tokenRepository.findOne(tokenId);
        final Calendar calendar = Calendar.getInstance();

        if (!user.isActivated() && (token.getExpiryDate().getTime() - calendar.getTime().getTime() > 0)) {
            user.setActivated(true);
            userRepository.save(user);
            tokenRepository.delete(token);
        }

        return "";
    }


}
