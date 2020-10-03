package com.bookstore.util.core.configurators;

import com.bookstore.util.annotations.InjectObject;
import com.bookstore.util.core.ApplicationContext;

import java.lang.reflect.Field;

public class InjectObjectAnnotationConfigurator implements ObjectConfigurator {

    @Override
    public void configure(Object obj, ApplicationContext context) {
        Class<?> objClass = obj.getClass();
        for (Field field : objClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(InjectObject.class)) {
                Class<?> fieldType = field.getType();
                try {
                    field.setAccessible(true);
                    field.set(obj, context.getObject(fieldType));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
