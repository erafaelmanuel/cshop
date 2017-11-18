package io.ermdev.cshop.business.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.mail.javamail.MimeMailMessage;

public class MailEvent extends ApplicationEvent {

    private MimeMailMessage mailMessage;

    public MailEvent(MimeMailMessage mailMessage) {
        super(mailMessage);
        this.mailMessage = mailMessage;
    }

    public MimeMailMessage getMailMessage() {
        return mailMessage;
    }

    public void setMailMessage(MimeMailMessage mailMessage) {
        this.mailMessage = mailMessage;
    }
}
