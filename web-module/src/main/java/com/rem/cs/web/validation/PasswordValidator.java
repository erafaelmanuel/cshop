package com.rem.cs.web.validation;

import com.rem.cs.data.jpa.service.UserService;
import com.rem.cs.web.validation.annotation.Password;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class PasswordValidator implements ConstraintValidator<Password, String> {

    private MessageSource messageSource;
    private UserService userService;

    public PasswordValidator(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public void initialize(Password constraintAnnotation) {
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        if (StringUtils.isEmpty(password)) {
            context.buildConstraintViolationWithTemplate(messageSource
                    .getMessage("error_message.password.required", null, null))
                    .addConstraintViolation();
            return false;
        } else {
            return true;
        }
    }
}
