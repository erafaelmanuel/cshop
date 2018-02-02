package io.ermdev.cshop.business.register;

import org.springframework.context.ApplicationEvent;

public class ConfirmEvent extends ApplicationEvent {

    private OnConfirmCompleted onConfirmCompleted;

    public ConfirmEvent(ConfirmSource source) {
        super(source);
    }

    public OnConfirmCompleted getOnConfirmCompleted() {
        return onConfirmCompleted;
    }

    public void setOnConfirmCompleted(OnConfirmCompleted onConfirmCompleted) {
        this.onConfirmCompleted = onConfirmCompleted;
    }
}
