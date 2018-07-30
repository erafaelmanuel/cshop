package com.rem.cs.web.event;

import org.springframework.context.ApplicationEvent;

public class RegistrationEvent extends ApplicationEvent {

    public static final int CREATE_USER = 1;

    public static final int ACTIVATE_USER = 2;

    public static final int RESEND_CONFIRMATION_EMAIL = 3;

    public static final int CHANGE_EMAIL_ADDRESS = 4;

    public RegistrationEvent(Object source) {
        super(source);
    }
}
