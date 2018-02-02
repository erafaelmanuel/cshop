package io.ermdev.cshop.business.register;

import org.springframework.context.ApplicationEvent;

public class ResendEvent extends ApplicationEvent {

    public OnResendCompleted onResendCompleted;

    public ResendEvent(ResendSource source) {
        super(source);
    }

    public OnResendCompleted getOnResendCompleted() {
        return onResendCompleted;
    }

    public void setOnResendCompleted(OnResendCompleted onResendCompleted) {
        this.onResendCompleted = onResendCompleted;
    }
}
