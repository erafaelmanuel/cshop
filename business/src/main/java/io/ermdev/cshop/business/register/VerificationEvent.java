package io.ermdev.cshop.business.register;

import org.springframework.context.ApplicationEvent;

public class VerificationEvent extends ApplicationEvent {

    public VerificationEvent(VerificationSource source) {
        super(source);
    }
}
