package io.ermdev.cshop.business.register;

import org.springframework.context.ApplicationEvent;

public class RegisterEvent extends ApplicationEvent {

    private OnRegisterSuccess onRegisterSuccess;

    private OnRegisterFailure onRegisterFailure;

    public RegisterEvent(RegisterSource registerSource) {
        super(registerSource);
    }

    public OnRegisterSuccess getOnRegisterSuccess() {
        return onRegisterSuccess;
    }

    public void setOnRegisterSuccess(OnRegisterSuccess onRegisterSuccess) {
        this.onRegisterSuccess = onRegisterSuccess;
    }

    public OnRegisterFailure getOnRegisterFailure() {
        return onRegisterFailure;
    }

    public void setOnRegisterFailure(OnRegisterFailure onRegisterFailure) {
        this.onRegisterFailure = onRegisterFailure;
    }
}
