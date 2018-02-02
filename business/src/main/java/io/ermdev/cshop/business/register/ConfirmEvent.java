package io.ermdev.cshop.business.register;

import org.springframework.context.ApplicationEvent;

public class ConfirmEvent extends ApplicationEvent {

    public ConfirmEvent(Object source) {
        super(source);
    }
}
