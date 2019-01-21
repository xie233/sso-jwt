package com.xie.ssoserver.validator;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;

@Target({METHOD,FIELD,PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {IsPasswordValidator.class})
public @interface IsPassword {

    boolean required() default true;
    String message() default "密码不符合规范";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
