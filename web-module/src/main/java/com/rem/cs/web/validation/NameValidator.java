package com.rem.cs.web.validation;

import com.rem.cs.data.jpa.user.UserService;
import com.rem.cs.web.validation.annotation.Name;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class NameValidator implements ConstraintValidator<Name, String> {

    private MessageSource messageSource;
    private UserService userService;

    public NameValidator(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public void initialize(Name constraintAnnotation) {
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        if (StringUtils.isEmpty(name)) {
            context.buildConstraintViolationWithTemplate(messageSource
                    .getMessage("error_message.name.required", null, null))
                    .addConstraintViolation();
            return false;
        } else {
            return true;
        }
    }
}
