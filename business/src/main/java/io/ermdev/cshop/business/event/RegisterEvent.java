package io.ermdev.cshop.business.event;

import io.ermdev.cshop.model.entity.User;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

public class RegisterEvent extends ApplicationEvent {

    private User user;
    private String applicationContextUrl;
    private Locale locale;

    public RegisterEvent(User user, String applicationContextUrl, Locale locale ) {
        super(user);
        this.user = user;
        this.applicationContextUrl = applicationContextUrl;
        this.locale = locale;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getApplicationContextUrl() {
        return applicationContextUrl;
    }

    public void setApplicationContextUrl(String applicationContextUrl) {
        this.applicationContextUrl = applicationContextUrl;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}
