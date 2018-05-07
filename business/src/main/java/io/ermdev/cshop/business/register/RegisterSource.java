package io.ermdev.cshop.business.register;

import io.ermdev.cshop.data.entity.User;

import java.util.Locale;

public class RegisterSource {

    private User user;

    private String url;

    private Locale locale;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}
