package io.ermdev.cshop.business.register;

import org.springframework.context.ApplicationEvent;

public class ResendEvent extends ApplicationEvent {

    public OnResendFinished onResendFinished;

    public ResendEvent(RegisterSource source) {
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

    public class ReturnValue {
        private boolean hasError;

        public boolean hasError() {
            return hasError;
        }

        public void setHasError(boolean hasError) {
            this.hasError = hasError;
        }
    }
}
