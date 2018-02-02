package io.ermdev.cshop.business.register;

import org.springframework.context.ApplicationListener;

public class ConfirmListener implements ApplicationListener<ConfirmEvent> {

    private ConfirmEvent.OnConfirmCompleted onConfirmCompleted;

    @Override
    public void onApplicationEvent(ConfirmEvent confirmEvent) {
        onConfirmCompleted = confirmEvent.getOnConfirmCompleted();
    }

    public void confirmUser() {

    }
}
