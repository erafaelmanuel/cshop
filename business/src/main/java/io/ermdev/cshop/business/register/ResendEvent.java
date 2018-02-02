package io.ermdev.cshop.business.register;

import org.springframework.context.ApplicationEvent;

public class ResendEvent extends ApplicationEvent {

    public OnResendFinished onResendFinished;

    public ResendEvent(ResendSource source) {
        super(source);
    }

    public OnResendFinished getOnResendFinished() {
        return onResendFinished;
    }

    public void setOnResendFinished(OnResendFinished onResendFinished) {
        this.onResendFinished = onResendFinished;
    }

    public interface OnResendFinished {
        void onFinish(boolean hasError);
    }
}
