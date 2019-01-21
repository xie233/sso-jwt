package com.xie.ssoserver.validator;

import com.xie.ssoserver.util.ValidatorUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsPasswordValidator implements ConstraintValidator<IsPassword,String>{

    private boolean required = true;

    @Override
    public void initialize(IsPassword constraintAnnotation) {
        required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext context) {
        if(required){
            return ValidatorUtil.isPassword(s);
        }else{
            return true;
        }
    }
}
