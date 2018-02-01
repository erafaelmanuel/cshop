package io.ermdev.cshop.business.register;

import org.springframework.context.ApplicationEvent;

public class RegisterEvent extends ApplicationEvent {

    private RegisterSource registerSource;

    public RegisterEvent(RegisterSource registerSource) {
        super(registerSource);
        this.registerSource = registerSource;
    }

    public RegisterSource getRegisterSource() {
        return registerSource;
    }
}
