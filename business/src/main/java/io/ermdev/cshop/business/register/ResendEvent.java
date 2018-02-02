package io.ermdev.cshop.business.register;

import org.springframework.context.ApplicationEvent;

public class ResendEvent extends ApplicationEvent {

    public ResendEvent(RegisterSource source) {
        super(source);
    }
}
