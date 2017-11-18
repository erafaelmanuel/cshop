package io.ermdev.cshop.business.event;

import io.ermdev.cshop.model.entity.VerificationToken;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

public class RegisterEvent extends ApplicationEvent {

    private VerificationToken verificationToken;
    private String url;
    private Locale locale;

    public RegisterEvent(VerificationToken verificationToken, String url, Locale locale) {
        super(verificationToken);
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
