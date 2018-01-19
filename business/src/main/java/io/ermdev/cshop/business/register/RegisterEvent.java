package io.ermdev.cshop.business.register;

import org.springframework.context.ApplicationEvent;

public class RegisterEvent extends ApplicationEvent {

    public RegisterEvent(RegisterSource source) {
        super(source);
    }
}
