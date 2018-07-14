package com.rem.cs.web.listener;

import org.springframework.context.ApplicationEvent;

public class UserEvent extends ApplicationEvent {

    public static final int CREATE_USER = 1;

    public static final int ACTIVATE_USER = 2;

    public static final int RESEND_CONFIRMATION_EMAIL = 3;

    public static final int CANCEL_REGISTRATION = 4;

    public UserEvent(Object source) {
        super(source);
    }
}
