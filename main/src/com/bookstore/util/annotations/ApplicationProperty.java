package com.bookstore.util.annotations;

import com.bookstore.util.enums.ApplicationPropertyTypesEnum;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ApplicationProperty {
    String propertyName() default "";

    ApplicationPropertyTypesEnum type();
}
