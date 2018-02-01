package com.fss.shopping.validation;

import com.fss.shopping.web.dto.UserRegistrationDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(final PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(final Object obj, final ConstraintValidatorContext context) {
        final UserRegistrationDto user = (UserRegistrationDto) obj;
        return user.getPassword().equals(user.getConfirmPassword());
    }
}
