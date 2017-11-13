package io.ermdev.cshop.web.controller;

import io.ermdev.cshop.data.service.MailService;
import io.ermdev.cshop.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;

@Controller
@SessionAttributes({"cartItems"})
public class AccountController {

    private MailService mailService;

    @Autowired
    public AccountController(MailService mailService) {
        this.mailService = mailService;
    }

    @GetMapping("join")
    public String showRegister() {
        return "v2/register";
    }

    @PostMapping("join")
    public String createAccount(RedirectAttributes redirectAttributes,
                                @RequestParam("name") String name,
                                @RequestParam("email") String email,
                                @RequestParam("password") String password) {
        try {
            User user = new User();
            user.setName(name);
            user.setPassword(password);
            user.setEmail(email);

            if(mailService.isValidEmailAddress(email)) {
                mailService.sendRegisterNotification(user);
            } else
                throw new Exception("Invalid email address");
        } catch (Exception e) {
            e.printStackTrace();

            redirectAttributes.addFlashAttribute("name", name);
            redirectAttributes.addFlashAttribute("password", password);
            redirectAttributes.addFlashAttribute("email", email);

            return "redirect:/join?error";
        }
        redirectAttributes.addFlashAttribute("registrationComplete", true);
        return "redirect:/join?registration-complete=true";
    }
}
