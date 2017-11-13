package io.ermdev.cshop.web.controller;

import io.ermdev.cshop.data.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;

@Controller
@SessionAttributes({"cartItems"})
public class AccountController {

    @Autowired
    private MailService mailService;

    @GetMapping("join")
    public String showRegister() {
        return "v2/register";
    }

    @PostMapping("join")
    public String createAccount(RedirectAttributes redirectAttributes) {
        try {
            mailService.sendRegisterNotification();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        redirectAttributes.addFlashAttribute("registrationComplete", true);
        return "redirect:/join?registration-complete=true";
    }
}
