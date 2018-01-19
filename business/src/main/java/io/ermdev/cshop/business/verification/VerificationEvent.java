package io.ermdev.cshop.business.verification;

import org.springframework.context.ApplicationEvent;

public class VerificationEvent extends ApplicationEvent {

    private OnVerificationSuccess onSuccess;
    private OnVerificationFailure onFailure;

    public VerificationEvent(VerificationSource source) {
        super(source);
    }

    public void setSource(VerificationSource source) {
        super.source = source;
    }

    public void setOnVerificationSuccess(OnVerificationSuccess onSuccess) {
        this.onSuccess = onSuccess;
    }

    public void setOnVerificationFailure(OnVerificationFailure onFailure) {
        this.onFailure = onFailure;
    }

    public void callSuccess() {
        if(onSuccess != null)
            onSuccess.call((VerificationSource) getSource());
    }

    public void callFailure() {
        if(onFailure != null)
            onFailure.call((VerificationSource) getSource());
    }
}
