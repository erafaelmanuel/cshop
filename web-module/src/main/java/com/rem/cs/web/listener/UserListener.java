package com.rem.cs.web.listener;

import com.rem.cs.data.jpa.token.Token;
import com.rem.cs.data.jpa.token.TokenRepository;
import com.rem.cs.data.jpa.user.User;
import com.rem.cs.data.jpa.user.UserRepository;
import com.rem.cs.web.dto.UserDto;
import com.rem.cs.web.event.UserEvent;
import com.rem.mappyfy.Mapper;
import io.ermdev.cshop.commons.DateHelper;
import io.ermdev.cshop.commons.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.stereotype.Component;

import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Component
public class UserListener implements ApplicationListener<UserEvent> {

    private UserRepository userRepository;
    private TokenRepository tokenRepository;
    private JavaMailSender mailSender;

    @Autowired
    public UserListener(UserRepository userRepository, TokenRepository tokenRepository, JavaMailSender mailSender) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.mailSender = mailSender;
    }

    @Override
    public void onApplicationEvent(UserEvent event) {
        final HashMap hashMap = (HashMap) event.getSource();
        if ((int) hashMap.get("do") == 1) {
            createUser((UserDto) hashMap.get("user"), (HttpServletRequest) hashMap.get("request"));
        } else if ((int) hashMap.get("do") == 2) {
            activateUser((User) hashMap.get("user"), (Token) hashMap.get("token"));
        }
    }

    private void createUser(UserDto userDto, HttpServletRequest request) {
        final Mapper mapper = new Mapper();
        final User user = mapper.set(userDto).mapTo(User.class);
        final Token token = new Token();
        final StringBuilder builder = new StringBuilder();

        user.setId(String.valueOf(IdGenerator.randomUUID()));
        user.setActivated(false);

        token.setKey(String.valueOf(IdGenerator.randomUUID()));
        token.setExpiryDate(new DateHelper().setTimeNow().addTimeInMinute(DateHelper.DAY_IN_MINUTE).getDate());

        builder.append(request.getRequestURL().toString().replace(request.getRequestURI(), "/"));
        builder.append("register/activate");
        builder.append("?uid=");
        builder.append(user.getId());
        builder.append("&tid=");
        builder.append(token.getKey());

        userRepository.save(user);
        tokenRepository.save(token);

        sendConfirmationEmail(user, builder.toString());
    }

    private void activateUser(User user, Token token) {
        user.setActivated(true);
        userRepository.save(user);
        tokenRepository.delete(token);
    }

    private void sendConfirmationEmail(User user, String url) {
        Thread thread = new Thread(() -> {
            try {
                final String address = "ermdev.io@gmail.com";
                final String recipientAddress = user.getEmail();
                final String title = "Cloth Shop";
                final String subject = "Account Details for " + user.getName() + " at " + title;

                MimeMailMessage mailMessage = new MimeMailMessage(mailSender.createMimeMessage());
                mailMessage.setTo(recipientAddress);
                mailMessage.setSubject(subject);
                mailMessage.getMimeMessage().setFrom(new InternetAddress(address, title));
                mailMessage.getMimeMessage().setContent(url, "text/html");
                mailSender.send(mailMessage.getMimeMessage());

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }


}
