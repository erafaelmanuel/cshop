package io.ermdev.cshop.business.register;

import io.ermdev.cshop.data.entity.Token;

import java.util.Locale;

public class RegisterSource {

    private Token token;
    private String url;
    private Locale locale;

    public RegisterSource(Token token, String url, Locale locale) {
        this.token = token;
        this.url = url;
        this.locale = locale;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
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
