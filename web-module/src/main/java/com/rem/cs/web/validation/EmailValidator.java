package com.rem.cs.web.validation;

import com.rem.cs.data.jpa.service.UserService;
import com.rem.cs.web.validation.annotation.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class EmailValidator implements ConstraintValidator<Email, String> {

    private MessageSource messageSource;
    private UserService userService;

    @Autowired
    public EmailValidator(MessageSource messageSource, UserService userService) {
        this.messageSource = messageSource;
        this.userService = userService;
    }

    @Override
    public void initialize(Email constraintAnnotation) {
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        final String EMAIL_PATTERN = "^[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*@" +
                "[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z0-9]+)$";
        context.disableDefaultConstraintViolation();
        if (StringUtils.isEmpty(email)) {
            context.buildConstraintViolationWithTemplate(messageSource
                    .getMessage("error_message.email.required", null, null))
                    .addConstraintViolation();
            return false;
        }
        if (!email.matches(EMAIL_PATTERN)) {
            context.buildConstraintViolationWithTemplate(messageSource
                    .getMessage("error_message.email.invalid", null, null))
                    .addConstraintViolation();
            return false;
        }
        if (userService.countByEmail(email) > 0) {
            context.buildConstraintViolationWithTemplate(messageSource
                    .getMessage("error_message.email.duplicated", null, null))
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
