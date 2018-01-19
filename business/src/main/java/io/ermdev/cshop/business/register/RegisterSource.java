package io.ermdev.cshop.business.register;

import io.ermdev.cshop.data.entity.VerificationToken;

import java.util.Locale;

public class RegisterSource {

    private VerificationToken verificationToken;
    private String url;
    private Locale locale;

    public RegisterSource() {}

    public RegisterSource(VerificationToken verificationToken, String url, Locale locale) {
        this.verificationToken = verificationToken;
        this.url = url;
        this.locale = locale;
    }

    public VerificationToken getVerificationToken() {
        return verificationToken;
    }

    public void setVerificationToken(VerificationToken verificationToken) {
        this.verificationToken = verificationToken;
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
