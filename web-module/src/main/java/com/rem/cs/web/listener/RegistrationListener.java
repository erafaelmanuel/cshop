package com.rem.cs.web.listener;

import com.rem.cs.data.jpa.entity.Token;
import com.rem.cs.data.jpa.repository.TokenRepository;
import com.rem.cs.data.jpa.entity.User;
import com.rem.cs.data.jpa.service.UserService;
import com.rem.cs.web.dto.UserDto;
import com.rem.cs.web.event.RegistrationEvent;
import com.rem.mappyfy.Mapper;
import io.ermdev.cshop.commons.DateHelper;
import io.ermdev.cshop.commons.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.stereotype.Component;

import javax.mail.internet.InternetAddress;
import java.util.HashMap;

@Component
public class RegistrationListener implements ApplicationListener<RegistrationEvent> {

    private UserService userService;
    private TokenRepository tokenRepository;
    private JavaMailSender mailSender;

    @Autowired
    public RegistrationListener(UserService userService, TokenRepository tokenRepository, JavaMailSender mailSender) {
        this.userService = userService;
        this.tokenRepository = tokenRepository;
        this.mailSender = mailSender;
    }

    @Override
    public void onApplicationEvent(RegistrationEvent event) {
        final HashMap parameters = (HashMap) event.getSource();
        switch ((int) parameters.get("do")) {
            case 1: {
                createUser((UserDto) parameters.get("user"), (String) parameters.get("baseUrl"));
                break;
            }
            case 2: {
                final User tempUser = new Mapper().from(parameters.get("user")).toInstanceOf(User.class);
                activateUser(tempUser, (Token) parameters.get("token"));
                break;
            }
            case 3: {
                sendConfirmationEmail((User) parameters.get("user"), (String) parameters.get("baseUrl"));
                break;
            }
            case 4: {
                changeUserEmail((User) parameters.get("user"), (String) parameters.get("email"),
                        (String) parameters.get("baseUrl"));
                break;
            }
        }
    }

    private void createUser(UserDto userDto, String baseUrl) {
        final Mapper mapper = new Mapper();
        final User user = mapper.from(userDto).toInstanceOf(User.class);

        user.setId(String.valueOf(IdGenerator.randomUUID()));
        user.setActivated(false);

        userService.save(user);
        sendConfirmationEmail(user, baseUrl);
    }

    private void activateUser(User user, Token token) {
        user.setActivated(true);
        userService.save(user);
        tokenRepository.delete(token);
    }

    private void changeUserEmail(User user, String email, String baseUrl) {
        user.setEmail(email);
        userService.save(user);
        sendConfirmationEmail(user, baseUrl);
    }

    private void sendConfirmationEmail(User user, String baseUrl) {
        Thread thread = new Thread(() -> {
            try {
                final Token token = new Token();
                final StringBuilder builder = new StringBuilder();

                final String address = "ermdev.io@gmail.com";
                final String recipientAddress = user.getEmail();
                final String title = "Cloth Shop";
                final String subject = "Account Details for " + user.getName() + " at " + title;

                token.setKey(String.valueOf(IdGenerator.randomUUID()));
                token.setExpiryDate(new DateHelper().setTimeNow().addTimeInMinute(DateHelper.DAY_IN_MINUTE).getDate());
                tokenRepository.save(token);

                builder.append(baseUrl);
                builder.append("register/activate");
                builder.append("?uid=");
                builder.append(user.getId());
                builder.append("&tid=");
                builder.append(token.getKey());

                MimeMailMessage mailMessage = new MimeMailMessage(mailSender.createMimeMessage());
                mailMessage.setTo(recipientAddress);
                mailMessage.setSubject(subject);
                mailMessage.getMimeMessage().setFrom(new InternetAddress(address, title));
                mailMessage.getMimeMessage().setContent(builder.toString(), "text/html");
                mailSender.send(mailMessage.getMimeMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }


}
