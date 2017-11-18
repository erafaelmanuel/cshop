package io.ermdev.cshop.business.listener;

import io.ermdev.cshop.business.event.MailEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.stereotype.Component;

@Component
public class MailListener implements ApplicationListener<MailEvent> {

    private JavaMailSender mailSender;

    @Autowired
    MailListener(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void onApplicationEvent(MailEvent mailEvent) {
        MimeMailMessage mailMessage = mailEvent.getMailMessage();
        mailSender.send(mailMessage.getMimeMessage());
    }
}
