package com.bookstore.util.core.configurators;

import com.bookstore.util.annotations.MenuItemAnnotation;
import com.bookstore.util.core.ApplicationContext;

import java.lang.reflect.Field;

public class MenuItemAnnotationConfigurator implements ObjectConfigurator {
    @Override
    public void configure(Object obj, ApplicationContext context) {
        Class<?> implClass = obj.getClass();
        MenuItemAnnotation annotation = implClass.getAnnotation(MenuItemAnnotation.class);

        if (annotation != null) {
            try {
                Field titleField = implClass.getDeclaredField("title");
                titleField.setAccessible(true);
                titleField.set(obj, annotation.title());

                Field currentStateField = implClass.getDeclaredField("currentState");
                currentStateField.setAccessible(true);
                currentStateField.set(obj, annotation.currentState());

                Field nextStateField = implClass.getDeclaredField("nextState");
                nextStateField.setAccessible(true);
                nextStateField.set(obj, annotation.nextState());
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
